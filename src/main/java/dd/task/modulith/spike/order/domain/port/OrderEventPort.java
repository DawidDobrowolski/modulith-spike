package dd.task.modulith.spike.order.domain.port;

import java.util.UUID;

public interface OrderEventPort {

    void orderPlaced(UUID orderId, String sku, int quantity);
}
