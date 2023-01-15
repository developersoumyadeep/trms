package com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher;

import com.wbsedcl.trms.domain.event.publisher.DomainEventPublisher;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;

public interface InterruptionLoggedNotificationMessagePublisher extends DomainEventPublisher<InterruptionLoggedEvent> {

}
