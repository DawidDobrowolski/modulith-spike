package dd.task.modulith.spike

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ModulithSpikeApplicationTest extends Specification {

    def "context loads"() {
        expect:
        true
    }
}
