package dd.task.modulith.spike.inventory.domain.model

import dd.task.modulith.spike.AbstractTest
import dd.task.modulith.spike.inventory.domain.InsufficientStockException

class StockTest extends AbstractTest {

    private final String SKU = SKU

    def "empty stock has quantity 0"() {
        expect:
        Stock.empty(SKU).quantity() == 0
    }

    def "cannot create stock with negative quantity"() {
        when:
        new Stock(SKU, -1)

        then:
        thrown(IllegalArgumentException)
    }

    def "add increases quantity"() {
        given:
        def stock = new Stock(SKU, 5)

        when:
        def result = stock.add(3)

        then:
        with(result){
            quantity() == 8
            sku() == SKU
        }
    }

    def "deduct reduces quantity"() {
        given:
        def stock = new Stock(SKU, 10)

        when:
        def result = stock.deduct(3)

        then:
        result.quantity() == 7
    }

    def "deducting more than available throws InsufficientStockException"() {
        given:
        def stock = new Stock(SKU, 5)

        when:
        stock.deduct(10)

        then:
        thrown(InsufficientStockException)
    }

    def "stock is immutable - operations return new instance"() {
        given:
        def original = new Stock(SKU, 5)

        when:
        def modified = original.add(3)

        then:
        original.quantity() == 5
        modified.quantity() == 8
        !original.is(modified)
    }
}
