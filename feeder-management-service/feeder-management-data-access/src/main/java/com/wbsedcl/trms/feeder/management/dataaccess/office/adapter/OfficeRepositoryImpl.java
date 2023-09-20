package com.wbsedcl.trms.feeder.management.dataaccess.office.adapter;

import com.wbsedcl.trms.feeder.management.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.feeder.management.dataaccess.office.mapper.OfficeDataAccessMapper;
import com.wbsedcl.trms.feeder.management.dataaccess.office.repository.OfficeJPARepository;
import com.wbsedcl.trms.feeder.management.domain.entity.Office;
import com.wbsedcl.trms.feeder.management.domain.ports.output.repository.OfficeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OfficeRepositoryImpl implements OfficeRepository {
    private final OfficeJPARepository officeJPARepository;
    private final OfficeDataAccessMapper officeDataAccessMapper;

    public OfficeRepositoryImpl(OfficeJPARepository officeJPARepository, OfficeDataAccessMapper officeDataAccessMapper) {
        this.officeJPARepository = officeJPARepository;
        this.officeDataAccessMapper = officeDataAccessMapper;
    }


    @Override
    public Optional<Office> findOffice(String officeId) {
        Optional<OfficeEntity> foundOfficeEntity = officeJPARepository.findById(officeId);
        if (foundOfficeEntity.isPresent()){
            return Optional.of(officeDataAccessMapper.officeEntityToOffice(foundOfficeEntity.get()));
        }
        return Optional.empty();
    }
}
