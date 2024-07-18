package com.abdulkadir.gateway;

import com.abdulkadir.gateway.filter.JwtAuthenticationFilter;
import com.abdulkadir.gateway.filter.RouteValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.WebFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public WebFilter jwtAuthenticationFilter(RouteValidator routeValidator) {
		return new JwtAuthenticationFilter(routeValidator);
	}

	@Bean
	public RouteValidator routeValidator() {
		return new RouteValidator();
	}
}