package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;

import java.util.List;
import java.util.Optional;

public interface SubstationLogRepository {
    Interruption save(Interruption interruption);
    EnergyConsumption save(EnergyConsumption energyConsumption);
    LoadRecord save(LoadRecord record);
    Optional<Interruption> findInterruptionById(String interruptionId);
    List<Feeder> getChildFeedersOf33kVFeeder(String feederId);
    List<Feeder> getChildFeedersOfPTR(String feederId);
}
