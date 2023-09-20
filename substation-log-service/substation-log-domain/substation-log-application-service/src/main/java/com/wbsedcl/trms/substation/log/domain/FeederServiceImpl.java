package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeederServiceImpl implements FeederService{

    private final FeederRepository feederRepository;

    public FeederServiceImpl(FeederRepository feederRepository) {
        this.feederRepository = feederRepository;
    }

    @Override
    public List<Feeder> getFeedersBySubstationOfficeId(String substationOfficeId) {
        return feederRepository.findAllFeedersBySubstationOfficeId(substationOfficeId);
    }
}
