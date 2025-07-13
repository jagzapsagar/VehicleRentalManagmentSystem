package com.example.VRMUserService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String topic = "user-event";

    public void sendUserCreatedEvent(String message) {
        kafkaTemplate.send(topic, message);
    }
}

