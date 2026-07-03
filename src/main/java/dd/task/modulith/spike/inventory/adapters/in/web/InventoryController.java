package dd.task.modulith.spike.inventory.adapters.in.web;

import dd.task.modulith.spike.inventory.adapters.in.web.model.AddStockRequest;
import dd.task.modulith.spike.inventory.application.InventoryApplicationService;
import dd.task.modulith.spike.inventory.domain.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
class InventoryController {

    private final InventoryApplicationService inventoryApplicationService;

    @PostMapping("/stock")
    ResponseEntity<Void> addStock(@RequestBody AddStockRequest request) {
        inventoryApplicationService.addStock(request.sku(), request.quantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{sku}")
    ResponseEntity<Stock> getStock(@PathVariable String sku) {
        return inventoryApplicationService.getStock(sku)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
