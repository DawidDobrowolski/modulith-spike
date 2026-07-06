package dd.task.modulith.spike.orders.it

import dd.task.modulith.spike.AbstractDB
import dd.task.modulith.spike.inventory.application.InventoryApplicationService
import dd.task.modulith.spike.orders.application.OrdersApplicationService
import dd.task.modulith.spike.orders.domain.OrderRejectedException
import dd.task.modulith.spike.orders.adapters.out.persistence.OrderEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
class OrdersIT extends AbstractDB {

    @Autowired
    private OrdersApplicationService ordersApplicationService

    @Autowired
    private InventoryApplicationService inventoryApplicationService

    @Autowired
    private OrderEntityRepository orderRepository

    def cleanup() {
        orderRepository.deleteAll()
    }

    def "places order when stock is available"() {
        given:
        def orderSku = "SKU-${UUID.randomUUID()}"
        inventoryApplicationService.addStock(orderSku, 5)

        when:
        def order = ordersApplicationService.place(orderSku, 3)

        then:
        with(order) {
            id != null
            sku == orderSku
            quantity == 3
        }

        and: "saved in db"
        def saved = orderRepository.findById(order.id).orElseThrow()
        with(saved) {
            id == order.id
            sku == orderSku
            quantity == 3
        }
    }

    def "rejects order when insufficient stock"() {
        given:
        def sku = "SKU-${UUID.randomUUID()}"
        inventoryApplicationService.addStock(sku, 2)

        when:
        ordersApplicationService.place(sku, 5)

        then:
        thrown(OrderRejectedException)

        and: "not saved in db"
        orderRepository.findAll().size() == 0
    }
}
