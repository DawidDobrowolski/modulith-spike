package dd.task.modulith.spike.inventory.adapter.out.persistence;

import dd.task.modulith.spike.inventory.adapter.out.persistence.model.StockEntity;
import dd.task.modulith.spike.inventory.domain.model.Stock;
import dd.task.modulith.spike.inventory.domain.port.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class JpaStockRepository implements StockRepository {

    private final StockEntityRepository jpa;

    @Override
    public void save(Stock stock) {
        jpa.save(new StockEntity(stock.sku(), stock.quantity()));
    }

    @Override
    public Optional<Stock> findBySku(String sku) {
        return jpa.findById(sku)
                .map(e -> new Stock(e.getSku(), e.getQuantity()));
    }
}
