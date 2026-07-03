package dd.task.modulith.spike.orders.domain.port;

import dd.task.modulith.spike.orders.domain.model.Order;

import java.util.Optional;

public interface OrderRepository {

    void save(Order order);

    Optional<Order> findById(String id);
}
