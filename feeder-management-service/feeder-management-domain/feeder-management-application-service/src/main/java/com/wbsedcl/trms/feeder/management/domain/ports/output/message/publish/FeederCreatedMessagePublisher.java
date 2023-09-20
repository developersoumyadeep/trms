package com.wbsedcl.trms.feeder.management.domain.ports.output.message.publish;

import com.wbsedcl.trms.domain.event.publisher.DomainEventPublisher;
import com.wbsedcl.trms.feeder.management.domain.event.FeederCreatedEvent;

public interface FeederCreatedMessagePublisher extends DomainEventPublisher<FeederCreatedEvent> {
}
