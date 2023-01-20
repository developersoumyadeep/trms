package com.wbsedcl.trms.substation.log.domain.event;

import com.wbsedcl.trms.domain.event.DomainEvent;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;

import java.time.LocalDateTime;

public class LoadRecordLoggedEvent implements DomainEvent<LoadRecord> {

    private final LoadRecord record;
    private final LocalDateTime createdAt;

    public LoadRecordLoggedEvent(LoadRecord record, LocalDateTime createdAt) {
        this.record = record;
        this.createdAt = createdAt;
    }

    public LoadRecord getRecord() {
        return record;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
