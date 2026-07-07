package dd.task.modulith.spike.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Order {

    private final UUID id;
    private final String sku;
    private final int quantity;

    public Order(String sku, int quantity) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU must not be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.id = UUID.randomUUID();
        this.sku = sku;
        this.quantity = quantity;
    }
}
