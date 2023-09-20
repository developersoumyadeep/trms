package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.exception.ConsumptionValidationException;
import com.wbsedcl.trms.substation.log.domain.valueobject.EnergyConsumptionId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class EnergyConsumption extends BaseEntity<EnergyConsumptionId> implements AggregateRoot {

    private final FeederId feederId;
    private final OfficeId substationOfficeId;
    private LocalDate date;
    private LocalTime time;
    private final String energyMeterNo;
    private final EnergyUnit energyUnit;
    private final double consumption;
    private final int multiplyingFactor;
    private final UserId recordedBy;

    public static EnergyConsumptionBuilder newBuilder() {
        return new EnergyConsumptionBuilder();
    }

    public void initialize() {
        setId(new EnergyConsumptionId(UUID.randomUUID()));
        date = LocalDate.now();
        time = LocalTime.now();
    }

    public void validate() {
        validateInitialConsumption();
        validateEnergyUnit();
        validateEnergyConsumption();
    }

    private void validateEnergyConsumption() {
        if (consumption < 0) {
            throw new ConsumptionValidationException("EnergyConsumption record must have a zero or positive value");
        }
    }

    private void validateInitialConsumption() {
        if (getId() != null || date != null || time != null) {
            throw new ConsumptionValidationException("EnergyConsumption record is not in correct state to be initialized");
        }
    }

    private void validateEnergyUnit() {
        if (EnergyUnit.findByEnergyUnit(energyUnit) == null) {
            throw new ConsumptionValidationException("EnergyConsumption record has invalid energy consumption unit");
        }
    }

    private EnergyConsumption(EnergyConsumptionBuilder energyConsumptionBuilder) {
        super.setId(energyConsumptionBuilder.id);
        feederId = energyConsumptionBuilder.feederId;
        substationOfficeId = energyConsumptionBuilder.substationOfficeId;
        date = energyConsumptionBuilder.date;
        time = energyConsumptionBuilder.time;
        energyMeterNo = energyConsumptionBuilder.energyMeterNo;
        energyUnit = energyConsumptionBuilder.energyUnit;
        consumption = energyConsumptionBuilder.consumption;
        multiplyingFactor = energyConsumptionBuilder.multiplyingFactor;
        recordedBy = energyConsumptionBuilder.recordedBy;
    }

    public FeederId getFeederId() {
        return feederId;
    }

    public OfficeId getSubstationOfficeId() {
        return substationOfficeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getEnergyMeterNo() {
        return energyMeterNo;
    }

    public EnergyUnit getEnergyUnit() {
        return energyUnit;
    }

    public double getConsumption() {
        return consumption;
    }

    public int getMultiplyingFactor() {
        return multiplyingFactor;
    }

    public UserId getRecordedBy() {
        return recordedBy;
    }


    public static final class EnergyConsumptionBuilder {
        private EnergyConsumptionId id;
        private FeederId feederId;
        private OfficeId substationOfficeId;
        private LocalDate date;
        private LocalTime time;
        private String energyMeterNo;
        private EnergyUnit energyUnit;
        private double consumption;
        private int multiplyingFactor;

        private UserId recordedBy;

        private EnergyConsumptionBuilder() {
        }

        public EnergyConsumptionBuilder id(EnergyConsumptionId val) {
            this.id = val;
            return this;
        }

        public EnergyConsumptionBuilder feederId(FeederId val) {
            this.feederId = val;
            return this;
        }

        public EnergyConsumptionBuilder substationOfficeId(OfficeId val) {
            this.substationOfficeId = val;
            return this;
        }

        public EnergyConsumptionBuilder date(LocalDate val) {
            this.date = val;
            return this;
        }

        public EnergyConsumptionBuilder time(LocalTime val) {
            this.time = val;
            return this;
        }

        public EnergyConsumptionBuilder energyMeterNo(String val) {
            this.energyMeterNo = val;
            return this;
        }

        public EnergyConsumptionBuilder energyUnit(EnergyUnit val) {
            this.energyUnit = val;
            return this;
        }

        public EnergyConsumptionBuilder consumption(double val) {
            this.consumption = val;
            return this;
        }

        public EnergyConsumptionBuilder multiplyingFactor(int val) {
            this.multiplyingFactor = val;
            return this;
        }

        public EnergyConsumptionBuilder recordedBy(UserId recordedBy){
            this.recordedBy = recordedBy;
            return this;
        }

        public EnergyConsumption build() {
            return new EnergyConsumption(this);
        }
    }
}
