package com.wbsedcl.trms.substation.log.domain.event;

import com.wbsedcl.trms.domain.event.DomainEvent;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeterReading;

import java.time.LocalDateTime;

public class EnergyMeterReadingLoggedEvent implements DomainEvent<EnergyMeterReading> {
    private final EnergyMeterReading energyMeterReading;
    private final LocalDateTime createdAt;

    public EnergyMeterReadingLoggedEvent(EnergyMeterReading energyMeterReading, LocalDateTime createdAt) {
        this.energyMeterReading = energyMeterReading;
        this.createdAt = createdAt;
    }

    public EnergyMeterReading getEnergyMeterReading() {
        return energyMeterReading;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
