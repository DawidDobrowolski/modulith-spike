package dd.task.modulith.spike.orders.infrastructure;

import dd.task.modulith.spike.orders.infrastructure.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface OrderEntityRepository extends JpaRepository<OrderEntity, UUID> {
}
