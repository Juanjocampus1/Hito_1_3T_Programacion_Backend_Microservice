package com.empresa.InventoryReportsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceInventoryReportsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceInventoryReportsApiApplication.class, args);
	}

}
