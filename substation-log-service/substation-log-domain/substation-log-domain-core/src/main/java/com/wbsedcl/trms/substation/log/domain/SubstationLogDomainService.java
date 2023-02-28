package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.LoadRecordLoggedEvent;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SubstationLogDomainService {
    InterruptionLoggedEvent validateAndInitiateInterruption(Interruption interruption);
    InterruptionRestoredEvent restoreInterruption(Interruption interruption, LocalDate endDate, LocalTime endTime, UserId restoredBy);
    EnergyConsumptionLoggedEvent validateAndInitiateConsumption(EnergyConsumption energyConsumption);
    LoadRecordLoggedEvent validateAndInitiateLoadRecord(LoadRecord loadRecord);
}
