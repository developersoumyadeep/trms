package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyConsumptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyConsumptionResponse;
import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.mapper.EnergyConsumptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.EnergyConsumptionLoggedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogEnergyConsumptionCommandHandler {
    private final LogEnergyConsumptionHelper logEnergyConsumptionHelper;

    private final EnergyConsumptionDataMapper mapper;

    public LogEnergyConsumptionCommandHandler(LogEnergyConsumptionHelper logEnergyConsumptionHelper,
                                              EnergyConsumptionDataMapper mapper) {
        this.logEnergyConsumptionHelper = logEnergyConsumptionHelper;
        this.mapper = mapper;
    }

    public LogEnergyConsumptionResponse logEnergyConsumption(LogEnergyConsumptionCommand command) {
        //1. persist the energy consumption
        EnergyConsumptionLoggedEvent event = logEnergyConsumptionHelper.persistEnergyConsumption(command);
        //2. log the info
        log.info("Energy consumption recorded with uuid {}",event.getConsumption().getId().getValue());
        //3. return the response
         return mapper.EnergyConsumptionToLogEnergyConsumptionResponse(event.getConsumption());
    }
}
