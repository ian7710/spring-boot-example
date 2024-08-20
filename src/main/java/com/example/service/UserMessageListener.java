package com.example.service;

import com.example.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserMessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received message from RabbitMQ: " + message);

        // Process the message
        if (message.startsWith("User created with ID: ")) {
            handleUserCreation(message);
        } else if (message.startsWith("User deactivated with ID: ")) {
            handleUserDeactivation(message);
        } else {
            System.out.println("Unknown message type");
        }
    }

    private void handleUserCreation(String message) {
        // Extract the user ID from the message
        Long userId = extractUserIdFromMessage(message, "User created with ID: ");
        System.out.println("Processing user creation for User ID: " + userId);
        // Add your logic here (e.g., update a database, trigger other services, etc.)
    }

    private void handleUserDeactivation(String message) {
        // Extract the user ID from the message
        Long userId = extractUserIdFromMessage(message, "User deactivated with ID: ");
        System.out.println("Processing user deactivation for User ID: " + userId);
        // Add your logic here (e.g., update a database, notify other systems, etc.)
    }

    private Long extractUserIdFromMessage(String message, String prefix) {
        try {
            return Long.parseLong(message.replace(prefix, "").trim());
        } catch (NumberFormatException e) {
            System.out.println("Failed to extract User ID from message: " + message);
            return null;
        }
    }
}
