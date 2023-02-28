package com.wbsedcl.trms.substation.log.dataaccess.feeder.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.mapper.FeederDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.repository.FeederJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;

import java.util.Optional;

public class FeederRepositoryImpl implements FeederRepository {
    private FeederJpaRepository feederJpaRepository;
    private FeederDataAccessMapper mapper;
    @Override
    public Optional<Feeder> findFeeder(String feederId) {
        Optional<FeederEntity> entity = feederJpaRepository.findById(feederId);
        if (entity.isPresent()){
            return Optional.of(mapper.feederEntityToFeeder(entity.get()));
        }
        return Optional.empty();
    }
}
