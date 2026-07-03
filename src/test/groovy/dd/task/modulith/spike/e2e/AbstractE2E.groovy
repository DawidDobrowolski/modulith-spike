package dd.task.modulith.spike.e2e

import dd.task.modulith.spike.AbstractDB
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.client.RestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractE2E extends AbstractDB {

    @LocalServerPort
    protected int port

    protected RestClient restClient = RestClient.create()
}
