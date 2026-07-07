package dd.task.modulith.spike.order.adapter.out;

import dd.task.modulith.spike.inventory.adapter.in.InventoryFacade;
import dd.task.modulith.spike.order.domain.port.InventoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InventoryClient implements InventoryPort {

    private final InventoryFacade inventoryFacade;

    @Override
    public boolean hasStockFor(String sku, int quantity) {
        return inventoryFacade.getStock(sku)
                .map(stock -> stock.quantity() >= quantity)
                .orElse(false);
    }
}
