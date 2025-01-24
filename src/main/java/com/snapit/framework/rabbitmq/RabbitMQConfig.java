package com.snapit.framework.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange framesExtractionFinishedExchange() {
        return new TopicExchange("frames-extraction-finished-exchange");
    }

    @Bean
    public TopicExchange framesExtractionFailedExchange() {
        return new TopicExchange("frames-extraction-failed-exchange");
    }

    @Bean
    public Queue framesExtractionFinishedQueue() {
        return new Queue("frames-extraction-finished-media-manager-queue", true);
    }

    @Bean
    public Queue framesExtractionFailedQueue() {
        return new Queue("frames-extraction-failed-media-manager-queue", true);
    }

    @Bean
    public Binding framesExtractionFinishedBinding(Queue framesExtractionFinishedQueue, TopicExchange framesExtractionFinishedExchange) {
        return BindingBuilder.bind(framesExtractionFinishedQueue).to(framesExtractionFinishedExchange).with("frames");
    }

    @Bean
    public Binding framesExtractionFailedBinding(Queue framesExtractionFailedQueue, TopicExchange framesExtractionFailedExchange) {
        return BindingBuilder.bind(framesExtractionFailedQueue).to(framesExtractionFailedExchange).with("frames");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}