package dd.task.modulith.spike.orders.domain.port;

public interface InventoryPort {

    boolean hasStockFor(String sku, int quantity);
}
