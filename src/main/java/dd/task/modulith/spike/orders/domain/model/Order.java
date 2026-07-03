package dd.task.modulith.spike.orders.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Order {

    private final String id;
    private final String sku;
    private final int quantity;

    public Order(String sku, int quantity) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU must not be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.id = UUID.randomUUID().toString();
        this.sku = sku;
        this.quantity = quantity;
    }
}
