package dd.task.modulith.spike.orders.domain

import dd.task.modulith.spike.AbstractTest
import dd.task.modulith.spike.orders.domain.model.Order
import dd.task.modulith.spike.orders.domain.port.InventoryPort
import dd.task.modulith.spike.orders.domain.port.OrderEventPort
import dd.task.modulith.spike.orders.domain.port.OrderRepository

class OrdersDomainServiceTest extends AbstractTest {

    private OrderRepository orderRepository = Mock()
    private InventoryPort inventoryPort = Mock()
    private OrderEventPort orderEventPort = Mock()

    private OrdersDomainService service = new OrdersDomainService(orderRepository, inventoryPort, orderEventPort)

    private final String SKU = "SKU-1"
    private final int QUANTITY = 3

    def "places order when stock is available, saves it and publishes event"() {
        given:
        inventoryPort.hasStockFor(SKU, QUANTITY) >> true

        when:
        Order order = service.place(SKU, QUANTITY)

        then:
        with(order) {
            id != null
            sku == SKU
            quantity == QUANTITY
        }
        1 * orderRepository.save({ Order o -> o.sku == SKU && o.quantity == QUANTITY })
        1 * orderEventPort.orderPlaced(_ as UUID, SKU, QUANTITY)
    }

    def "rejects order when insufficient stock, without side effects"() {
        given:
        int excessiveQuantity = 100
        inventoryPort.hasStockFor(SKU, excessiveQuantity) >> false

        when:
        service.place(SKU, excessiveQuantity)

        then:
        thrown(OrderRejectedException)
        0 * orderRepository.save(_)
        0 * orderEventPort.orderPlaced(_, _, _)
    }

    def "find delegates to repository"() {
        given:
        UUID id = UUID.randomUUID()
        Order order = new Order(id, SKU, QUANTITY)
        orderRepository.findById(id) >> Optional.of(order)

        when:
        def result = service.find(id)

        then:
        result.get().is(order)
    }
}
