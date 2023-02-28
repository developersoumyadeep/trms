package com.wbsedcl.trms.substation.log.dataaccess.feeder.mapper;

import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.valueobject.FeederId;
import org.springframework.stereotype.Component;

@Component
public class FeederDataAccessMapper {

    public FeederEntity feederToFeederEntity(Feeder feeder) {
        OfficeEntity substationOffice = OfficeEntity.builder().officeId((feeder.getSubstationOfficeId()).getValue()).build();
        return FeederEntity.builder()
                .feederId(feeder.getId().getValue())
                .feederName(feeder.getFeederName())
                .feederType(feeder.getFeederType())
                .energyMeterNo(feeder.getEnergyMeterNo())
                .substationOffice(substationOffice)
                .voltageLevel(feeder.getVoltageLevel())
                .build();
    }
    
    public Feeder feederEntityToFeeder(FeederEntity feederEntity) {
        return Feeder.newBuilder()
                .feederId(new FeederId(feederEntity.getFeederId()))
                .feederName(feederEntity.getFeederName())
                .feederType(feederEntity.getFeederType())
                .energyMeterNo(feederEntity.getEnergyMeterNo())
                .substationOfficeId(new OfficeId(feederEntity.getSubstationOffice().getOfficeId()))
                .voltageLevel(feederEntity.getVoltageLevel())
                .build();
    }
}
