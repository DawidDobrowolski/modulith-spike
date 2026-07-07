package dd.task.modulith.spike.inventory.adapter.in;

import dd.task.modulith.spike.inventory.application.InventoryApplicationService;
import dd.task.modulith.spike.inventory.adapter.in.model.StockView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryFacade {

    private final InventoryApplicationService inventoryApplicationService;

    public void addStock(String sku, int quantity) {
        inventoryApplicationService.addStock(sku, quantity);
    }

    public Optional<StockView> getStock(String sku) {
        return inventoryApplicationService.getStock(sku)
                .map(s -> new StockView(s.sku(), s.quantity()));
    }
}
