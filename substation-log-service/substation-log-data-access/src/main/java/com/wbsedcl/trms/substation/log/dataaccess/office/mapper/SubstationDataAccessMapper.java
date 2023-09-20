package com.wbsedcl.trms.substation.log.dataaccess.office.mapper;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.mapper.FeederDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.SubstationEntity;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.Substation;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubstationDataAccessMapper {

    private final FeederRepository feederRepository;
    private final FeederDataAccessMapper feederDataAccessMapper;

    public SubstationDataAccessMapper(FeederRepository feederRepository, FeederDataAccessMapper feederDataAccessMapper) {
        this.feederRepository = feederRepository;
        this.feederDataAccessMapper = feederDataAccessMapper;
    }
    public SubstationEntity substationToSubstationEntity(Substation substation) {
        List<Feeder> feeders = substation.getIncomingSourceFeeders();
        List<FeederEntity> feederEntities = new ArrayList<>();
        for (Feeder feeder : feeders) {
            feederEntities.add(feederDataAccessMapper.feederToFeederEntity(feeder));
        }
        return new SubstationEntity(substation.getId().getValue(),
                                    substation.getOfficeText(),
                                    substation.getSubstationContactNumber(),
                                    feederEntities);
    }

    public Substation substaionEntityToSubstation(SubstationEntity substationEntity) {
        List<Feeder> feeders = new ArrayList<>();
        List<FeederEntity> feederEntities = substationEntity.getIncomingSourceFeeders();
        for (FeederEntity feederEntity : feederEntities) {
            feeders.add(feederDataAccessMapper.feederEntityToFeeder(feederEntity));
        }
        return new Substation(new OfficeId(substationEntity.getOfficeId()),
                substationEntity.getOfficeText(),
                substationEntity.getSubstationContactNumber(),
                feeders);
    }


}
