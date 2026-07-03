package dd.task.modulith.spike.inventory.domain.model;

import dd.task.modulith.spike.inventory.domain.InsufficientStockException;

public record Stock(String sku, int quantity) {

    public Stock {
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }

    public static Stock empty(String sku) {
        return new Stock(sku, 0);
    }

    public Stock add(int amount) {
        return new Stock(sku, quantity + amount);
    }

    public Stock deduct(int amount) {
        if (amount > quantity) {
            throw new InsufficientStockException(sku, quantity, amount);
        }
        return new Stock(sku, quantity - amount);
    }
}
