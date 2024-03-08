package com.wbsedcl.trms.substation.log.dataaccess.office.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.mapper.OfficeDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.office.repository.OfficeJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.Office;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.OfficeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OfficeRepositoryImpl implements OfficeRepository {

    private final OfficeJpaRepository officeJpaRepository;
    private final OfficeDataAccessMapper mapper;

    public OfficeRepositoryImpl(OfficeJpaRepository officeJpaRepository, OfficeDataAccessMapper mapper) {
        this.officeJpaRepository = officeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Office> findOffice(String officeId) {
        Optional<OfficeEntity> entity = officeJpaRepository.findById(officeId);
        if (entity.isPresent()){
            return Optional.of(mapper.officeEntityToOffice(entity.get()));
        }
        return Optional.empty();
    }
}
