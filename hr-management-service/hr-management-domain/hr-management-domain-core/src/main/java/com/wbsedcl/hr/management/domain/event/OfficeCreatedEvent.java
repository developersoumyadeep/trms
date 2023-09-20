package com.wbsedcl.hr.management.domain.event;

import com.wbsedcl.trms.domain.event.DomainEvent;
import com.wbsedcl.hr.management.domain.entity.Office;

import java.time.LocalDateTime;

public class OfficeCreatedEvent implements DomainEvent<Office> {

    private Office office;
    private LocalDateTime creationTimeStamp;

    public OfficeCreatedEvent(Office office, LocalDateTime creationTimeStamp) {
        this.office = office;
        this.creationTimeStamp = creationTimeStamp;
    }

    public Office getOffice() {
        return office;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }
}
