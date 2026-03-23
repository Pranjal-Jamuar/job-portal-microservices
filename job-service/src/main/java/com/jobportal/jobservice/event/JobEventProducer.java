package com.jobportal.jobservice.event;

import com.jobportal.jobservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public JobEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJobCreated(Object job) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_CREATED,
                job
        );
    }

    public void sendJobClosed(Object job) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_CLOSED,
                job
        );
    }
}