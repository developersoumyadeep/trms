package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyMeterReadingCommand;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeterReading;
import com.wbsedcl.trms.substation.log.domain.event.EnergyMeterReadingLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.mapper.EnergyMeterReadingDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogEnergyMeterReadingHelper {
    private SubstationLogDomainService substationLogDomainService;
    private SubstationLogRepository substationLogRepository;
    private EnergyMeterReadingDataMapper energyMeterReadingDataMapper;

    public LogEnergyMeterReadingHelper(SubstationLogDomainService substationLogDomainService, SubstationLogRepository substationLogRepository, EnergyMeterReadingDataMapper mapper) {
        this.substationLogDomainService = substationLogDomainService;
        this.substationLogRepository = substationLogRepository;
        this.energyMeterReadingDataMapper = mapper;
    }

    public EnergyMeterReadingLoggedEvent persistEnergyMeterReading(LogEnergyMeterReadingCommand command) {
        //1. Get the domain object from the command
        EnergyMeterReading energyMeterReading = energyMeterReadingDataMapper.logEnergyMeterReadingCommandToEnergyMeterReading(command);
        //2. Initialize and validate the domain object
        EnergyMeterReadingLoggedEvent event = substationLogDomainService.validateAndInitiateEnergyMeterReading(energyMeterReading);
        //3. Save the energy meter reading using the repository
        substationLogRepository.save(energyMeterReading);
        //4. Log the operation
        log.info("Energy meter reading saved with id {}", energyMeterReading.getId().getValue());
        //5. Return the event
        return event;
    }
}
