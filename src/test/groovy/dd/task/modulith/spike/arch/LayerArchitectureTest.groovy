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
    def "'#from' layer should not depend on '#to' across all modules"() {
        when:
        ArchRuleDefinition.noClasses().that().resideInAPackage("..${from}..")
                .should().dependOnClassesThat().resideInAPackage("..${to}..")
                .allowEmptyShould(true)
                .check(classes)

        then:
        noExceptionThrown()

        where:
        from          | to
        "domain"      | "application"
        "domain"      | "adapters.in"
        "domain"      | "adapters.out"
        "domain"      | "shared"

        "application" | "adapters.in"
        "application" | "adapters.out"
        "application" | "shared"
    }

    @Unroll
    def "in module '#module' adapter '#from' should not depend on '#to'"() {
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
                    [m, "adapters.in", "adapters.out"],
                    [m, "adapters.out", "adapters.in"]
            ]
        }
    }
}