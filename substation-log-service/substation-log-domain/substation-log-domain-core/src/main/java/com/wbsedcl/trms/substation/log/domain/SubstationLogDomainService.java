package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.entity.Consumption;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SubstationLogDomainService {
    InterruptionLoggedEvent validateAndInitiateInterruption(Interruption interruption);
    InterruptionRestoredEvent restoreInterruption(Interruption interruption, LocalDate endDate, LocalTime endTime, UserId restoredBy);
    EnergyConsumptionLoggedEvent validateAndInitiateConsumption(Consumption consumption);
    void validateAndInitiateLoadRecord(LoadRecord loadRecord);
}
