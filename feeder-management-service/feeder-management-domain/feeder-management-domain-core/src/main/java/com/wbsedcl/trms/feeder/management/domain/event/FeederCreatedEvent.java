package com.wbsedcl.trms.feeder.management.domain.event;

import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.domain.event.DomainEvent;

import java.time.LocalDateTime;

public class FeederCreatedEvent implements DomainEvent<Feeder> {
    private final Feeder createdFeeder;
    private final LocalDateTime createdAt;

    public FeederCreatedEvent(Feeder createdFeeder, LocalDateTime createdAt) {
        this.createdFeeder = createdFeeder;
        this.createdAt = createdAt;
    }

    public Feeder getCreatedFeeder() {
        return createdFeeder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
