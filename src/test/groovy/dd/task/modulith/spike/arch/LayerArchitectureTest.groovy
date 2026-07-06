package dd.task.modulith.spike.arch

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import dd.task.modulith.spike.AbstractTest
import dd.task.modulith.spike.ModulithSpikeApplication
import org.springframework.modulith.core.ApplicationModules
import spock.lang.Shared
import spock.lang.Unroll

class LayerArchitectureTest extends AbstractTest {

    @Shared
    def classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("dd.task.modulith.spike")

    @Shared
    def discoveredModules = ApplicationModules.of(ModulithSpikeApplication)
            .collect { it.identifier.toString() }

    @Unroll
    def "in module '#module' layer '#from' should not depend on '#to'"() {
        when:
        ArchRuleDefinition.noClasses().that().resideInAPackage("..${module}.${from}..")
                .should().dependOnClassesThat().resideInAPackage("..${module}.${to}..")
                .allowEmptyShould(true)
                .check(classes)

        then:
        noExceptionThrown()

        where:
        [module, from, to] << discoveredModules.collectMany { m ->
            [
                    [m, "domain", "application"],
                    [m, "domain", "adapters.in"],
                    [m, "domain", "adapters.out"],

                    [m, "application", "adapters.in"],
                    [m, "application", "adapters.out"],

                    [m, "adapters.in", "adapters.out"],
                    [m, "adapters.out", "adapters.in"]
            ]
        }
    }
}