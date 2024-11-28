package com.baond.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
// The @EnableConfigServer annotation is used to enable the Spring Cloud Config Server functionality in this application.
// It allows the application to serve as a centralized configuration server,
// providing external configuration for distributed systems or microservices.
@EnableConfigServer
public class ConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
	}

}
