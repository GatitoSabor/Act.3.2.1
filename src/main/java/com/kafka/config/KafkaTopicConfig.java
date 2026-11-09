package com.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String TOPIC = "tutorial-particiones";

    @Bean
    NewTopic tutorialParticionesTopic() {
        return TopicBuilder.name(TOPIC).partitions(3).replicas(1).build();
    }
}
