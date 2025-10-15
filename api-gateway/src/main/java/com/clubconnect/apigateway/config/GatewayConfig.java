package com.clubconnect.apigateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced; // <-- IMPORT ADDED
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient; 

@Configuration
@EnableWebFluxSecurity
public class GatewayConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    GatewayConfig(){

    }
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().permitAll()
            )
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        return http.build();
    }
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("clubservice", r -> r.path("/clubs/**")
                // Corrected: Use the hyphenated name
                .uri("lb://club-service"))

            .route("eventservice", r -> r.path("/events/**")
                // Corrected: Use the hyphenated name
                .uri("lb://event-service"))

            .route("memberservice", r -> r.path("/members/**")
                // Corrected: Use the hyphenated name
                .uri("lb://member-service"))

            .route("registrationservice", r -> r.path("/registrations/**")
                // Corrected: Use the hyphenated name
                .uri("lb://registration-service"))

            .route("authservice", r -> r.path("/auth/**")
                // Corrected: Use the hyphenated name
                .uri("lb://auth-service"))
                
            .build();
    }
}
