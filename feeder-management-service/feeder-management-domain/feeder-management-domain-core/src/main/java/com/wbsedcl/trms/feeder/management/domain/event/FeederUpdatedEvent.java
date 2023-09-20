package com.wbsedcl.trms.feeder.management.domain.event;

import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.domain.event.DomainEvent;

import java.time.LocalDateTime;

public class FeederUpdatedEvent implements DomainEvent<Feeder> {

    private final Feeder updatedFeeder;

    private final LocalDateTime createdAt;

    public FeederUpdatedEvent(Feeder updatedFeeder, LocalDateTime createdAt) {
        this.updatedFeeder = updatedFeeder;
        this.createdAt = createdAt;
    }

    public Feeder getUpdatedFeeder() {
        return updatedFeeder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
