package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.dto.message.EnergyMeterReadingDTO;
import com.wbsedcl.trms.substation.log.domain.dto.message.InterruptionDTO;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeterReading;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.mapper.EnergyMeterReadingDataMapper;
import com.wbsedcl.trms.substation.log.domain.mapper.InterruptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.input.service.SubstationLogApplicationService;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class SubstationLogApplicationServiceImpl implements SubstationLogApplicationService {

    private final LogInterruptionCommandHandler logInterruptionCommandHandler;
    private final RestoreInterruptionCommandHandler restoreInterruptionCommandHandler;
    private final LogEnergyMeterReadingCommandHandler logEnergyMeterReadingCommandHandler;
    private final LogLoadRecordCommandHandler logLoadRecordCommandHandler;
    private final SubstationLogRepository substationLogRepository;
    private final InterruptionDataMapper interruptionDataMapper;
    private final EnergyMeterReadingDataMapper energyMeterReadingDataMapper;
    Logger logger = Logger.getLogger(SubstationLogApplicationServiceImpl.class.getName());

    public SubstationLogApplicationServiceImpl(LogInterruptionCommandHandler logInterruptionCommandHandler,
                                               RestoreInterruptionCommandHandler restoreInterruptionCommandHandler, LogEnergyMeterReadingCommandHandler logEnergyMeterReadingCommandHandler,
//                                               LogEnergyConsumptionCommandHandler logEnergyConsumptionCommandHandler,
                                               LogLoadRecordCommandHandler logLoadRecordCommandHandler, SubstationLogRepository substationLogRepository, InterruptionDataMapper interruptionDataMapper, EnergyMeterReadingDataMapper energyMeterReadingDataMapper) {
        this.logInterruptionCommandHandler = logInterruptionCommandHandler;
        this.restoreInterruptionCommandHandler = restoreInterruptionCommandHandler;
        this.logEnergyMeterReadingCommandHandler = logEnergyMeterReadingCommandHandler;
//        this.logEnergyConsumptionCommandHandler = logEnergyConsumptionCommandHandler;
        this.logLoadRecordCommandHandler = logLoadRecordCommandHandler;
        this.substationLogRepository = substationLogRepository;
        this.interruptionDataMapper = interruptionDataMapper;
        this.energyMeterReadingDataMapper = energyMeterReadingDataMapper;
    }

    @Override
    public LogInterruptionResponse logInterruption(LogInterruptionCommand command) {
        return logInterruptionCommandHandler.logInterruption(command);
    }

    @Override
    public List<LogInterruptionResponse> logSourceChangeOver(LogSourceChangeOverInterruptionCommand command) {
        return logInterruptionCommandHandler.logSourceChangeOver(command);
    }

    @Override
    public RestoreInterruptionResponse restoreInterruption(RestoreInterruptionCommand command) {
        return restoreInterruptionCommandHandler.restoreInterruption(command);
    }

    @Override
    public LogEnergyMeterReadingResponse logEnergyMeterReading(LogEnergyMeterReadingCommand command) {
        return logEnergyMeterReadingCommandHandler.logEnergyMeterReading(command);
    }

    @Override
    public LogLoadRecordResponse logLoadRecord(LogLoadRecordCommand command) {
        return logLoadRecordCommandHandler.logLoadRecord(command);
    }

    @Override
    public List<LogInterruptionResponse> logMainPowerFail(MainPowerFailCommand command) {
        return logInterruptionCommandHandler.logMainPowerFail(command);
    }

    @Override
    public List<InterruptionDTO> getAllOpenInterruptionsBySubstationOfficeId(String substationOfficeId) {
        List<InterruptionDTO> dtos = substationLogRepository.findAllInterruptionsBySubstationOfficeId(substationOfficeId)
                .stream()
                .filter(interruption -> interruption.getInterruptionStatus() == InterruptionStatus.NOT_RESTORED)
                .map(interruptionDataMapper::interruptionToInterruptionDTO)
                .collect(Collectors.toList());
        logger.info("Following open interruptions were retrieved from database ..");
        dtos.forEach(dto -> logger.info(""+dto));
        return dtos;
    }

    @Override
    public List<EnergyMeterReadingDTO> getLatestEnergyMeterReadingsBySubstationOfficeId(String substationOfficeId) {
       List<EnergyMeterReading> energyMeterReadings = substationLogRepository.findAllLatestEnergyMeterReadingsAgainstSubstationOfficeId(substationOfficeId);
       return energyMeterReadings.stream().map(energyMeterReadingDataMapper::energyMeterReadingToEnergyMeterReadingDTO).collect(Collectors.toList());
    }
}
