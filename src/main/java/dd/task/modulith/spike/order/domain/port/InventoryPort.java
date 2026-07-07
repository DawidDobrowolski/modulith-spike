package dd.task.modulith.spike.order.domain.port;

public interface InventoryPort {

    boolean hasStockFor(String sku, int quantity);
}
