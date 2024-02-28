package com.wbsedcl.trms.substation.log.domain.mapper;

import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyMeterReadingCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyMeterReadingResponse;
import com.wbsedcl.trms.substation.log.domain.dto.message.EnergyMeterReadingDTO;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeter;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeterReading;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.EnergyMeterRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnergyMeterReadingDataMapper {

    private final EnergyMeterRepository energyMeterRepository;
    private final SubstationLogRepository substationLogRepository;

    public EnergyMeterReadingDataMapper(EnergyMeterRepository energyMeterRepository, SubstationLogRepository substationLogRepository) {
        this.energyMeterRepository = energyMeterRepository;
        this.substationLogRepository = substationLogRepository;
    }

    public EnergyMeterReading logEnergyMeterReadingCommandToEnergyMeterReading(LogEnergyMeterReadingCommand command) {
        EnergyMeter energyMeter = energyMeterRepository
                .findEnergyMeterBySerialNumber(command.getEnergyMeterNo()).get();

        return EnergyMeterReading.builder()
                .feederId(new FeederId(command.getFeederId()))
                .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                .energyMeterNo(energyMeter.getSerialNumber())
                .meterReading(command.getMeterReading())
                .recordedBy(new UserId(command.getRecordedBy()))
                .energyUnit(energyMeter.getEnergyUnit())
                .build();
    }

    public EnergyMeterReadingDTO energyMeterReadingToEnergyMeterReadingDTO(EnergyMeterReading energyMeterReading) {
        return EnergyMeterReadingDTO.builder()
                .meterReadingId(energyMeterReading.getId().getValue().toString())
                .feederId(energyMeterReading.getFeederId().getValue())
                .meterSerialNumber(energyMeterReading.getEnergyMeterNo().getValue())
                .meterReading(energyMeterReading.getMeterReading())
                .recordDate(energyMeterReading.getRecordDate())
                .recordTime(energyMeterReading.getRecordTime())
                .energyUnit(energyMeterReading.getEnergyUnit())
                .build();
    }
}
