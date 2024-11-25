package com.microservice.cards;

import com.microservice.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = "EasyBank Cards microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Bao Nguyen Duc",
						email = "nguyenducbaokey@gmail.com",
						url = "https://github.com/baotuyloan1"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EasyBank Cards microservice REST API Documentation",
				url = "https://github.com/baotuyloan1/Microservice/blob/d412b4a6a98ba4292f1653926c927ea0d85a2f61/Accounts/README.md"
		)
)
@EnableConfigurationProperties(value = CardsContactInfoDto.class)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
