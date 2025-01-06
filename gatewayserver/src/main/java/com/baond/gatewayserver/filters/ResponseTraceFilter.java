package com.baond.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);


    /**
     * Whenever there is a retry attempt is happening on the Gateway server, the response filter that we have written to populate these easy correlation ID is going to be executed
     * Whenever a response comes, regardless of an exception response or a retry response
     *
     * @return
     */
    @Bean
    public GlobalFilter globalFilter() {
        logger.debug("Creating ResponseTraceFilter");
        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders requestHeader = exchange.getRequest().getHeaders();
            String correlationId = FilterUtility.getCorrelationId(requestHeader);

            if (!exchange.getResponse().getHeaders().containsKey(FilterUtility.CORRELATION_ID)) {
                logger.debug("Updated correlation id to the outbound headers: {}", correlationId);
                exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
            }


        })));
    }


}
