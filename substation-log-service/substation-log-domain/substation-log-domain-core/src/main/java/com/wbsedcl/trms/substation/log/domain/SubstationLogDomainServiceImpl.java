package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.entity.Consumption;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Logger;

@Slf4j
public class SubstationLogDomainServiceImpl implements SubstationLogDomainService {

    private static final Logger logger =Logger.getLogger(SubstationLogDomainServiceImpl.class.getName());

    @Override
    public InterruptionLoggedEvent validateAndInitiateInterruption(Interruption interruption) {
        interruption.validateInterruption();
        interruption.initializeInterruption();
        log.info("Interruption created with id {}",interruption.getId().getValue());
        return new InterruptionLoggedEvent(interruption, LocalDateTime.now());
    }

    @Override
    public InterruptionRestoredEvent restoreInterruption(Interruption interruption, LocalDate endDate, LocalTime endTime, UserId restoredBy) {
        interruption.restoreInterruption(endDate,endTime,restoredBy );
        log.info("Interruption with id {} restored", interruption.getId().getValue());
        return new InterruptionRestoredEvent(interruption, LocalDateTime.now());
    }

    @Override
    public EnergyConsumptionLoggedEvent validateAndInitiateConsumption(Consumption consumption) {
        consumption.validate();
        consumption.initialize();
        log.info("Energy consumption record created with id {}",consumption.getId().getValue());
        return new EnergyConsumptionLoggedEvent(consumption, LocalDateTime.now());
    }

    @Override
    public void validateAndInitiateLoadRecord(LoadRecord loadRecord) {
        loadRecord.validate();
        loadRecord.initialize();
        log.info("Load record created with id {}",loadRecord.getId().getValue());
    }
}
