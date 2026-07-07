package dd.task.modulith.spike.order.domain.port;

import dd.task.modulith.spike.order.domain.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    void save(Order order);

    Optional<Order> findById(UUID id);
}
