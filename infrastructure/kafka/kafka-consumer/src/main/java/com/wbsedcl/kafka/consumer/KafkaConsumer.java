package com.wbsedcl.kafka.consumer;

import org.apache.avro.specific.SpecificRecordBase;

import java.util.List;

public interface KafkaConsumer<T extends SpecificRecordBase> {
    public void receive(List<T> message, List<Long> keys, List<Integer> partition, List<Long> offsets);
}
