package com.wbsedcl.trms.feeder.management.domain.event;

import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.domain.event.DomainEvent;

import java.time.LocalDateTime;

public class FeederDeletedEvent implements DomainEvent<Feeder> {
    private final Feeder feeder;
    private final LocalDateTime createdAt;

    public FeederDeletedEvent(Feeder feeder, LocalDateTime createdAt) {
        this.feeder = feeder;
        this.createdAt = createdAt;
    }

    public Feeder getFeeder() {
        return feeder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
