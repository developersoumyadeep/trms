package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.exception.EnergyMeterReadingValidationException;
import com.wbsedcl.trms.substation.log.domain.valueobject.EnergyMeterReadingId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import java.util.logging.Logger;

@SuppressWarnings("LombokGetterMayBeUsed")
public class EnergyMeterReading extends BaseEntity<EnergyMeterReadingId> implements AggregateRoot {
    Logger logger = Logger.getLogger(EnergyMeterReading.class.getName());
    private final FeederId feederId;
    private final EnergyMeterSerialNumber energyMeterNo;
    private final OfficeId substationOfficeId;
    private LocalDate recordDate;
    private LocalTime recordTime;
    private final Double meterReading;
    private final EnergyUnit energyUnit;
    private final UserId recordedBy;
    private final Double multiplyingFactor;

    private EnergyMeterReading(Builder builder) {
        setId(builder.id);
        feederId = builder.feederId;
        energyMeterNo = builder.energyMeterNo;
        substationOfficeId = builder.substationOfficeId;
        recordDate = builder.recordDate;
        recordTime = builder.recordTime;
        meterReading = builder.meterReading;
        energyUnit = builder.energyUnit;
        recordedBy = builder.recordedBy;
        multiplyingFactor = builder.multiplyingFactor;
    }

    public void initialize() {
        logger.info("Initializing new energy meter reading");
        setId(new EnergyMeterReadingId(UUID.randomUUID()));
        recordDate = LocalDate.now();
        recordTime = LocalTime.now();
    }

    public void validate() {
        logger.info("Validating new energy meter reading");
        validateInitialReading();
        validateEnergyUnit();
        validateMeterReading();
        logger.info("Validation completed for energy meter reading");
    }

    private void validateInitialReading() {
        logger.info("Validating initial energy meter reading");
        if (getId() != null || recordDate != null || recordTime != null) {
            throw new EnergyMeterReadingValidationException("Energy meter reading record is not in a correct state to be initialized");
        }
    }

    private void validateEnergyUnit() {
        logger.info("Validating energy meter reading unit");
        if (EnergyUnit.findByEnergyUnit(energyUnit) == null) {
            throw new EnergyMeterReadingValidationException("Energy meter reading record has invalid energy unit");
        }
    }

    private void validateMeterReading() {
        logger.info("Validating energy meter reading value");
        if (meterReading<0) {
            throw new EnergyMeterReadingValidationException("Energy meter reading must have a zero or greater than zero value");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public FeederId getFeederId() {
        return feederId;
    }

    public EnergyMeterSerialNumber getEnergyMeterNo() {
        return energyMeterNo;
    }

    public OfficeId getSubstationOfficeId() {
        return substationOfficeId;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public LocalTime getRecordTime() {
        return recordTime;
    }

    public Double getMeterReading() {
        return meterReading;
    }

    public EnergyUnit getEnergyUnit() {
        return energyUnit;
    }

    public UserId getRecordedBy() {
        return recordedBy;
    }


    public static final class Builder {

        private EnergyMeterReadingId id;
        private FeederId feederId;
        private EnergyMeterSerialNumber energyMeterNo;
        private OfficeId substationOfficeId;
        private LocalDate recordDate;
        private LocalTime recordTime;
        private Double meterReading;
        private EnergyUnit energyUnit;
        private UserId recordedBy;
        private Double multiplyingFactor;

        private Builder() {
        }

        public Builder id(EnergyMeterReadingId val) {
            id = val;
            return this;
        }

        public Builder feederId(FeederId val) {
            feederId = val;
            return this;
        }

        public Builder energyMeterNo(EnergyMeterSerialNumber val) {
            energyMeterNo = val;
            return this;
        }

        public Builder substationOfficeId(OfficeId val) {
            substationOfficeId = val;
            return this;
        }

        public Builder recordDate(LocalDate val) {
            recordDate = val;
            return this;
        }

        public Builder recordTime(LocalTime val) {
            recordTime = val;
            return this;
        }

        public Builder meterReading(Double val) {
            meterReading = val;
            return this;
        }

        public Builder energyUnit(EnergyUnit val) {
            energyUnit = val;
            return this;
        }

        public Builder recordedBy(UserId val) {
            recordedBy = val;
            return this;
        }

        public Builder multiplyingFactor(Double multiplyingFactor) {
            this.multiplyingFactor = multiplyingFactor;
            return this;
        }

        public EnergyMeterReading build() {
            return new EnergyMeterReading(this);
        }
    }

    @Override
    public String toString() {
        return "EnergyMeterReading{" +
                "feederId='" + feederId + '\'' +
                ", energyMeterNo='" + energyMeterNo + '\'' +
                ", substationOfficeId='" + substationOfficeId + '\'' +
                ", recordDate=" + recordDate +
                ", recordTime=" + recordTime +
                ", meterReading=" + meterReading +
                ", energyUnit=" + energyUnit +
                ", recordedBy=" + recordedBy +
                ", multiplyingFactor=" + multiplyingFactor +
                '}';
    }
}
