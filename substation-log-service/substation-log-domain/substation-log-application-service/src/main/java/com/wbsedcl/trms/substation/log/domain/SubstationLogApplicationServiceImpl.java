package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.ports.input.service.SubstationLogApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


@Service
public class SubstationLogApplicationServiceImpl implements SubstationLogApplicationService {

    private final LogInterruptionCommandHandler logInterruptionCommandHandler;
    private final RestoreInterruptionCommandHandler restoreInterruptionCommandHandler;
    private final LogEnergyConsumptionCommandHandler logEnergyConsumptionCommandHandler;
    private final LogLoadRecordCommandHandler logLoadRecordCommandHandler;

    public SubstationLogApplicationServiceImpl(LogInterruptionCommandHandler logInterruptionCommandHandler,
                                               RestoreInterruptionCommandHandler restoreInterruptionCommandHandler,
                                               LogEnergyConsumptionCommandHandler logEnergyConsumptionCommandHandler,
                                               LogLoadRecordCommandHandler logLoadRecordCommandHandler) {
        this.logInterruptionCommandHandler = logInterruptionCommandHandler;
        this.restoreInterruptionCommandHandler = restoreInterruptionCommandHandler;
        this.logEnergyConsumptionCommandHandler = logEnergyConsumptionCommandHandler;
        this.logLoadRecordCommandHandler = logLoadRecordCommandHandler;
    }

    @Override
    public LogInterruptionResponse logInterruption(LogInterruptionCommand command) {
        return logInterruptionCommandHandler.logInterruption(command);
    }

    @Override
    public LogInterruptionResponse logSourceChangeOver(LogSourceChangeOverInterruptionCommand command) {
        return logInterruptionCommandHandler.logSourceChangeOver(command);
    }

    @Override
    public RestoreInterruptionResponse restoreInterruption(RestoreInterruptionCommand command) {
       return restoreInterruptionCommandHandler.restoreInterruption(command);
    }

    @Override
    public LogEnergyConsumptionResponse logEnergyConsumption(@Valid LogEnergyConsumptionCommand command) {
        return logEnergyConsumptionCommandHandler.logEnergyConsumption(command);
    }

    @Override
    public LogLoadRecordResponse logLoadRecord(LogLoadRecordCommand command) {
        return logLoadRecordCommandHandler.logLoadRecord(command);
    }

    @Override
    public LogInterruptionResponse logMainPowerFail(LogInterruptionCommand command) {
        return logInterruptionCommandHandler.logMainPowerFail(command);
    }
}
