package dd.task.modulith.spike.inventory.adapter.in.event;

import dd.task.modulith.spike.inventory.application.InventoryApplicationService;
import dd.task.modulith.spike.shared.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderPlacedListener {

    private final InventoryApplicationService inventoryApplicationService;

    @ApplicationModuleListener
    void on(OrderPlacedEvent event) {
        inventoryApplicationService.deductStock(event.sku(), event.quantity());
    }
}
