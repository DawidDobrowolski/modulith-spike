package dd.task.modulith.spike.inventory.domain;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String sku, int available, int requested) {
        super("Cannot deduct %d from stock '%s' (available: %d)".formatted(requested, sku, available));
    }
}
