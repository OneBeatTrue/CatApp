package ru.onebeattrue.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfiguration {
    @Bean
    public Queue createCatQueue() {
        return new Queue("createCatQueue", false);
    }

    @Bean
    public Queue befriendCatQueue() {
        return new Queue("befriendCatQueue", false);
    }

    @Bean
    public Queue quarrelCatQueue() {
        return new Queue("quarrelCatQueue", false);
    }

    @Bean
    public Queue getFriendsCatQueue() {
        return new Queue("getFriendsCatQueue", false);
    }

    @Bean
    public Queue getAllCatsByColorCatQueue() {
        return new Queue("getAllCatsByColorCatQueue", false);
    }

    @Bean
    public Queue getCatsByColorCatQueue() {
        return new Queue("getCatsByColorCatQueue", false);
    }

    @Bean
    public Queue getCatByIdCatQueue() {
        return new Queue("getCatByIdCatQueue", false);
    }

    @Bean
    public Queue getAllCatQueue() {
        return new Queue("getAllCatQueue", false);
    }

    @Bean
    public Queue getCatsByMasterCatQueue() {
        return new Queue("getCatsByMasterCatQueue", false);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}