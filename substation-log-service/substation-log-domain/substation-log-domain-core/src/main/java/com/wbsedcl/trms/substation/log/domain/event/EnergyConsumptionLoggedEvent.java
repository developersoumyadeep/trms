package com.wbsedcl.trms.substation.log.domain.event;

import com.wbsedcl.trms.domain.event.DomainEvent;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;

import java.time.LocalDateTime;

public class EnergyConsumptionLoggedEvent implements DomainEvent<EnergyConsumption> {
    private final EnergyConsumption energyConsumption;
    private final LocalDateTime createdAt;

    public EnergyConsumptionLoggedEvent(EnergyConsumption energyConsumption, LocalDateTime createdAt) {
        this.energyConsumption = energyConsumption;
        this.createdAt = createdAt;
    }

    public EnergyConsumption getConsumption() {
        return this.energyConsumption;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
