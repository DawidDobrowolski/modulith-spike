package dd.task.modulith.spike.orders.adapters.out.persistence;

import dd.task.modulith.spike.orders.adapters.out.persistence.model.OrderEntity;
import dd.task.modulith.spike.orders.domain.model.Order;
import dd.task.modulith.spike.orders.domain.port.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class JpaOrderRepository implements OrderRepository {

    private final OrderEntityRepository jpa;

    @Override
    public void save(Order order) {
        jpa.save(new OrderEntity(order.getId(), order.getSku(), order.getQuantity()));
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpa.findById(id)
                .map(e -> new Order(e.getId(), e.getSku(), e.getQuantity()));
    }
}
