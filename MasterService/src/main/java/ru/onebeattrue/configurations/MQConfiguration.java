package ru.onebeattrue.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfiguration {
    @Bean
    public Queue createMasterQueue() {
        return new Queue("createMasterQueue", false);
    }

    @Bean
    public Queue getCatsMasterQueue() {
        return new Queue("getCatsMasterQueue", false);
    }

    @Bean
    public Queue getMasterByIdMasterQueue() {
        return new Queue("getMasterByIdMasterQueue", false);
    }

    @Bean
    public Queue getAllMasterQueue() {
        return new Queue("getAllMasterQueue", false);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}