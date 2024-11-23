package com.example.microserviceaccounts;

import com.example.microserviceaccounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Note: The three annotations above (@ComponentScan, @EnableJpaRepositories, and @EntityScan)
 * are not necessary if the application configuration class (this class) is in the same package
 * as the classes that you want to scan. Spring Boot will automatically scan the classes in the
 * same package and its sub-packages. However, if the application configuration class is in a
 * different package, you need to specify the packages that you want to scan explicitly using
 * these three annotations.
 */
//@ComponentScan("com.example.microserviceaccounts.controller")
//@EnableJpaRepositories("com.example.microserviceaccounts.repository")
//@EntityScan("com.example.microserviceaccounts.entity")
/**
 * @EnableJpaAuditing is a Spring Boot annotation used to enable JPA Auditing in the application.
 *
 * It is necessary for the following reasons:
 *
 * 1. Auditing: It allows the application to automatically populate auditing-related fields (such as
 *    createdDate, lastModifiedDate, createdBy, and lastModifiedBy) in entities. This is useful for
 *    maintaining a record of who created or modified an entity and when these actions were performed.
 *
 * 2. Configuration: The `auditorAwareRef = "auditAwareImpl"` attribute specifies the bean name of
 *    the `AuditorAware` implementation. This implementation provides the current auditor (user)
 *    information, which is then used to populate the auditing fields.
 *
 * 3. Simplification: Without this annotation, you would need to manually set the auditing
 *    information for each entity, which would be cumbersome and error-prone.
 */

/**
 * To configure JPA Auditing in a Spring Boot application, you need to follow
 * the following steps:
 *
 * Step 1: Add the `@EnableJpaAuditing` annotation to the application configuration
 * class.
 *
 * Step 2: Create an `AuditorAware` implementation that provides the current auditor
 * information. This implementation is responsible for providing the current user
 * information that will be used to populate the auditing fields.
 *
 * Step 3: Add the `@EntityListeners(AuditingEntityListener.class)` annotation to
 * the entity class that you want to audit. This annotation enables the auditing
 * feature for the entity.
 *
 * Step 4: Add the auditing fields to the entity class. The fields that you can
 * use are:
 *   - `@CreatedBy`: The user who created the entity.
 *   - `@LastModifiedBy`: The user who modified the entity.
 *   - `@CreatedDate`: The date and time when the entity was created.
 *   - `@LastModifiedDate`: The date and time when the entity was modified.
 *
 * With these steps, you can configure JPA Auditing in your Spring Boot application.
 */
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
@EnableConfigurationProperties(value = AccountsContactInfoDto.class)
@OpenAPIDefinition(
        info =@Info(
                title = "Accounts microservice REST API Documentation",
                description = "EasyBank Accounts microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                    name = "Bao Nguyen",
                        email = "nguyenducbaokey@gmail.com",
                        url = "https://github.com/baotuyloan1"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "EasyBank Accounts microservice REST API Documentation",
                url = "https://github.com/baotuyloan1"
        )
)
public class MicroserviceAccountsApplication {

    /**
     * This is the main entry point for the microservice.  It starts
     * the spring application.
     *
     * @param args Command line arguments.  These are ignored.
     */
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAccountsApplication.class, args);
    }

}
