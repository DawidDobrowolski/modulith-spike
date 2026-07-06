package dd.task.modulith.spike.inventory.application;

import dd.task.modulith.spike.inventory.domain.InventoryDomainService;
import dd.task.modulith.spike.inventory.domain.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryApplicationService {

    private final InventoryDomainService inventoryDomainService;

    @Transactional
    public void addStock(String sku, int quantity) {
        validate(sku, quantity);
        inventoryDomainService.addStock(sku, quantity);
    }

    @Transactional(readOnly = true)
    public Optional<Stock> getStock(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU must not be blank");
        }
        return inventoryDomainService.getStock(sku);
    }

    @Transactional
    public void deductStock(String sku, int quantity) {
        validate(sku, quantity);
        inventoryDomainService.deductStock(sku, quantity);
    }

    private void validate(String sku, int quantity) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU must not be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
}
