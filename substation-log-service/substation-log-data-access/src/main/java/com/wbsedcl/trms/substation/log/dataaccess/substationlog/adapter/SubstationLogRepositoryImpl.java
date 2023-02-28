package com.wbsedcl.trms.substation.log.dataaccess.substationlog.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.EnergyConsumptionEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.InterruptionEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.LoadRecordEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.mapper.SubstationLogDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.EnergyConsumptionJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.InterruptionLogJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository.LoadRecordJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SubstationLogRepositoryImpl implements SubstationLogRepository {

    private final InterruptionLogJpaRepository interruptionLogJpaRepository;
    private final EnergyConsumptionJpaRepository energyConsumptionJpaRepository;
    private final LoadRecordJpaRepository loadRecordJpaRepository;

    private final SubstationLogDataAccessMapper mapper;

    public SubstationLogRepositoryImpl(InterruptionLogJpaRepository interruptionLogJpaRepository,
                                       EnergyConsumptionJpaRepository energyConsumptionJpaRepository,
                                       LoadRecordJpaRepository loadRecordJpaRepository,
                                       SubstationLogDataAccessMapper mapper) {
        this.interruptionLogJpaRepository = interruptionLogJpaRepository;
        this.energyConsumptionJpaRepository = energyConsumptionJpaRepository;
        this.loadRecordJpaRepository = loadRecordJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Interruption save(Interruption interruption) {
        InterruptionEntity interruptionEntity = mapper.interruptionToInterruptionEntity(interruption);
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
        Optional<InterruptionEntity> interruptionEntity = interruptionLogJpaRepository.findById(UUID.fromString(interruptionId));
        if (interruptionEntity.isPresent()){
            return Optional.of(mapper.interruptionEntityToInterruption(interruptionEntity.get()));
        }
        return Optional.empty();
    }
}
