package com.example.VRMPaymentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VrmPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrmPaymentServiceApplication.class, args);
	}

}
