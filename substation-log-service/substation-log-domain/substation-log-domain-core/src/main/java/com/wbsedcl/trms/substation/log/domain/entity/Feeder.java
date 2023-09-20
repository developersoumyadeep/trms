package com.wbsedcl.trms.substation.log.domain.entity;


import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;

public class Feeder extends BaseEntity<FeederId> implements AggregateRoot {
    private String feederName;
    private String energyMeterNo;
    private OfficeId substationOfficeId;
    private Integer voltageLevel;
    private FeederType feederType;
    private FeederId incomer11kVFeederId;
    private FeederId feedingPTRId;
    private FeederId feeding33kVFeederId;
    private Boolean isCharged;
    private Boolean isLoaded;

    private Feeder(FeederBuilder feederBuilder) {
        setId(feederBuilder.feederId);
        this.feederName = feederBuilder.feederName;
        this.feederType = feederBuilder.feederType;
        this.substationOfficeId = feederBuilder.substationOfficeId;
        this.energyMeterNo = feederBuilder.energyMeterNo;
        this.voltageLevel = feederBuilder.voltageLevel;
        this.incomer11kVFeederId = feederBuilder.incomer11kVFeederId;
        this.feedingPTRId = feederBuilder.feedingPTRId;
        this.feeding33kVFeederId = feederBuilder.feeding33kVFeederId;
        this.isCharged = feederBuilder.isCharged;
        this.isLoaded = feederBuilder.isLoaded;
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

    public FeederId getIncomer11kVFeederId() {
        return incomer11kVFeederId;
    }

    public FeederId getFeedingPTRId() {
        return feedingPTRId;
    }

    public FeederId getFeeding33kVFeederId() {
        return feeding33kVFeederId;
    }

    public Boolean isLoaded() {return isLoaded;}

    public void setIsLoaded(Boolean loadingStatus) {
        this.isLoaded = loadingStatus;
    }

    public Boolean getCharged() {
        return isCharged;
    }

    public void setCharged(Boolean charged) {
        isCharged = charged;
    }

    public void setIncomer11kVFeederId(FeederId incomer11kVFeederId) {
        this.incomer11kVFeederId = incomer11kVFeederId;
    }

    public void setFeedingPTRId(FeederId feedingPTRId) {
        this.feedingPTRId = feedingPTRId;
    }

    public void setFeeding33kVFeederId(FeederId feeding33kVFeederId) {
        this.feeding33kVFeederId = feeding33kVFeederId;
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
        private FeederId incomer11kVFeederId;
        private FeederId feedingPTRId;
        private FeederId feeding33kVFeederId;

        private Boolean isCharged;
        private Boolean isLoaded;

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
        public FeederBuilder incomer11kVFeederId(FeederId incomer11kVFeederId) {
            this.incomer11kVFeederId = incomer11kVFeederId;
            return this;
        }

        public FeederBuilder feedingPTRId(FeederId feedingPTRId) {
            this.feedingPTRId = feedingPTRId;
            return this;
        }

        public FeederBuilder feeding33kVFeederId(FeederId feeding33kVFeederId) {
            this.feeding33kVFeederId = feeding33kVFeederId;
            return this;
        }

        public FeederBuilder isCharged(Boolean isCharged) {
            this.isCharged = isCharged;
            return this;
        }
        public FeederBuilder isLoaded(Boolean isLoaded) {
            this.isLoaded = isLoaded;
            return this;
        }

        public Feeder build() {
            return new Feeder(this);
        }


    }

}
