package com.example.springreactive.reactivespring.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProfileEndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> routes(ProfileHandler handler) {
        return route(i(GET("/profiles")), handler::all)
                .andRoute(i(GET("/profiles/{id}")), handler::getById)
                .andRoute(i(DELETE("/profiles/{id}")), handler::deleteById)
                .andRoute(i(POST("/profiles")), handler::create)
                .andRoute(i(PUT("/profiles/{id}")), handler::updateById);
    }

    private static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }
}