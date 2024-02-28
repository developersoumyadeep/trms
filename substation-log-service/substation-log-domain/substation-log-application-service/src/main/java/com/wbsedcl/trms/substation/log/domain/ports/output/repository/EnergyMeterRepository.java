package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeter;

import java.util.Optional;

public interface EnergyMeterRepository {
    Optional<EnergyMeter> findEnergyMeterBySerialNumber(String energyMeterSerialNumber);
}
