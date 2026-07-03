package dd.task.modulith.spike.orders.domain;

import dd.task.modulith.spike.orders.domain.model.Order;
import dd.task.modulith.spike.orders.domain.port.InventoryPort;
import dd.task.modulith.spike.orders.domain.port.OrderEventPort;
import dd.task.modulith.spike.orders.domain.port.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersDomainService {

    private final OrderRepository orderRepository;
    private final InventoryPort inventoryPort;
    private final OrderEventPort orderEventPort;

    public Order place(String sku, int quantity) {
        if (!inventoryPort.hasStockFor(sku, quantity)) {
            throw new OrderRejectedException("Insufficient stock for sku: " + sku);
        }
        Order order = new Order(sku, quantity);
        orderRepository.save(order);
        orderEventPort.orderPlaced(order.getId(), order.getSku(), order.getQuantity());
        return order;
    }

    public Optional<Order> find(String id) {
        return orderRepository.findById(id);
    }
}
