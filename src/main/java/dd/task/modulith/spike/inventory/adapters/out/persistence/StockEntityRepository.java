package dd.task.modulith.spike.inventory.adapters.out.persistence;

import dd.task.modulith.spike.inventory.adapters.out.persistence.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockEntityRepository extends JpaRepository<StockEntity, String> {
}
