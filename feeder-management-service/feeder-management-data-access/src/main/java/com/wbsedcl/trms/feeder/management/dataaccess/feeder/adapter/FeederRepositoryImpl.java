package com.wbsedcl.trms.feeder.management.dataaccess.feeder.adapter;

import com.wbsedcl.trms.feeder.management.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.feeder.management.dataaccess.feeder.mapper.FeederDataAccessMapper;
import com.wbsedcl.trms.feeder.management.dataaccess.feeder.repository.FeederJPARepository;
import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.feeder.management.domain.ports.output.repository.FeederRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FeederRepositoryImpl implements FeederRepository {

    private final FeederJPARepository feederJPARepository;
    private final FeederDataAccessMapper feederDataAccessMapper;

    public FeederRepositoryImpl(FeederJPARepository feederJPARepository, FeederDataAccessMapper feederDataAccessMapper) {
        this.feederJPARepository = feederJPARepository;
        this.feederDataAccessMapper = feederDataAccessMapper;
    }

    @Override
    public Feeder saveFeeder(Feeder feeder) {
        FeederEntity feederEntityToBeSaved = feederDataAccessMapper.feederToFeederEntity(feeder);
        FeederEntity savedFeederEntity = feederJPARepository.save(feederEntityToBeSaved);
        return feederDataAccessMapper.feederEntityToFeeder(savedFeederEntity);
    }

    @Override
    public Optional<Feeder> findFeeder(String feederId) {
        Optional<FeederEntity> foundFeederEntity = feederJPARepository.findById(feederId);
        if (foundFeederEntity.isPresent()) {
            return Optional.of(feederDataAccessMapper.feederEntityToFeeder(foundFeederEntity.get()));
        }
        return Optional.empty();
    }
}
