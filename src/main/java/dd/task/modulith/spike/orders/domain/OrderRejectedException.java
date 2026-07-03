package dd.task.modulith.spike.orders.domain;

public class OrderRejectedException extends RuntimeException {

    public OrderRejectedException(String reason) {
        super(reason);
    }
}
