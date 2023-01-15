package com.wbsedcl.trms.domain.event.publisher;

import com.wbsedcl.trms.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
