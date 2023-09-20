package com.wbsedcl.trms.substation.log.dataaccess.office.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.SubstationEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.mapper.SubstationDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.office.repository.SubstationJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.Substation;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubstationRepositoryImpl implements SubstationRepository {

    private final SubstationJpaRepository substationJpaRepository;
    private final SubstationDataAccessMapper substationDataAccessMapper;

    public SubstationRepositoryImpl(SubstationJpaRepository substationJpaRepository, SubstationDataAccessMapper substationDataAccessMapper) {
        this.substationJpaRepository = substationJpaRepository;
        this.substationDataAccessMapper = substationDataAccessMapper;
    }

    @Override
    public Optional<Substation> findSubstationByOfficeId(String officeId) {
        SubstationEntity substationEntity =  substationJpaRepository.findByOfficeId(officeId).get();
        Substation substation = substationDataAccessMapper.substaionEntityToSubstation(substationEntity);
        return Optional.of(substation);
    }
}
