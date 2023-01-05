package com.wbsedcl.trms.substation.log.domain.event;

import com.wbsedcl.trms.substation.log.domain.entity.Interruption;

import java.time.LocalDateTime;

public class InterruptionRestoredEvent extends InterruptionEvent{
    public InterruptionRestoredEvent(Interruption interruption, LocalDateTime createdAt) {
        super(interruption, createdAt);
    }
}
