package dd.task.modulith.spike.orders.domain.model

import dd.task.modulith.spike.AbstractTest
import spock.lang.Unroll

class OrderTest extends AbstractTest {

    private final String SKU = "SKU-1"

    def "creating order generates UUID and preserves fields"() {
        given:
        int orderQuantity = 5

        when:
        def order = new Order(SKU, orderQuantity)

        then:
        with(order) {
            id != null
            sku == SKU
            quantity == orderQuantity
        }
    }

    def "generated IDs are unique"() {
        expect:
        new Order(SKU, 5).id != new Order(SKU, 5).id
    }

    @Unroll
    def "rejects blank SKU (#sku)"() {
        when:
        new Order(sku, 5)

        then:
        thrown(IllegalArgumentException)

        where:
        sku << [null, "", "   "]
    }

    @Unroll
    def "rejects non-positive quantity (#quantity)"() {
        when:
        new Order(SKU, quantity)

        then:
        thrown(IllegalArgumentException)

        where:
        quantity << [0, -1, -100]
    }

    def "full-args constructor creates Order with given ID (for persistence reconstitution)"() {
        given:
        UUID orderId = UUID.randomUUID()
        int orderQuantity = 5

        when:
        def order = new Order(orderId, SKU, orderQuantity)

        then:
        with(order){
            id == orderId
            sku == SKU
            quantity == orderQuantity
        }
    }
}
