package com.dogpals.training;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.dogpals.training");

        noClasses()
            .that()
                .resideInAnyPackage("com.dogpals.training.service..")
            .or()
                .resideInAnyPackage("com.dogpals.training.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.dogpals.training.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
