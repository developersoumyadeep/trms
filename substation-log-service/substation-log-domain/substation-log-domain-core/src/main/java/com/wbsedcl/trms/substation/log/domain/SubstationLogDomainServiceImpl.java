package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.domain.valueobject.UserId;
//import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeterReading;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
//import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.EnergyMeterReadingLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;
import com.wbsedcl.trms.substation.log.domain.event.LoadRecordLoggedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
public class SubstationLogDomainServiceImpl implements SubstationLogDomainService {

    @Override
    public InterruptionLoggedEvent validateAndInitiateInterruption(Interruption interruption) {
        interruption.validateInterruption();
        interruption.initializeInterruption();
        log.info("Interruption created with id {}",interruption.getId().getValue());
        return new InterruptionLoggedEvent(interruption, LocalDateTime.now());
    }

    @Override
    public InterruptionRestoredEvent restoreInterruption(Interruption interruption, LocalDate endDate, LocalTime endTime, UserId restoredBy, String cause) {
        interruption.restoreInterruption(endDate,endTime,restoredBy, cause);
        log.info("Interruption with id {} restored", interruption.getId().getValue());
        return new InterruptionRestoredEvent(interruption, LocalDateTime.now());
    }

    @Override
    public EnergyMeterReadingLoggedEvent validateAndInitiateEnergyMeterReading(EnergyMeterReading energyMeterReading) {
        log.info("Validating and initializing new energy meter reading for energy meter "+energyMeterReading.getEnergyMeterNo());
        energyMeterReading.validate();
        energyMeterReading.initialize();
        log.info("Energy meter reading created with id {}", energyMeterReading.getId().getValue());
        return new EnergyMeterReadingLoggedEvent(energyMeterReading,LocalDateTime.now());
    }

    @Override
    public LoadRecordLoggedEvent validateAndInitiateLoadRecord(LoadRecord loadRecord) {
        loadRecord.validate();
        loadRecord.initialize();
        log.info("Load record created with id {}",loadRecord.getId().getValue());
        return new LoadRecordLoggedEvent(loadRecord, LocalDateTime.now());
    }
}
