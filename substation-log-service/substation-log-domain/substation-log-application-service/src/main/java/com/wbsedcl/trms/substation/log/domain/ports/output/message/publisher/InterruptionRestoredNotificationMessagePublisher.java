package com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher;

import com.wbsedcl.trms.domain.event.publisher.DomainEventPublisher;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;

public interface InterruptionRestoredNotificationMessagePublisher extends DomainEventPublisher<InterruptionRestoredEvent> {
}
