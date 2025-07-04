package com.example.VMSVehicleService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VmsVehicleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmsVehicleServiceApplication.class, args);
	}

}
