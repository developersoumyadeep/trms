package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.OfficeId;

public class Office extends BaseEntity<OfficeId> implements AggregateRoot {

    private String officeText;
    private OfficeId parentOfficeId;

    public Office() {
    }

    public Office(OfficeId officeId, OfficeId parentOfficeId) {
        setId(officeId);
        this.parentOfficeId = parentOfficeId;
    }

    public Office(OfficeId officeId, String officeText, OfficeId parentOfficeId) {
        setId(officeId);
        setOfficeText(officeText);
        setParentOfficeId(parentOfficeId);
    }

    public void setParentOfficeId(OfficeId parentOfficeId) {
        this.parentOfficeId = parentOfficeId;
    }

    public OfficeId getParentOfficeId() {
        return parentOfficeId;
    }

    public String getOfficeText() {
        return officeText;
    }

    public void setOfficeText(String officeText) {
        this.officeText = officeText;
    }
}
