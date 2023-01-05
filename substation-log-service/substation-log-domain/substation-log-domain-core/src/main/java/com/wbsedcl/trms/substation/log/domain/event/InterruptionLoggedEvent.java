package com.wbsedcl.trms.substation.log.domain.event;

import com.wbsedcl.trms.substation.log.domain.entity.Interruption;

import java.time.LocalDateTime;

public class InterruptionLoggedEvent extends InterruptionEvent{
    public InterruptionLoggedEvent(Interruption interruption, LocalDateTime createdAt) {
        super(interruption, createdAt);
    }
}
