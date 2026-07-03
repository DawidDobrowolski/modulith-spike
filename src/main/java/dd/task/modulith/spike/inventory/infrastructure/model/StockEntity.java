package dd.task.modulith.spike.inventory.infrastructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock", schema = "inventory")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockEntity {

    @Id
    private String sku;

    private int quantity;

    public StockEntity(String sku, int quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }
}
