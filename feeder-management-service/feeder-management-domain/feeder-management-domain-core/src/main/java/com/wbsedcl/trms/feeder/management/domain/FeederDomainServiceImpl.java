package com.wbsedcl.trms.feeder.management.domain;

import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.feeder.management.domain.event.FeederCreatedEvent;
import com.wbsedcl.trms.feeder.management.domain.event.FeederUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Slf4j
public class FeederDomainServiceImpl implements FeederDomainService{
    @Override
    public FeederCreatedEvent createNewFeeder(Feeder feeder) {
        //validate feeder
        feeder.validateFeeder();
        //log the info
        log.info("Feeder created with id: {}",feeder.getId());
        //return the com.wbsedcl.hr.management.domain.event
        return new FeederCreatedEvent(feeder, LocalDateTime.now());
    }

    @Override
    public FeederUpdatedEvent updateFeeder(Feeder feeder) {
        //validate feeder
        feeder.validateFeeder();
        //log the info
        log.info("Feeder with id: {} updated",feeder.getId());
        //return the com.wbsedcl.hr.management.domain.event
        return new FeederUpdatedEvent(feeder, LocalDateTime.now());
    }
}
