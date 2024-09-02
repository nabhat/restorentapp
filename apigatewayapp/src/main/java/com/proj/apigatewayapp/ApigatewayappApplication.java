package com.proj.apigatewayapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApigatewayappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayappApplication.class, args);
	}
	
	@Bean
	public RouteLocator configureRoute(RouteLocatorBuilder builder) {
	       return builder.routes()
	      .route("foodservice_route", r->r.path("/client1/**").uri("lb://foodservice")) //dynamic routing
	      .route("customerservice_route", r->r.path("/client2/**").uri("lb://customerservice")) //dynamic routing
	      .build();
	    }

}
