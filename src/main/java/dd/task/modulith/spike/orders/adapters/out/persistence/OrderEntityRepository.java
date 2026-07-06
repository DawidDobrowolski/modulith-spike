package dd.task.modulith.spike.orders.adapters.out.persistence;

import dd.task.modulith.spike.orders.adapters.out.persistence.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, UUID> {
}
