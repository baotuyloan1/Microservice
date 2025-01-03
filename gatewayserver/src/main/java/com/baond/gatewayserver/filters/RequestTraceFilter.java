package com.baond.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// an order of execution for filters.
@Order(1)
@Component
//implements GlobalFilter whenever want filters to be executed for all kinds of traffic.
public class RequestTraceFilter implements GlobalFilter {

//    private FilterUtility filterUtility;
//
//    RequestTraceFilter(FilterUtility filterUtility) {
//        this.filterUtility = filterUtility;
//    }

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);


    /**
     *
     * Mono indicates a single object, whereas flux indicates a collection of objects.
     * @param exchange - we can access the request and response associated with the exchange
     * @param chain - indie GatewayFilterChain there can be any number of filters that we can configure.
     * @return - not returning anything => use void
     * since we don't have collection of objects and we are not returning anything. -> Mono<Void></Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)) {
            logger.debug("easyBank-correlation-id found in RequestTraceFilter: {}", FilterUtility.getCorrelationId(requestHeaders));
        } else {
            String correlationId = FilterUtility.generateCorrelationId();
            exchange = FilterUtility.setCorrelationId(exchange, correlationId);
            logger.debug("easyBank-correlation-id generated in RequestTraceFilter: {}", correlationId);
        }
        return chain.filter(exchange);
    }


    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (FilterUtility.getCorrelationId(requestHeaders) != null) {
            return true;
        }else {
            return false;
        }
    }
}
