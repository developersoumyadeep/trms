package com.wbsedcl.trms.substation.log.dataaccess.energymeter.adapter;

import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterEntity;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.mapper.EnergyMeterDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.repository.EnergyMeterJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeter;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.EnergyMeterRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EnergyMeterRepositoryImpl implements EnergyMeterRepository {

    private final EnergyMeterJpaRepository energyMeterJpaRepository;
    private final EnergyMeterDataAccessMapper energyMeterDataAccessMapper;

    public EnergyMeterRepositoryImpl(EnergyMeterJpaRepository energyMeterJpaRepository, EnergyMeterDataAccessMapper energyMeterDataAccessMapper) {
        this.energyMeterJpaRepository = energyMeterJpaRepository;
        this.energyMeterDataAccessMapper = energyMeterDataAccessMapper;
    }

    @Override
    public Optional<EnergyMeter> findEnergyMeterBySerialNumber(String energyMeterSerialNumber) {
        EnergyMeterEntity energyMeterEntity = energyMeterJpaRepository.findEnergyMeterBySerialNo(energyMeterSerialNumber);
        if (energyMeterEntity != null) {
            return Optional.of(energyMeterDataAccessMapper.energyMeterEntityToEnergyMeter(energyMeterEntity));
        }
        return Optional.empty();
    }
}
