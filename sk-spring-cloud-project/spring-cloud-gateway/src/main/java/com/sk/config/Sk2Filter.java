package com.sk.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2021/2/26 10:46
 */
@Configuration
public class Sk2Filter implements GatewayFilter {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r ->
                        r.path("/server/**")
                                .filters(
                                        f -> f.stripPrefix(1)
                                                .filters(new Sk2Filter())
                                )
                                .uri("lb://spring-cloud-client-demo")
                )
                .build();
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("gatewayfilter");
        ServerHttpRequest request = exchange.getRequest();
        request.mutate().header("sk2", "自定义添加头信息").build();
        return chain.filter(exchange);
    }
}
