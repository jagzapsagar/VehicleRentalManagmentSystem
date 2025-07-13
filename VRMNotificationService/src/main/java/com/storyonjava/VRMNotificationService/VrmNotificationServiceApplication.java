package com.storyonjava.VRMNotificationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class VrmNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrmNotificationServiceApplication.class, args);
	}

}
