package dd.task.modulith.spike.orders.adapters.out;

import dd.task.modulith.spike.inventory.adapters.in.InventoryFacade;
import dd.task.modulith.spike.orders.domain.port.InventoryPort;
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
