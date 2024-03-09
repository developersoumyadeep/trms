package com.wbsedcl.trms.substation.log.dataaccess.office.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.mapper.OfficeDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.office.repository.OfficeJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.Office;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.OfficeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OfficeRepositoryImpl implements OfficeRepository {

    private final Logger logger = LoggerFactory.getLogger(OfficeRepositoryImpl.class);
    private final OfficeJpaRepository officeJpaRepository;
    private final OfficeDataAccessMapper mapper;

    public OfficeRepositoryImpl(OfficeJpaRepository officeJpaRepository, OfficeDataAccessMapper mapper) {
        this.officeJpaRepository = officeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Office> findOffice(String officeId) {
        logger.debug("Getting office with id {} from db", officeId);
        Optional<OfficeEntity> entity = officeJpaRepository.findByOfficeId(officeId);
        if (entity.isPresent()){
            return Optional.of(mapper.officeEntityToOffice(entity.get()));
        }
        return Optional.empty();
    }
}
