package com.wbsedcl.trms.feeder.management.domain.entity;

import com.wbsedcl.trms.feeder.management.domain.exception.FeederValidationException;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.FeederId;

public class Feeder extends BaseEntity<FeederId> {

    private String feederText;
    private Integer voltageLevel;
    private String sourceSubstationOfficeId;
    private String terminalSubstationOfficeId;
    private String energyMeterNoAtSource;
    private String energyMeterNoAtTerminal;
    private Double gisLength;
    private Double ctRatioAtSource;
    private Double ctRatioAtTerminal;
    private Double multiplyingFactor;
    private FeederType feederType;
    private String ptrId;
    private Double incomerCTRatio;
    private String switchgearId;
    private Boolean isDedicatedBulkFeeder;

    private FeederId incomer11kVFeederId;
    private FeederId feedingPTRId;
    private FeederId feeding33kVFeederId;

    private Feeder(FeederBuilder feederBuilder) {
        setId(feederBuilder.feederId);
        this.feederText = feederBuilder.feederText;
        this.voltageLevel = feederBuilder.voltageLevel;
        this.sourceSubstationOfficeId = feederBuilder.sourceSubstationOfficeId;
        this.terminalSubstationOfficeId = feederBuilder.terminalSubstationOfficeId;
        this.energyMeterNoAtSource = feederBuilder.energyMeterNoAtSource;
        this.energyMeterNoAtTerminal = feederBuilder.energyMeterNoAtTerminal;
        this.gisLength = feederBuilder.gisLength;
        this.ctRatioAtSource = feederBuilder.ctRatioAtSource;
        this.ctRatioAtTerminal = feederBuilder.ctRatioAtTerminal;
        this.multiplyingFactor = feederBuilder.multiplyingFactor;
        this.feederType = feederBuilder.feederType;
        this.ptrId = feederBuilder.ptrId;
        this.incomerCTRatio = feederBuilder.incomerCTRatio;
        this.switchgearId = feederBuilder.switchgearId;
        this.isDedicatedBulkFeeder = feederBuilder.isDedicatedBulkFeeder;
        this.incomer11kVFeederId = feederBuilder.incomer11kVFeederId;
        this.feedingPTRId = feederBuilder.feedingPTRId;
        this.feeding33kVFeederId = feederBuilder.feeding33kVFeederId;
    }
    public String getFeederText() {
        return feederText;
    }
    public Integer getVoltageLevel() {
        return voltageLevel;
    }
    public String getSourceSubstationOfficeId() {
        return sourceSubstationOfficeId;
    }

    public String getTerminalSubstationOfficeId() {
        return terminalSubstationOfficeId;
    }
    public String getEnergyMeterNoAtSource() {
        return energyMeterNoAtSource;
    }
    public String getEnergyMeterNoAtTerminal() {
        return energyMeterNoAtTerminal;
    }

    public Double getGisLength() {
        return gisLength;
    }

    public Double getCtRatioAtSource() {
        return ctRatioAtSource;
    }

    public Double getCtRatioAtTerminal() {
        return ctRatioAtTerminal;
    }

    public Double getMultiplyingFactor() {
        return multiplyingFactor;
    }

    public FeederType getFeederType() {
        return feederType;
    }

    public String getPtrId() {
        return ptrId;
    }

    public Double getIncomerCTRatio() {
        return incomerCTRatio;
    }

    public String getSwitchgearId() {
        return switchgearId;
    }

