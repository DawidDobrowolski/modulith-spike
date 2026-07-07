package dd.task.modulith.spike.order.adapter.out.persistence;

import dd.task.modulith.spike.order.adapter.out.persistence.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, UUID> {
}
