package dd.task.modulith.spike.inventory.infrastructure;

import dd.task.modulith.spike.inventory.infrastructure.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface StockEntityRepository extends JpaRepository<StockEntity, String> {
}
