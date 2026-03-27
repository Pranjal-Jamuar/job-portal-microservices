package com.jobportal.notificationservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = "job.queue")
    public void handleMessage(String message) {
        System.out.println("📩 Notification received: " + message);
    }
}