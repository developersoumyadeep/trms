package com.wbsedcl.trms.feeder.management.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.OfficeId;


public class Office extends BaseEntity<OfficeId> implements AggregateRoot {

    private String officeText;

    public Office(String officeText) {
        this.officeText = officeText;
    }

    private Office(OfficeBuilder officeBuilder) {
        setId(officeBuilder.officeId);
        setOfficeText(officeBuilder.officeText);
    }

    public String getOfficeText() {
        return officeText;
    }

    public void setOfficeText(String officeText) {
        this.officeText = officeText;
    }

    public static OfficeBuilder newBuilder(){
        return new OfficeBuilder();
    }
    public static final class OfficeBuilder {
        private  OfficeId officeId;
        private String officeText;

        public OfficeBuilder() {
        }

        public OfficeBuilder officeId(OfficeId officeId) {
           this.officeId = officeId;
            return this;
        }

        public OfficeBuilder officeText(String val) {
            officeText = val;
            return this;
        }

        public Office build() {
            return new Office(this);
        }
    }
}
