package com.wbsedcl.trms.substation.log.domain.event;

import com.wbsedcl.trms.domain.event.DomainEvent;
import com.wbsedcl.trms.substation.log.domain.entity.Consumption;

import java.time.LocalDateTime;

public class EnergyConsumptionLoggedEvent implements DomainEvent<Consumption> {
    private final Consumption consumption;
    private final LocalDateTime createdAt;

    public EnergyConsumptionLoggedEvent(Consumption consumption, LocalDateTime createdAt) {
        this.consumption = consumption;
        this.createdAt = createdAt;
    }

    public Consumption getConsumption() {
        return this.consumption;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
