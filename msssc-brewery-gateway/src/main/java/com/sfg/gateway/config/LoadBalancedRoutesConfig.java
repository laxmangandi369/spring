package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LoadBalancedRoutesConfig {

    @Bean
    public RouteLocator loadBalancedRoutes(RouteLocatorBuilder builder){

        return builder.routes()
                .route(r -> r.path("/api/v2/beer*", "/api/v2/beer/*")
                        // "lb" is for load balanced - and this is how the Gateway will look up services on Eureka
                        .uri("lb://beer-service")
                        .id("beer-service"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("lb://order-service")
                        .id("order-service"))
                .route(r -> r.path("/api/v1/beer/*/inventory")
                        .uri("lb://inventory-service")
                        .id("inventory-service"))
                .build();
    }
}
