package dd.task.modulith.spike.inventory.domain.port;

import dd.task.modulith.spike.inventory.domain.model.Stock;

import java.util.Optional;

public interface StockRepository {

    void save(Stock stock);

    Optional<Stock> findBySku(String sku);
}
