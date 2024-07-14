package com.abdulkadir.advert.config;


import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class RabbitMQConfig {


    @Value(value = "${status.queue}")
    private String statusQueue;

    @Value(value = "${status.exchange}")
    private String exchange;

    @Value(value = "${status.routingKey}")
    private String routingKey;

    @Bean
    public Queue advertStatusQueue() {
        return new Queue(statusQueue, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(Queue advertStatusQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(advertStatusQueue).
                to(exchange).
                with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}