package dd.task.modulith.spike.inventory.adapter.out.persistence;

import dd.task.modulith.spike.inventory.adapter.out.persistence.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockEntityRepository extends JpaRepository<StockEntity, String> {
}
