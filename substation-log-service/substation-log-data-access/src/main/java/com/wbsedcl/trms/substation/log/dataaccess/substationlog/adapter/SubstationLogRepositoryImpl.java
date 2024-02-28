package com.wbsedcl.trms.substation.log.dataaccess.substationlog.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterFeederAssociationEntity;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.repository.EnergyMeterJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.mapper.FeederDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.repository.FeederJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.EnergyMeterReadingEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.InterruptionEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.LoadRecordEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.mapper.SubstationLogDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.EnergyMeterReadingJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.InterruptionLogJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.LoadRecordJpaRepository;
import com.wbsedcl.trms.substation.log.domain.dto.message.EnergyMeterReadingDTO;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeterReading;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SubstationLogRepositoryImpl implements SubstationLogRepository {

    private final InterruptionLogJpaRepository interruptionLogJpaRepository;
    private final LoadRecordJpaRepository loadRecordJpaRepository;
    private final FeederJpaRepository feederJpaRepository;
    private final EnergyMeterJpaRepository energyMeterJpaRepository;
    private final EnergyMeterReadingJpaRepository energyMeterReadingJpaRepository;
    private final SubstationLogDataAccessMapper mapper;
    private final FeederDataAccessMapper feederDataAccessMapper;

    public SubstationLogRepositoryImpl(InterruptionLogJpaRepository interruptionLogJpaRepository,
                                       LoadRecordJpaRepository loadRecordJpaRepository,
                                       FeederJpaRepository feederJpaRepository, EnergyMeterJpaRepository energyMeterJpaRepository, EnergyMeterReadingJpaRepository energyMeterReadingJpaRepository, SubstationLogDataAccessMapper mapper, FeederDataAccessMapper feederDataAccessMapper) {
        this.interruptionLogJpaRepository = interruptionLogJpaRepository;
        this.loadRecordJpaRepository = loadRecordJpaRepository;
        this.feederJpaRepository = feederJpaRepository;
        this.energyMeterJpaRepository = energyMeterJpaRepository;
        this.energyMeterReadingJpaRepository = energyMeterReadingJpaRepository;
        this.mapper = mapper;
        this.feederDataAccessMapper = feederDataAccessMapper;
    }

    @Override
    public Interruption save(Interruption interruption) {
        log.info("Mapping interruption to entity {}",interruption);
        InterruptionEntity interruptionEntity = mapper.interruptionToInterruptionEntity(interruption);
        log.info("InterruptionEntity mapped from interruption: {}",interruptionEntity);
        log.info("Saving interruption for feeder {} in the database", interruptionEntity.getFaultyFeeder().getFeederName());
        log.info("interruptionEntity.getRestoredByUser() : {}", interruptionEntity.getRestoredByUser().getUserId());
        interruptionEntity = interruptionLogJpaRepository.save(interruptionEntity);
        return mapper.interruptionEntityToInterruption(interruptionEntity);
    }

    @Override
    public LoadRecord save(LoadRecord record) {
        LoadRecordEntity loadRecordEntity = mapper.loadRecordToLoadRecordEntity(record);
        loadRecordEntity = loadRecordJpaRepository.save(loadRecordEntity);
        return mapper.loadRecordEntityToLoadRecord(loadRecordEntity);
    }

    @Override
    public EnergyMeterReading save(EnergyMeterReading energyMeterReading) {
        EnergyMeterReadingEntity energyMeterReadingEntity = mapper.energyMeterReadingToEnergyMeterReadingEntity(energyMeterReading);
        energyMeterReadingEntity = energyMeterReadingJpaRepository.save(energyMeterReadingEntity);
        return mapper.energyMeterReadingEntityToEnergyMeterReading(energyMeterReadingEntity);
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

    @Override
    public List<Interruption> findAllInterruptionsBySubstationOfficeId(String substationOfficeId) {
        log.info("Fetching all interruptions by substation office id from jpa repository");
        List<InterruptionEntity> interruptionEntities = interruptionLogJpaRepository.findAllBySubstationOfficeOfficeId(substationOfficeId);
        return interruptionEntities.stream().map(interruptionEntity -> mapper.interruptionEntityToInterruption(interruptionEntity)).collect(Collectors.toList());
    }

    @Override
    public List<EnergyMeterReading> findAllLatestEnergyMeterReadingsAgainstSubstationOfficeId(String substationOfficeId) {

        log.info("Fetching latest energy meter reading for all feeders by substation office id from jpa repository");
        List<EnergyMeterReadingEntity> entities = energyMeterReadingJpaRepository.getLatestEnergyMeterReadingBySubstationOfficeId(substationOfficeId);
        return entities.stream().map(mapper::energyMeterReadingEntityToEnergyMeterReading).collect(Collectors.toList());
    }
}
