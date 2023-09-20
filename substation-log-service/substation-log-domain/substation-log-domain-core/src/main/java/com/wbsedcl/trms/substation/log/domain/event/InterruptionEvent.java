package com.wbsedcl.trms.substation.log.domain.event;

import com.wbsedcl.trms.domain.event.DomainEvent;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;

import java.time.LocalDateTime;

public class InterruptionEvent implements DomainEvent<Interruption> {

    private final Interruption interruption;

    private final LocalDateTime createdAt;

    public InterruptionEvent(Interruption interruption, LocalDateTime createdAt) {
        this.interruption = interruption;
        this.createdAt = createdAt;
    }

    public Interruption getInterruption() {
        return interruption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