    public Boolean isDedicatedBulkFeeder() {
        return isDedicatedBulkFeeder;
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

    public void validateFeeder() {
        validateVoltageLevel();
        validateIsDedicatedBulkFeeder();
        validateSourceAndTerminalSubstationOfficeId();
        validateEnergyMeterNosAtSourceAndTerminal();
        validateGisLength();
        validateCTRatioAtSourceAndTerminal();
        validateMultiplyingFactor();
        validateIncomerCtRatio();
        validateFeederType();
    }

    private void validateFeederType() {
        if (FeederType.findByFeederType(feederType) == null) {
            throw new FeederValidationException("Invalid feeder type "+feederType);
        }
    }

    public void validateIncomerCtRatio() {
        if (incomerCTRatio == null || incomerCTRatio <= 0) {
            throw new FeederValidationException("Incomer CT ratio must be non null and greater than zero");
        }
    }

    public void validateCTRatioAtSourceAndTerminal() {
        if (ctRatioAtSource == null || ctRatioAtSource <=0) {
            throw new FeederValidationException("CT Ratio at source substation must be non null and greater than 0");
        }

        //Applicable for 33kV feeders only
        if (ctRatioAtTerminal != null && ctRatioAtTerminal <=0) {
            throw new FeederValidationException("CT Ratio at terminal substation should either be null or greater than zero");
        }
    }

    public void validateMultiplyingFactor() {
        if (multiplyingFactor == null || multiplyingFactor <= 0) {
            throw new FeederValidationException("The multiplying factor must be non null and greater than 0");
        }
    }

    public void validateGisLength() {
        if (gisLength == null || gisLength <=0) {
            throw new FeederValidationException("The gis length of the feeder must be greater than zero or null");
        }
    }

    private void validateEnergyMeterNosAtSourceAndTerminal() {
        if (sourceSubstationOfficeId == null) {
            throw new FeederValidationException("Feeder must have a sourceSubstationId");
        }
        //this logic here assumes that there is no LILO 11kV feeder in the system
        //if the 33kV feeder is not a dedicated bulk feeder then it must have a terminal substation id;
        if (voltageLevel == 33.0 && terminalSubstationOfficeId == null && !isDedicatedBulkFeeder) {
            throw new FeederValidationException("33kV feeder must have a terminalSubstationOfficeId");
        }
    }

    private void validateIsDedicatedBulkFeeder() {
        if (isDedicatedBulkFeeder == null) {
            throw new FeederValidationException("Feeder must have a non null isDedicatedBulkFeeder property");
        }
    }

    private void validateSourceAndTerminalSubstationOfficeId() {
        if (sourceSubstationOfficeId == null) {
            throw new FeederValidationException("Feeder must have a sourceSubstationId");
        }
        //this logic here assumes that there is no LILO 11kV feeder in the system
        //if the 33kV feeder is not a dedicated bulk feeder then it must have a terminal substation id;
        if (voltageLevel == 33.0 && terminalSubstationOfficeId == null && !isDedicatedBulkFeeder) {
            throw new FeederValidationException("33kV feeder must have a terminalSubstationOfficeId");
        }
    }

    private void validateVoltageLevel() {
       if (!(voltageLevel == 11.0 || voltageLevel == 33.0 || voltageLevel == 6.6)) {
           throw new FeederValidationException("Invalid voltage level: "+this.voltageLevel);
       }
    }

    public static FeederBuilder newBuilder() {
        return new FeederBuilder();
    }



    public static class FeederBuilder {
        private FeederId feederId;
        private String feederText;
        private Integer voltageLevel;
        private String sourceSubstationOfficeId;
        private String terminalSubstationOfficeId;
        private String energyMeterNoAtSource;
        private String energyMeterNoAtTerminal;
        private Double gisLength;
        private Double ctRatioAtSource;
        private Double ctRatioAtTerminal;
        private Double multiplyingFactor;
        private FeederType feederType;
        private String ptrId;
        private Double incomerCTRatio;
        private String switchgearId;
        private Boolean isDedicatedBulkFeeder;

        private FeederId incomer11kVFeederId;
        private FeederId feedingPTRId;
        private FeederId feeding33kVFeederId;

        public FeederBuilder feederId(FeederId feederId) {
            this.feederId = feederId;
            return this;
        }

        public FeederBuilder feederText(String feederText) {
            this.feederText = feederText;
            return this;
        }

        public FeederBuilder voltageLevel(Integer voltageLevel) {
            this.voltageLevel = voltageLevel;
            return this;
        }

        public FeederBuilder sourceSubstationOfficeId(String sourceSubstationOfficeId){
            this.sourceSubstationOfficeId = sourceSubstationOfficeId;
            return this;
        }

        public FeederBuilder terminalSubstationOfficeId(String terminalSubstationOfficeId) {
            this.terminalSubstationOfficeId = terminalSubstationOfficeId;
            return this;
        }

        public FeederBuilder energyMeterNoAtSource(String energyMeterNoAtSource) {
            this.energyMeterNoAtSource = energyMeterNoAtSource;
            return this;
        }

        public FeederBuilder energyMeterNoAtTerminal(String energyMeterNoAtTerminal) {
            this.energyMeterNoAtTerminal = energyMeterNoAtTerminal;
            return this;
        }

        public FeederBuilder gisLength(Double gisLength){
            this.gisLength = gisLength;
            return this;
        }

        public FeederBuilder ctRatioAtSource(Double ctRatioAtSource) {
            this.ctRatioAtSource = ctRatioAtSource;
            return this;
        }

        public FeederBuilder ctRatioAtTerminal(Double ctRatioAtTerminal) {
            this.ctRatioAtTerminal = ctRatioAtTerminal;
            return this;
        }

        public FeederBuilder multiplyingFactor(Double multiplyingFactor) {
            this.multiplyingFactor = multiplyingFactor;
            return this;
        }

        public FeederBuilder feederType(FeederType feederType) {
            this.feederType = feederType;
            return this;
        }

        public FeederBuilder ptrId(String ptrId) {
            this.ptrId = ptrId;
            return this;
        }

        public FeederBuilder incomerCTRatio(Double incomerCTRatio) {
            this.incomerCTRatio = incomerCTRatio;
            return this;
        }

        public FeederBuilder switchgearId(String switchgearId) {
            this.switchgearId = switchgearId;
            return this;
        }

        public FeederBuilder isDedicatedBulkFeeder(Boolean isDedicatedBulkFeeder) {
            this.isDedicatedBulkFeeder = isDedicatedBulkFeeder;
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

        public Feeder build(){
            return new Feeder(this);
        }
    }
}
