package com.wbsedcl.trms.feeder.management.domain;

import com.wbsedcl.trms.feeder.management.domain.dto.create.CreateFeederCommand;
import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.feeder.management.domain.event.FeederCreatedEvent;
import com.wbsedcl.trms.feeder.management.domain.mapper.FeederDataMapper;
import com.wbsedcl.trms.feeder.management.domain.ports.output.repository.FeederRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class CreateFeederHelper {

    private final FeederDataMapper feederDataMapper;
    private final FeederDomainService feederDomainService;
    private final FeederRepository feederRepository;

    public CreateFeederHelper(FeederDataMapper feederDataMapper, FeederDomainService feederDomainService, FeederRepository feederRepository) {
        this.feederDataMapper = feederDataMapper;
        this.feederDomainService = feederDomainService;
        this.feederRepository = feederRepository;
    }

    @Transactional
    public FeederCreatedEvent persistFeeder(CreateFeederCommand command) {
        //Get Domain object from command
        Feeder feeder = feederDataMapper.createFeederCommandToFeeder(command);
        //Create the new feeder using domain logic
        FeederCreatedEvent feederCreatedEvent = feederDomainService.createNewFeeder(feeder);
        //save the feeder with repository
        feederRepository.saveFeeder(feederCreatedEvent.getCreatedFeeder());
        //log the info
        log.info("Feeder saved with id {}",feederCreatedEvent.getCreatedFeeder().getId());
        //return the com.wbsedcl.hr.management.domain.event
        return feederCreatedEvent;
    }
}
