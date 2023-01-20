package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.Consumption;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.event.LoadRecordLoggedEvent;

import java.util.Optional;

public interface SubstationLogRepository {
    Interruption save(Interruption interruption);

    Optional<Interruption> findInterruptionByRefId(String referenceId);

    Consumption save(Consumption consumption);

    LoadRecord save(LoadRecord record);
}
