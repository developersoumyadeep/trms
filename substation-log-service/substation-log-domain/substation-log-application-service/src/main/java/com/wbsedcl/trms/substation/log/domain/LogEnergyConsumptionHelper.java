package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyConsumptionCommand;
import com.wbsedcl.trms.substation.log.domain.entity.Consumption;
import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.mapper.EnergyConsumptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.AssetRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.OfficeRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.UserRepository;
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
        Consumption consumption = mapper.LogEnergyConsumptionCommandToConsumption(command);
        //2. validate and initiate energy consumption (domain logic)
        EnergyConsumptionLoggedEvent event = substationLogDomainService.validateAndInitiateConsumption(consumption);
        //3. save the energy consumption using repository
        substationLogRepository.save(consumption);
        //4. log the operation
        log.info("Energy consumption saved with uuid {}", consumption.getId().getValue());
        //5. return the event with the saved energy consumption data
        return event;
    }

}
