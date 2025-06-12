package com.example.VRMUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VrmUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrmUserServiceApplication.class, args);
	}

}
