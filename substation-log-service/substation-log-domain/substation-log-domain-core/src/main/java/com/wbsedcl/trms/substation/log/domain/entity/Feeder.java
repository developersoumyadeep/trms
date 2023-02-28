package com.wbsedcl.trms.substation.log.domain.entity;


import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.domain.valueobject.FeederId;

public class Feeder extends BaseEntity<FeederId> implements AggregateRoot {
    private String feederName;
    private String energyMeterNo;
    private OfficeId substationOfficeId;
    private Integer voltageLevel;
    private FeederType feederType;


    private Feeder(FeederBuilder feederBuilder) {
        setId(feederBuilder.feederId);
    }

    public String getFeederName() {
        return feederName;
    }

    public OfficeId getSubstationOfficeId() {
        return substationOfficeId;
    }

    public Integer getVoltageLevel() {
        return voltageLevel;
    }

    public FeederType getFeederType() {
        return feederType;
    }

    public String getEnergyMeterNo() {
        return energyMeterNo;
    }

    public static FeederBuilder newBuilder() {
        return new FeederBuilder();
    }

    public static final class FeederBuilder {
        private FeederId feederId;
        private String feederName;
        private OfficeId substationOfficeId;
        private Integer voltageLevel;
        private FeederType feederType;
        private String energyMeterNo;

        private FeederBuilder() {

        }

        public FeederBuilder feederId(FeederId feederId) {
            this.feederId = feederId;
            return this;
        }
        public FeederBuilder feederName(String feederName) {
            this.feederName = feederName;
            return this;
        }

        public FeederBuilder substationOfficeId(OfficeId substationOfficeId) {
            this.substationOfficeId = substationOfficeId;
            return this;
        }

        public FeederBuilder voltageLevel(Integer voltageLevel) {
            this.voltageLevel = voltageLevel;
            return this;
        }

        public FeederBuilder feederType(FeederType feederType) {
            this.feederType = feederType;
            return this;
        }

        public FeederBuilder energyMeterNo(String energyMeterNo) {
            this.energyMeterNo = energyMeterNo;
            return this;
        }

        public Feeder build() {
            return new Feeder(this);
        }


    }

}
