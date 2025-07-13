package com.storyonjava.VRMNotificationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyonjava.VRMNotificationService.UserDTO;

@Service
public class UserNotificationConsumer {
	
	@Autowired
    private EmailService emailService;

    @KafkaListener(topics = "user-event", groupId = "notification-group")
    public void handleUserCreatedEvent(String message) {
        System.out.println("📨 Received user created event: " + message);

        try {
            ObjectMapper mapper = new ObjectMapper();
            UserDTO user = mapper.readValue(message, UserDTO.class);

            // Send email to user.getEmail()
            System.out.println("📧 Sending welcome email to: " + user.getEmail());
            System.out.println(user);
            System.out.println("----------Calling email service-----------");
         // Call EmailService
            emailService.sendUserCreatedEmail(user);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

