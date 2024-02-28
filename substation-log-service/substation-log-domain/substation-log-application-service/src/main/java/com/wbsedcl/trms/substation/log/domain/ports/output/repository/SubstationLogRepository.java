package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

//import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.dto.message.EnergyMeterReadingDTO;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeterReading;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;

import java.util.List;
import java.util.Optional;

public interface SubstationLogRepository {
    Interruption save(Interruption interruption);
    LoadRecord save(LoadRecord record);
    EnergyMeterReading save(EnergyMeterReading energyMeterReading);

    Optional<Interruption> findInterruptionById(String interruptionId);
    List<Feeder> getChildFeedersOf33kVFeeder(String feederId);
    List<Feeder> getChildFeedersOfPTR(String feederId);
    List<Interruption> findAllInterruptionsBySubstationOfficeId(String substationOfficeId);
    List<EnergyMeterReading> findAllLatestEnergyMeterReadingsAgainstSubstationOfficeId(String substationOfficeId);
}
