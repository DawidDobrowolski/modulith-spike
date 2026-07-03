package dd.task.modulith.spike.inventory.domain;

import dd.task.modulith.spike.inventory.domain.model.Stock;
import dd.task.modulith.spike.inventory.domain.port.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryDomainService {

    private final StockRepository stockRepository;

    public void addStock(String sku, int quantity) {
        Stock stock = stockRepository.findBySku(sku).orElseGet(() -> Stock.empty(sku));
        stockRepository.save(stock.add(quantity));
    }

    public Optional<Stock> getStock(String sku) {
        return stockRepository.findBySku(sku);
    }

    public void deductStock(String sku, int quantity) {
        stockRepository.findBySku(sku)
                .ifPresent(stock -> stockRepository.save(stock.deduct(quantity)));
    }
}
