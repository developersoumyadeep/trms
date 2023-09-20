package com.wbsedcl.trms.kafka.consumer.service;

import org.apache.avro.specific.SpecificRecordBase;

import java.util.List;

public interface KafkaConsumer<T extends SpecificRecordBase> {
    public void receive(List<T> messages, List<Long> keys, List<Integer> partition, List<Long> offsets);
}
