package com.abdulkadir.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user_route", r -> r.path("/user/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://user"))
                .route("order_route", r -> r.path("/order/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://order"))
                .route("advert_route", r -> r.path("/advert/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://advert"))
                .build();
    }
}