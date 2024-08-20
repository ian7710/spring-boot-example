package com.example.service;

import com.example.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserNotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserCreationMessage(Long userId) {
        String message = "User created with ID: " + userId;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
        System.out.println("Message sent to RabbitMQ: " + message);
    }

    public void sendUserDeactivationMessage(Long userId) {
        String message = "User deactivated with ID: " + userId;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
        System.out.println("Message sent to RabbitMQ: " + message);
    }
}
