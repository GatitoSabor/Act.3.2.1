package com.kafka.controller;

import com.kafka.config.KafkaTopicConfig;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    private static final int PARTITION_COUNT = 3;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AtomicInteger nextPartition = new AtomicInteger();

    public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/health")
    public String health() {
        return "Aplicacion de Kafka Tutorial esta corriendo en puerto 8888";
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendGet(@RequestParam String message,
                                          @RequestParam(required = false) String key) {
        return send(message, key);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendPost(@RequestParam String message,
                                           @RequestParam(required = false) String key) {
        return send(message, key);
    }

    private ResponseEntity<String> send(String message, String key) {
        if (key == null || key.isBlank()) {
            int partition = Math.floorMod(nextPartition.getAndIncrement(), PARTITION_COUNT);
            kafkaTemplate.send(KafkaTopicConfig.TOPIC, partition, null, message);
            return ResponseEntity.accepted().body("Mensaje enviado sin key a la particion " + partition + ": " + message);
        }

        kafkaTemplate.send(KafkaTopicConfig.TOPIC, key, message);
        return ResponseEntity.accepted().body("Mensaje enviado con key '" + key + "': " + message);
    }
}
