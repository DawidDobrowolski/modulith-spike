package dd.task.modulith.spike.order.domain;

public class OrderRejectedException extends RuntimeException {

    public OrderRejectedException(String reason) {
        super(reason);
    }
}
