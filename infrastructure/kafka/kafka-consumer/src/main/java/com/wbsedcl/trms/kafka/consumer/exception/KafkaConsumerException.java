package com.wbsedcl.trms.kafka.consumer.exception;

public class KafkaConsumerException extends RuntimeException{

    public KafkaConsumerException(String message) {
        super(message);
    }
}
