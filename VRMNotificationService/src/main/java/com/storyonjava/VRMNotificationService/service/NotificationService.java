package com.storyonjava.VRMNotificationService.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	@KafkaListener(topics = "booking-events", groupId = "notification-group")
    public void handleBookingEvent(String message) {
        System.out.println("📨 Received Booking Event: " + message);
        
        // TODO: Parse message (JSON) and send email/notification
        // Example: sendEmailToUser(message);
    }

}
