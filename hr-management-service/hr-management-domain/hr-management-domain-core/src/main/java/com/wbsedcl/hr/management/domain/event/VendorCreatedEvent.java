package com.wbsedcl.hr.management.domain.event;

import com.wbsedcl.trms.domain.event.DomainEvent;
import com.wbsedcl.hr.management.domain.entity.Vendor;

import java.time.LocalDateTime;

public class VendorCreatedEvent implements DomainEvent<Vendor> {

    private Vendor vendor;
    private LocalDateTime creationTimeStamp;

    public VendorCreatedEvent(Vendor vendor, LocalDateTime creationTimeStamp) {
        this.vendor = vendor;
        this.creationTimeStamp = creationTimeStamp;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }
}
