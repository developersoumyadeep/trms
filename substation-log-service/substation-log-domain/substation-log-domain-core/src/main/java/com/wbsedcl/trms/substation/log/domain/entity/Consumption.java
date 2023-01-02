package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.AssetId;
import com.wbsedcl.trms.substation.log.domain.exception.ConsumptionValidationException;
import com.wbsedcl.trms.substation.log.domain.valueobject.ConsumptionId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Consumption extends BaseEntity<ConsumptionId> implements AggregateRoot {

    private final AssetId assetId;
    private final OfficeId substationOfficeId;
    private LocalDate date;
    private LocalTime time;
    private final String energyMeterNo;
    private final EnergyUnit energyUnit;
    private final int consumption;
    private final int multiplyingFactor;

    public static ConsumptionBuilder newBuilder() {
        return new ConsumptionBuilder();
    }

    public void initialize() {
        setId(new ConsumptionId(UUID.randomUUID()));
        date = LocalDate.now();
        time = LocalTime.now();
    }

    public void validate() {
        validateInitialConsumption();
        validateEnergyUnit();
    }

    private void validateInitialConsumption() {
        if (getId() != null || date != null || time != null) {
            throw new ConsumptionValidationException("Consumption record is not in correct state to be initialized");
        }
    }

    private void validateEnergyUnit() {
        if (EnergyUnit.findByEnergyUnit(energyUnit) == null) {
            throw new ConsumptionValidationException("Consumption record has invalid energy consumption unit");
        }
    }

    private Consumption(ConsumptionBuilder consumptionBuilder) {
        super.setId(consumptionBuilder.id);
        assetId = consumptionBuilder.assetId;
        substationOfficeId = consumptionBuilder.substationOfficeId;
        date = consumptionBuilder.date;
        time = consumptionBuilder.time;
        energyMeterNo = consumptionBuilder.energyMeterNo;
        energyUnit = consumptionBuilder.energyUnit;
        consumption = consumptionBuilder.consumption;
        multiplyingFactor = consumptionBuilder.multiplyingFactor;
    }

    public AssetId getAssetId() {
        return assetId;
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

    public int getConsumption() {
        return consumption;
    }

    public int getMultiplyingFactor() {
        return multiplyingFactor;
    }


    public static final class ConsumptionBuilder {
        private ConsumptionId id;
        private AssetId assetId;
        private OfficeId substationOfficeId;
        private LocalDate date;
        private LocalTime time;
        private String energyMeterNo;
        private EnergyUnit energyUnit;
        private int consumption;
        private int multiplyingFactor;

        private ConsumptionBuilder() {
        }

        public ConsumptionBuilder id(ConsumptionId val) {
            id = val;
            return this;
        }

        public ConsumptionBuilder assetId(AssetId val) {
            assetId = val;
            return this;
        }

        public ConsumptionBuilder substationOfficeId(OfficeId val) {
            substationOfficeId = val;
            return this;
        }

        public ConsumptionBuilder date(LocalDate val) {
            date = val;
            return this;
        }

        public ConsumptionBuilder time(LocalTime val) {
            time = val;
            return this;
        }

        public ConsumptionBuilder energyMeterNo(String val) {
            energyMeterNo = val;
            return this;
        }

        public ConsumptionBuilder energyUnit(EnergyUnit val) {
            energyUnit = val;
            return this;
        }

        public ConsumptionBuilder consumption(int val) {
            consumption = val;
            return this;
        }

        public ConsumptionBuilder multiplyingFactor(int val) {
            multiplyingFactor = val;
            return this;
        }

        public Consumption build() {
            return new Consumption(this);
        }
    }
}
