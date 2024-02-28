package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.substation.log.domain.exception.EnergyMeterReadingValidationException;
import com.wbsedcl.trms.substation.log.domain.exception.EnergyMeterValidationException;

import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class EnergyMeter extends BaseEntity<EnergyMeterSerialNumber> implements AggregateRoot {
    Logger logger = Logger.getLogger(EnergyMeter.class.getName());
    private Double meterCTRatio;
    private Double meterPTRatio;
    private EnergyUnit energyUnit;

    private EnergyMeter(Builder builder) {
        setId(builder.serialNumber);
        meterCTRatio = builder.meterCTRatio;
        meterPTRatio = builder.meterPTRatio;
        energyUnit = builder.energyUnit;
    }
    public static Builder builder() {
        return new Builder();
    }

    public void validate() {
        logger.info("Validating energy meter with serial number "+getSerialNumber().getValue());
        validateCtAndPtRatio();
        validateEnergyUnit();
    }

    private void validateCtAndPtRatio() {
        logger.info("Validating energy meter reading unit");
        if (meterCTRatio <0 || meterCTRatio <1) {
            throw new EnergyMeterValidationException("Energy meter CT ratio can not be less than 0 or fraction");
        }
        if (meterPTRatio <0 || meterPTRatio <1) {
            throw new EnergyMeterValidationException("Energy meter PT ratio can not be less than 0 or fraction");
        }
    }

    private void validateEnergyUnit() {
        logger.info("Validating energy meter reading unit");
        if (EnergyUnit.findByEnergyUnit(energyUnit) == null) {
            throw new EnergyMeterReadingValidationException("Energy meter reading record has invalid energy unit");
        }
    }

    public static final class Builder {
        private EnergyMeterSerialNumber serialNumber;
        private Double meterCTRatio;
        private Double meterPTRatio;
        private EnergyUnit energyUnit;

        private Builder() {
        }

        public Builder serialNumber(EnergyMeterSerialNumber val) {
            this.serialNumber = val;
            return this;
        }

        public Builder meterCTRatio(Double val) {
            meterCTRatio = val;
            return this;
        }

        public Builder meterPTRatio(Double val) {
            meterPTRatio = val;
            return this;
        }

        public Builder energyUnit(EnergyUnit val) {
            energyUnit = val;
            return this;
        }

        public EnergyMeter build() {
            return new EnergyMeter(this);
        }
    }

    public EnergyMeterSerialNumber getSerialNumber() {
        return getId();
    }
    public Double getMeterCTRatio() {
        return meterCTRatio;
    }

    public Double getMeterPTRatio() {
        return meterPTRatio;
    }

    public EnergyUnit getEnergyUnit() {
        return energyUnit;
    }

    @Override
    public String toString() {
        return "EnergyMeter{" +
                "serialNumber=" + getSerialNumber().getValue() +
                "meterCTRatio=" + meterCTRatio +
                ", meterPTRatio=" + meterPTRatio +
                ", energyUnit=" + energyUnit +
                '}';
    }
}
