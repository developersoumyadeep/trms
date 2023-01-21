package com.wbsedcl.trms.substation.log.domain.mapper;

import com.wbsedcl.trms.domain.valueobject.AssetId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyConsumptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyConsumptionResponse;
import com.wbsedcl.trms.substation.log.domain.entity.Consumption;
import org.springframework.stereotype.Component;

@Component
public class EnergyConsumptionDataMapper {
    public LogEnergyConsumptionResponse EnergyConsumptionToLogEnergyConsumptionResponse(Consumption consumption) {
        String message = "Consumption for meter no " +
                consumption.getEnergyMeterNo() +
                " logged at " +
                consumption.getTime() +
                " hrs";
        return new LogEnergyConsumptionResponse(message, consumption.getId().getValue());
    }

    public Consumption LogEnergyConsumptionCommandToConsumption(LogEnergyConsumptionCommand command) {
       return Consumption.newBuilder()
                .assetId(new AssetId(command.getAssetId()))
                .energyMeterNo(command.getEnergyMeterNo())
                .energyUnit(command.getEnergyUnit())
                .consumption(command.getConsumption())
                .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                .multiplyingFactor(command.getMultiplyingFactor())
                .recordedBy(new UserId(command.getRecordedBy()))
                .build();
    }
}
