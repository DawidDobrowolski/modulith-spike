package dd.task.modulith.spike.arch

import dd.task.modulith.spike.AbstractTest
import dd.task.modulith.spike.ModulithSpikeApplication
import dd.task.modulith.spike.inventory.adapters.in.InventoryFacade
import dd.task.modulith.spike.shared.events.OrderPlacedEvent
import org.springframework.modulith.core.ApplicationModules
import spock.lang.Shared

class ModulesArchitectureTest extends AbstractTest {

    @Shared
    def modules = ApplicationModules.of(ModulithSpikeApplication)

    def "application modules structure is valid"() {
        expect:
        modules.verify()
    }

    def "modules are correctly detected"() {
        expect:
        modules.collect { it.identifier.toString() }
                .containsAll(["orders", "inventory", "shared"])
    }

    def "inventory exposes InventoryFacade"() {
        given:
        def inventory = modules.getModuleByName("inventory").get()

        expect:
        inventory.isExposed(InventoryFacade)
    }

    def "shared exposes OrderPlacedEvent"() {
        given:
        def shared = modules.getModuleByName("shared").get()

        expect:
        shared.isExposed(OrderPlacedEvent)
    }
}
