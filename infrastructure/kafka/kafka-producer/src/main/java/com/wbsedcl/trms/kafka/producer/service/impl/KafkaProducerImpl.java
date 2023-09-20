package com.wbsedcl.trms.kafka.producer.service.impl;

import com.wbsedcl.trms.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K,V> {

    private final KafkaTemplate<K,V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message) {
        log.info("Sending message={} to topic={}", message,topicName);
        CompletableFuture<SendResult<K,V>> kafkaResultFuture = kafkaTemplate.send(topicName, key,  message);
        kafkaResultFuture.whenComplete((result, exc)-> {
            if (exc != null){
                log.error("Exception occurred while sending kafka message", exc);
                throw new KafkaProducerException(result.getProducerRecord(), "Error occurred on producer with key: "+key+" and message: "+message, exc);
            } else {
                log.info("Sent message {} to topic {} at partition {} offset {}", result.getProducerRecord().value(),
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }

    @PreDestroy
    public void close(){
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer! ");
            kafkaTemplate.destroy();
        }
    }
}
