package com.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void handleOrderEvent(String message) {
        System.out.println("Received message from Kafka: " + message);

        // Logique pour envoyer une notification
        notificationService.sendEmail("client@example.com", "Nouvelle Commande", message);
    }
}
