package dd.task.modulith.spike.orders.application;

import dd.task.modulith.spike.orders.domain.model.Order;
import dd.task.modulith.spike.orders.domain.OrdersDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersApplicationService {

    private final OrdersDomainService ordersDomainService;

    public Order place(String sku, int quantity) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU must not be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return ordersDomainService.place(sku, quantity);
    }

    public Optional<Order> find(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID must not be null");
        }
        return ordersDomainService.find(id);
    }
}
