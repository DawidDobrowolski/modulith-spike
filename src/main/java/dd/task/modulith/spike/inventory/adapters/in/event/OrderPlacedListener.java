package dd.task.modulith.spike.inventory.adapters.in.event;

import dd.task.modulith.spike.inventory.application.InventoryApplicationService;
import dd.task.modulith.spike.shared.events.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderPlacedListener {

    private final InventoryApplicationService inventoryApplicationService;

    @EventListener
    void on(OrderPlacedEvent event) {
        inventoryApplicationService.deductStock(event.sku(), event.quantity());
    }
}
