package dd.task.modulith.spike

import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration)
abstract class AbstractDB extends AbstractTest {
}
