package com.wbsedcl.trms.kafka.producer.service.impl;

import com.wbsedcl.trms.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
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
        ListenableFuture<SendResult<K,V>> kafkaResultFuture = kafkaTemplate.send(topicName, key,  message);
        kafkaResultFuture.addCallback(new ListenableFutureCallback<SendResult<K, V>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Exception occurred while sending kafka message", ex);
            }

            @Override
            public void onSuccess(SendResult<K, V> result) {
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
