package com.example.VRMBookingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
public class VrmBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrmBookingServiceApplication.class, args);
	}

}
