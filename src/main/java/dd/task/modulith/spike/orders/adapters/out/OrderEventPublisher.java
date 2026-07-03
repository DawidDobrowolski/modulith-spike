package dd.task.modulith.spike.orders.adapters.out;

import dd.task.modulith.spike.orders.domain.port.OrderEventPort;
import dd.task.modulith.spike.shared.events.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderEventPublisher implements OrderEventPort {

    private final ApplicationEventPublisher publisher;

    @Override
    public void orderPlaced(String orderId, String sku, int quantity) {
        publisher.publishEvent(new OrderPlacedEvent(orderId, sku, quantity));
    }
}
