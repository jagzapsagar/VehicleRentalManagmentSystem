package com.storyonjava.VRMNotificationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyonjava.VRMNotificationService.UserDTO;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendUserCreatedEmail(UserDTO user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("🎉 Welcome to VRM System - Profile Created!");
        message.setText("Hello " + user.getFirstName() + ",\n\n" +
                "Your profile has been successfully created with the following details:\n\n" +
                "User ID: " + user.getUserId() + "\n" +
                "Name: " + user.getFirstName() + " " + user.getLastName() + "\n" +
                "Email: " + user.getEmail() + "\n\n" +
                "Thank you for registering!\n\n" +
                "- Vehicle Rental Management Team");
        
        mailSender.send(message);
        System.out.println("Mail Sent"+message);
    }
}
