package com.baond.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator easyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p -> p
//						 predicate - path predicate.
						.path("/easybank/accounts/**")
//						pre-defined filter
						.filters(f -> f.rewritePath("/easybank/accounts/(?<remaining>.*)", "/${remaining}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
//										Whenever there is an exception happens, please invoke this fallback by forwarding the request to the contact support.
										.setFallbackUri("forward:/contactSupport")))
//						forward the request to the actual microservice.
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/easybank/cards/**")
						.filters(f -> f.rewritePath("/easybank/cards/(?<remaining>.*)", "/${remaining}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.route(p -> p
						.path("/easybank/loans/**")
						.filters(f -> f.rewritePath("/easybank/loans/(?<remaining>.*)", "/${remaining}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.build();
	}

}
