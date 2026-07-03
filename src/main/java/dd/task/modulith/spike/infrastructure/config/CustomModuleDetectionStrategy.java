package dd.task.modulith.spike.infrastructure.config;

import org.jspecify.annotations.NullMarked;
import org.springframework.modulith.core.ApplicationModuleDetectionStrategy;
import org.springframework.modulith.core.ApplicationModuleInformation;
import org.springframework.modulith.core.JavaPackage;
import org.springframework.modulith.core.NamedInterfaces;

import java.util.Set;
import java.util.stream.Stream;

@NullMarked
class CustomModuleDetectionStrategy implements ApplicationModuleDetectionStrategy {

    private static final Set<String> EXCLUDED_FROM_MODULES = Set.of("infrastructure");
    private static final Set<String> MODULES_ALL_PACKAGES_ALLOWED = Set.of("shared");
    private static final String MODULE_API_LOCATION = ".adapters.in";
    private static final Set<String> API_EXPOSED_PACKAGES = Set.of(".models");

    @Override
    public Stream<JavaPackage> getModuleBasePackages(JavaPackage basePackage) {
        return basePackage.getDirectSubPackages().stream()
                .filter(subPackage -> !EXCLUDED_FROM_MODULES.contains(subPackage.getLocalName()));
    }

    @Override
    public NamedInterfaces detectNamedInterfaces(JavaPackage basePackage, ApplicationModuleInformation information) {
        if (MODULES_ALL_PACKAGES_ALLOWED.contains(basePackage.getLocalName())) {
            return NamedInterfaces.builder(basePackage)
                    .recursive()
                    .including(_ -> true)
                    .build();
        }

        return NamedInterfaces.builder(basePackage)
                .recursive()
                .including(javaPackage -> {
                    final String name = javaPackage.getName();

                    return name.endsWith(MODULE_API_LOCATION) ||
                            API_EXPOSED_PACKAGES.stream()
                                    .anyMatch(exposed -> name.contains(MODULE_API_LOCATION) && name.endsWith(exposed));
                })
                .build();
    }
}
