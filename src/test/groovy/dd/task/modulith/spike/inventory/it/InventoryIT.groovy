package dd.task.modulith.spike.inventory.it

import dd.task.modulith.spike.AbstractDB
import dd.task.modulith.spike.inventory.application.InventoryApplicationService
import dd.task.modulith.spike.inventory.infrastructure.StockEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest
class InventoryIT extends AbstractDB {

    @Autowired
    private InventoryApplicationService inventoryApplicationService

    @Autowired
    private StockEntityRepository stockEntityRepository

    def cleanup() {
        stockEntityRepository.deleteAll()
    }

    def "adds stock and reads it back"() {
        given:
        String stockSku = "SKU-${UUID.randomUUID()}"

        when:
        inventoryApplicationService.addStock(stockSku, 10)

        then:
        def stock = inventoryApplicationService.getStock(stockSku).get()
        with(stock) {
            sku() == stockSku
            quantity() == 10
        }

        and: "saved in db"
        def saved = stockEntityRepository.findById(stockSku).orElseThrow()
        with(saved) {
            sku == stockSku
            quantity == 10
        }
    }

    def "deducts stock reducing quantity"() {
        given:
        String stockSku = "SKU-${UUID.randomUUID()}"
        inventoryApplicationService.addStock(stockSku, 10)

        when:
        inventoryApplicationService.deductStock(stockSku, 3)

        then:
        inventoryApplicationService.getStock(stockSku).get().quantity() == 7

        and: "quantity updated in db"
        def saved = stockEntityRepository.findById(stockSku).orElseThrow()
        saved.quantity == 7
    }
}
