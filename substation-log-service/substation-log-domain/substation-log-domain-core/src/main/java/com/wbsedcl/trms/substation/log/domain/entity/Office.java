package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.OfficeId;

public class Office extends BaseEntity<OfficeId> implements AggregateRoot {

    private String officeText;

    public Office() {
    }

    public Office(OfficeId officeId) {
        setId(officeId);
    }

    public Office(OfficeId officeId, String officeText) {
        setId(officeId);
        setOfficeText(officeText);
    }

    public String getOfficeText() {
        return officeText;
    }

    public void setOfficeText(String officeText) {
        this.officeText = officeText;
    }
}
