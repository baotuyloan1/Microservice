package com.baond.gatewayserver.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public class FilterUtility  {

    public static final String CORRELATION_ID = "easybank-correlation-id";

    public static String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID) != null){
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            return requestHeaderList.stream().findFirst().get();
        } else {
            return  null;
        }
    }

    public static String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public static ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value){
        ServerHttpRequest request = exchange.getRequest().mutate().header(name, value).build();
        return exchange.mutate().request(request).build();
    }

    public static ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId){
        return setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
