package dd.task.modulith.spike.inventory.infrastructure;

import dd.task.modulith.spike.inventory.domain.model.Stock;
import dd.task.modulith.spike.inventory.domain.port.StockRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
class InMemoryStockRepository implements StockRepository {

    private final Map<String, Stock> stocks = new HashMap<>();

    @Override
    public void save(Stock stock) {
        stocks.put(stock.sku(), stock);
    }

    @Override
    public Optional<Stock> findBySku(String sku) {
        return Optional.ofNullable(stocks.get(sku));
    }
}
