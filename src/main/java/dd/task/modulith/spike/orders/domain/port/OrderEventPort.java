package dd.task.modulith.spike.orders.domain.port;

public interface OrderEventPort {

    void orderPlaced(String orderId, String sku, int quantity);
}
