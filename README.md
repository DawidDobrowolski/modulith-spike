# modulith-spike

Spike project exploring [Spring Modulith](https://spring.io/projects/spring-modulith) — modular monolith architecture with Spring Boot 4.

## Purpose

The goal of this spike was to evaluate Spring Modulith as a tool for enforcing module boundaries within a monolithic Spring Boot application. The following capabilities were tested:

- **Custom module detection** — `CustomModuleDetectionStrategy` excludes the top-level `infrastructure` package from being treated as a module and controls which sub-packages each module exposes as its public API.
- **Module boundary enforcement** — `ApplicationModules.verify()` fails the build if any module accesses another module's internals.
- **Synchronous cross-module communication** — `orders` calls `inventory` through an explicitly exposed `InventoryFacade`, preventing direct access to internal classes.
- **Asynchronous cross-module events with outbox** — `orders` publishes `OrderPlacedEvent` via Spring's `ApplicationEventPublisher`; `inventory` consumes it with `@ApplicationModuleListener`. Spring Modulith persists unpublished events in a dedicated outbox table (`modulith.event_publication`) to guarantee at-least-once delivery.
- **Architecture tests** — ArchUnit rules verify layer isolation inside each module (domain → application → adapters.in/out); Modulith-level tests verify module structure and exposed types.
- **Documentation generation** — `Documenter` produces PlantUML diagrams and module canvases from the live module model.

## Tech stack

| Technology | Version | Role |
|---|---|---|
| Java | 25 | Language |
| Spring Boot | 4.1.0 | Application framework |
| Spring Modulith | 2.1.0 | Module boundary enforcement, async events with outbox, documentation |
| Spring Data JPA / Hibernate | (Boot-managed) | Persistence |
| PostgreSQL | (via Docker) | Database |
| Liquibase | (Boot-managed) | Schema migrations |
| Lombok | (Boot-managed) | Boilerplate reduction |
| Spock | 2.4 / Groovy 5.0 | Test framework |
| Testcontainers | 1.21.1 | PostgreSQL in tests |
| ArchUnit | (Modulith-managed) | Architecture rule enforcement |

## Running locally

Requires a PostgreSQL instance on `localhost:5432` with database `modulith`, user `postgres`, password `postgres`.

```bash
./mvnw spring-boot:run
```

## Running tests

Tests start a PostgreSQL container automatically via Testcontainers — no local database needed.

```bash
./mvnw test
```

## Architecture

### Layer model (applied inside each module)

```
adapters.in  ─────────►  application  ─────────►  domain
adapters.out ─────────►
```

| Layer            | Responsibility                                                                                                                                                                                 |
|------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `domain`         | Pure business logic: entities, domain services, domain exceptions, port interfaces. No Spring, no JPA.                                                                                         |
| `application`    | Orchestration and transaction management. Calls domain services, delegates to ports. Entry point for all business operations.                                                                  |
| `adapters.in`    | Inbound entry points: REST controllers, Spring event listeners (`@ApplicationModuleListener`), and `Facade` classes that form the module's public API.                                         |
| `adapters.out`   | Outbound adapters implementing domain ports: inter-module clients, event publishers, and a `persistence` sub-package containing JPA entities, Spring Data interfaces, and repository adapters. |
| `infrastructure` | Config classes.                                                                                                                                                                                |

Layer isolation is verified automatically by `LayerArchitectureTest` for every discovered module.

### Modules

#### `orders`
Manages order placement and retrieval.

- Exposes `POST /api/orders` and `GET /api/orders/{id}` via `OrdersController`.
- Checks stock availability synchronously by calling `InventoryFacade` (via `InventoryClient` adapter in `adapters.out`).
- Publishes `OrderPlacedEvent` after a successful order via `OrderEventPublisher`.

#### `inventory`
Manages product stock levels.

- Exposes `InventoryFacade` as its public API — the only entry point accessible to other modules.
- Exposes `GET /api/inventory/{sku}` and `POST /api/inventory/stock` via `InventoryController`.
- Listens for `OrderPlacedEvent` and deducts stock asynchronously via `OrderPlacedListener`.

#### `shared`
Cross-module contract package.

- Contains `OrderPlacedEvent` — a record used by `orders` to publish and by `inventory` to consume.
- All types in this package are fully exposed to all modules.

#### `infrastructure` _(excluded from Modulith module detection)_
Top-level cross-cutting infrastructure.

- Contains `CustomModuleDetectionStrategy` which configures how Spring Modulith discovers modules and determines their public API boundaries.
- Excluded from module detection by name so Modulith does not treat it as a module.

### Cross-module communication

```
orders ──(sync)──► InventoryFacade  [inventory module]
orders ──(async, outbox)──► OrderPlacedEvent ──► OrderPlacedListener  [inventory module]
```

Synchronous calls use the Facade pattern — `orders` depends only on `InventoryFacade`, which is the sole type `inventory` exposes at the `adapters.in` level.

Asynchronous events are delivered via Spring Modulith's JDBC-backed outbox. The `modulith.event_publication` table stores each event until its listener acknowledges it, surviving application restarts.

## Test types

| Type | Location | Tool | What it tests |
|---|---|---|---|
| Unit | `order/domain`, `inventory/domain` | Spock + Mocks | Domain logic in isolation — no Spring, no DB, ports mocked |
| Integration | `order/it`, `inventory/it` | Spock + `@ApplicationModuleTest` + Testcontainers | Single module bootstrapped with its direct dependencies, real DB |
| E2E | `e2e` | Spock + `@SpringBootTest` + Testcontainers | Full application over HTTP — verifies cross-module flows including async event delivery |
| Architecture | `arch` | ArchUnit + Spring Modulith | Module boundary enforcement (`ModulesArchitectureTest`) and layer isolation within modules (`LayerArchitectureTest`) |

`@ApplicationModuleTest(DIRECT_DEPENDENCIES)` boots only the tested module and its direct neighbours — faster feedback than a full context, wider than a pure unit test.

## Database schemas

| Schema      | Contents                                                      |
|-------------|---------------------------------------------------------------|
| `orders`    | `orders` table                                                |
| `inventory` | `stock` table                                                 |
| `modulith`  | `event_publication` outbox table (managed by Spring Modulith) |

Migrations are managed by Liquibase (`db/changelog/db.changelog-master.xml`).
