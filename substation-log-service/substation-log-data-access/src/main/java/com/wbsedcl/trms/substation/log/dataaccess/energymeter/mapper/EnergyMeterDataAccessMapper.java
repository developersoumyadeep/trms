package com.wbsedcl.trms.substation.log.dataaccess.energymeter.mapper;

import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterEntity;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeter;
import org.springframework.stereotype.Component;

@Component
public class EnergyMeterDataAccessMapper {
    public EnergyMeterEntity energyMeterToEnergyMeterEntity(EnergyMeter energyMeter) {
        return EnergyMeterEntity.builder()
                .energyMeterNo(energyMeter.getSerialNumber().getValue())
                .meterCTRatio(energyMeter.getMeterCTRatio())
                .meterPTRatio(energyMeter.getMeterPTRatio())
                .energyUnit(energyMeter.getEnergyUnit())
                .build();
    }

    public EnergyMeter energyMeterEntityToEnergyMeter(EnergyMeterEntity energyMeterEntity) {
        return EnergyMeter.builder()
                .serialNumber(new EnergyMeterSerialNumber(energyMeterEntity.getEnergyMeterNo()))
                .meterCTRatio(energyMeterEntity.getMeterCTRatio())
                .meterPTRatio(energyMeterEntity.getMeterPTRatio())
                .energyUnit(energyMeterEntity.getEnergyUnit())
                .build();
    }
}
