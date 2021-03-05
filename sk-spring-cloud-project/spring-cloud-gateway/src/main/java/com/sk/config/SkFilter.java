package com.sk.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.springframework.web.server.session.InMemoryWebSessionStore;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2021/2/26 10:46
 */
@Configuration
public class SkFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("GlobalFilter");
        ServerHttpRequest request = exchange.getRequest();
        WebSession memoryWebSession = exchange.getSession().block();
        //添加session信息
        memoryWebSession.getAttributes().put("username", "sk");
        HttpHeaders headers = request.getHeaders();
        //不能这样追加，因为header是readonly的
        //headers.add("add", "addd");
        request.mutate().header("sk", "自定义全局头部信息").build();
        return chain.filter(exchange);
    }
}
