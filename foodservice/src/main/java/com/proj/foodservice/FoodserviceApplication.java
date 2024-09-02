package com.proj.foodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FoodserviceApplication {

	public static void main(String[] args) {
		
		
		
		SpringApplication.run(FoodserviceApplication.class, args);
	}

}
