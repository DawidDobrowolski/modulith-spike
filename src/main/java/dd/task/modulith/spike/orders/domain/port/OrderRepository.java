package dd.task.modulith.spike.orders.domain.port;

import dd.task.modulith.spike.orders.domain.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    void save(Order order);

    Optional<Order> findById(UUID id);
}
