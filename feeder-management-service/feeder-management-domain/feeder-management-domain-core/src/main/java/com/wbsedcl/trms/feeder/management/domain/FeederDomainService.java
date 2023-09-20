package com.wbsedcl.trms.feeder.management.domain;

import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.feeder.management.domain.event.FeederCreatedEvent;
import com.wbsedcl.trms.feeder.management.domain.event.FeederUpdatedEvent;

public interface FeederDomainService {
    FeederCreatedEvent createNewFeeder(Feeder feeder);
    FeederUpdatedEvent updateFeeder(Feeder feeder);
}
