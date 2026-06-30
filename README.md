# modulith-spike

Spike project exploring [Spring Modulith](https://spring.io/projects/spring-modulith) — modular monolith architecture with Spring Boot 4.

## Tech stack

- Java 25
- Spring Boot 4.1.0
- Spring Modulith
- Spock 2.4 + Groovy 4.0 (testy)

## Running locally

```bash
sdk env          # activate Java 25 via SDKMAN (if using .sdkmanrc)
./mvnw spring-boot:run
```

## Running tests

```bash
./mvnw test
```
