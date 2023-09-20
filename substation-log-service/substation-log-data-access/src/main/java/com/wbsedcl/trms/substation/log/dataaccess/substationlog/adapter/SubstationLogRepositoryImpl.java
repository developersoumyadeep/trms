package com.wbsedcl.trms.substation.log.dataaccess.substationlog.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.mapper.FeederDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.repository.FeederJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.EnergyConsumptionEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.InterruptionEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.LoadRecordEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.mapper.SubstationLogDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.EnergyConsumptionJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.InterruptionLogJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.LoadRecordJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class SubstationLogRepositoryImpl implements SubstationLogRepository {

    private final InterruptionLogJpaRepository interruptionLogJpaRepository;
    private final EnergyConsumptionJpaRepository energyConsumptionJpaRepository;
    private final LoadRecordJpaRepository loadRecordJpaRepository;

    private final FeederJpaRepository feederJpaRepository;

    private final SubstationLogDataAccessMapper mapper;

    private final FeederDataAccessMapper feederDataAccessMapper;

    public SubstationLogRepositoryImpl(InterruptionLogJpaRepository interruptionLogJpaRepository,
                                       EnergyConsumptionJpaRepository energyConsumptionJpaRepository,
                                       LoadRecordJpaRepository loadRecordJpaRepository,
                                       FeederJpaRepository feederJpaRepository, SubstationLogDataAccessMapper mapper, FeederDataAccessMapper feederDataAccessMapper) {
        this.interruptionLogJpaRepository = interruptionLogJpaRepository;
        this.energyConsumptionJpaRepository = energyConsumptionJpaRepository;
        this.loadRecordJpaRepository = loadRecordJpaRepository;
        this.feederJpaRepository = feederJpaRepository;
        this.mapper = mapper;
        this.feederDataAccessMapper = feederDataAccessMapper;
    }

    @Override
    public Interruption save(Interruption interruption) {
        InterruptionEntity interruptionEntity = mapper.interruptionToInterruptionEntity(interruption);
        log.info("interruptionEntity.creationTimeStamp():{}",interruptionEntity.getCreationTimeStamp());
        interruptionEntity = interruptionLogJpaRepository.save(interruptionEntity);
        return mapper.interruptionEntityToInterruption(interruptionEntity);
    }

    @Override
    public EnergyConsumption save(EnergyConsumption energyConsumption) {
        EnergyConsumptionEntity energyConsumptionEntity = mapper.energyConsumptionToEnergyConsumptionEntity(energyConsumption);
        energyConsumptionEntity = energyConsumptionJpaRepository.save(energyConsumptionEntity);
        return mapper.energyConsumptionEntityToEnergyConsumption(energyConsumptionEntity);
    }

    @Override
    public LoadRecord save(LoadRecord record) {
        LoadRecordEntity loadRecordEntity = mapper.loadRecordToLoadRecordEntity(record);
        loadRecordEntity = loadRecordJpaRepository.save(loadRecordEntity);
        return mapper.loadRecordEntityToLoadRecord(loadRecordEntity);
    }

    @Override
    public Optional<Interruption> findInterruptionById(String interruptionId) {
        Optional<InterruptionEntity> interruptionEntity = interruptionLogJpaRepository.findById(interruptionId);
        if (interruptionEntity.isPresent()){
            return Optional.of(mapper.interruptionEntityToInterruption(interruptionEntity.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<Feeder> getChildFeedersOf33kVFeeder(String feederId) {
        List<FeederEntity> childFeederEntities = feederJpaRepository.getChildFeedersOf33kVFeeder(feederId);
        List<Feeder> childFeeders = new ArrayList<>();
        for (FeederEntity entity :
                childFeederEntities) {
            childFeeders.add(feederDataAccessMapper.feederEntityToFeeder(entity));
        }
        return childFeeders;
    }

    @Override
    public List<Feeder> getChildFeedersOfPTR(String feederId) {
        List<FeederEntity> childFeederEntities = feederJpaRepository.getChildFeedersOfPTR(feederId);
        List<Feeder> childFeeders = new ArrayList<>();
        for (FeederEntity entity :
                childFeederEntities) {
            childFeeders.add(feederDataAccessMapper.feederEntityToFeeder(entity));
        }
        return childFeeders;
    }
}
