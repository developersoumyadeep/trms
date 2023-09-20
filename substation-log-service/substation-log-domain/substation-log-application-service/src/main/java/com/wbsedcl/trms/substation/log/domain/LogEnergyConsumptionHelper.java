package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyConsumptionCommand;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.mapper.EnergyConsumptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Slf4j
public class LogEnergyConsumptionHelper {
    private final SubstationLogDomainService substationLogDomainService;
    private final SubstationLogRepository substationLogRepository;
    private final EnergyConsumptionDataMapper mapper;
    public LogEnergyConsumptionHelper(SubstationLogDomainService substationLogDomainService,
                                      SubstationLogRepository substationLogRepository,
                                      EnergyConsumptionDataMapper mapper) {
        this.substationLogDomainService = substationLogDomainService;
        this.substationLogRepository = substationLogRepository;
        this.mapper = mapper;
    }
    @Transactional
    public EnergyConsumptionLoggedEvent persistEnergyConsumption(LogEnergyConsumptionCommand command) {
        //1. get domain object from command
        EnergyConsumption energyConsumption = mapper.LogEnergyConsumptionCommandToEnergyConsumption(command);
        //2. validate and initiate energy energyConsumption (domain logic)
        EnergyConsumptionLoggedEvent event = substationLogDomainService.validateAndInitiateConsumption(energyConsumption);
        //3. save the energy energyConsumption using repository
        substationLogRepository.save(energyConsumption);
        //4. log the operation
        log.info("Energy energyConsumption saved with uuid {}", energyConsumption.getId().getValue());
        //5. return the com.wbsedcl.hr.management.domain.event with the saved energy energyConsumption data
        return event;
    }
}
