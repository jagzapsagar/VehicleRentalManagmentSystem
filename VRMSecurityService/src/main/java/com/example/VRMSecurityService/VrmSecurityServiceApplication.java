package com.example.VRMSecurityService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VrmSecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrmSecurityServiceApplication.class, args);
	}

}
