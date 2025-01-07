package com.baond.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
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
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
										.setKeyResolver(userKeyResolver())))
						.uri("lb://CARDS"))
				.route(p -> p
						.path("/easybank/loans/**")
						.filters(f -> f.rewritePath("/easybank/loans/(?<remaining>.*)", "/${remaining}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryConfig ->
//									the number of retries
									retryConfig.setRetries(3)
//											only retries for get operation, because won't be any side effects whenever we are trying to invoke the get operation multiple times.
											.setMethods(HttpMethod.GET)
//											spring cloud gateway will wait for 100 milliseconds whenever it is trying to initiate the very first retry operation.
//											based factor, but it will not be higher than 1000 milliseconds
//											spring cloud gateway will apply the factor value on the previous backoff number to calculate the next backoff time but not higher than 1000 milliseconds
											.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)
								)
						)
						.uri("lb://LOANS"))
				.build();
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

	@Bean
	public RedisRateLimiter redisRateLimiter(){
		return new RedisRateLimiter(1,1,1);
	}

	@Bean
	public KeyResolver userKeyResolver(){
//	get header from the request. If there is no header, assign anonymous.
		return exchange -> Mono.justOrEmpty(exchange
				.getRequest()
				.getHeaders()
				.getFirst("user"))
				.defaultIfEmpty("anonymous");
	}
}
