package com.wbsedcl.trms.substation.log.domain.mapper;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyConsumptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyConsumptionResponse;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import org.springframework.stereotype.Component;

@Component
public class EnergyConsumptionDataMapper {
    public LogEnergyConsumptionResponse EnergyConsumptionToLogEnergyConsumptionResponse(EnergyConsumption energyConsumption) {
        String message = "Energy consumption for meter no " +
                energyConsumption.getEnergyMeterNo() +
                " logged at " +
                energyConsumption.getTime() +
                " hrs";
        return new LogEnergyConsumptionResponse(message, energyConsumption.getId().getValue());
    }

    public EnergyConsumption LogEnergyConsumptionCommandToEnergyConsumption(LogEnergyConsumptionCommand command) {
       return EnergyConsumption.newBuilder()
                .feederId(new FeederId(command.getFeederId()))
                .energyMeterNo(command.getEnergyMeterNo())
                .energyUnit(command.getEnergyUnit())
                .consumption(command.getConsumption())
                .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                .multiplyingFactor(command.getMultiplyingFactor())
                .recordedBy(new UserId(command.getRecordedBy()))
                .build();
    }
}
