package dd.task.modulith.spike.e2e

import dd.task.modulith.spike.inventory.application.InventoryApplicationService
import dd.task.modulith.spike.inventory.adapters.out.persistence.StockEntityRepository
import dd.task.modulith.spike.orders.adapters.out.persistence.OrderEntityRepository
import org.awaitility.Awaitility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

import java.util.concurrent.TimeUnit

class OrderPlacementE2E extends AbstractE2E {

    @Autowired
    private InventoryApplicationService inventoryApplicationService

    @Autowired
    private StockEntityRepository stockEntityRepository

    @Autowired
    private OrderEntityRepository orderEntityRepository

    def cleanup() {
        orderEntityRepository.deleteAll()
        stockEntityRepository.deleteAll()
    }

    def "should save stock, place order and update stock quantity"() {
        given:
        def testSku = "SKU-" + UUID.randomUUID()

        when: "add stock via REST"
        def stockStatus = restClient.post()
                .uri("http://localhost:${port}/api/inventory/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .body([sku: testSku, quantity: 10])
                .retrieve()
                .toBodilessEntity()
                .statusCode

        then:
        stockStatus.is2xxSuccessful()

        and: "stock saved in db"
        def stock = stockEntityRepository.findById(testSku).orElseThrow()
        with(stock) {
            sku == testSku
            quantity == 10
        }

        when: "place order via REST"
        def orderBody = restClient.post()
                .uri("http://localhost:${port}/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body([sku: testSku, quantity: 3])
                .retrieve()
                .body(Map)

        then: "order saved in db"
        def order = orderEntityRepository.findById(UUID.fromString(orderBody.id as String)).orElseThrow()
        with(order) {
            sku == testSku
            quantity == 3
        }

        and: "stock deducted asynchronously via OrderPlacedEvent"
        Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted {
            stockEntityRepository.findById(testSku).orElseThrow().quantity == 7
        }
    }
}
