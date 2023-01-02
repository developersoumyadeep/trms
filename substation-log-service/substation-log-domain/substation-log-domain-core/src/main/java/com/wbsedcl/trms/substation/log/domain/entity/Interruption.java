package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.AssetId;
import com.wbsedcl.trms.substation.log.domain.valueobject.InterruptionId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Interruption extends BaseEntity<InterruptionId> implements AggregateRoot {

    private final AssetId faultyAssetId;
    private final OfficeId substationOfficeId;
    private final InterruptionType interruptionType;
    private final FaultNature faultNature;
    private final String createdBy;
    private final LocalDate startDate;
    private final LocalTime startTime;

    private LocalDateTime creationTimestamp;
    private String restoredBy;
    private LocalDateTime restorationTimeStamp;
    private InterruptionStatus interruptionStatus;
    private String cause;
    private LocalDate endDate;
    private LocalTime endTime;

    private Interruption(InterruptionBuilder interruptionBuilder) {
        setId(interruptionBuilder.interruptionId);
        faultyAssetId = interruptionBuilder.faultyAssetId;
        substationOfficeId = interruptionBuilder.substationOfficeId;
        interruptionType = interruptionBuilder.interruptionType;
        faultNature = interruptionBuilder.faultNature;
        createdBy = interruptionBuilder.createdBy;
        creationTimestamp = interruptionBuilder.creationTimestamp;
        restoredBy = interruptionBuilder.restoredBy;
        restorationTimeStamp = interruptionBuilder.restorationTimeStamp;
        interruptionStatus = interruptionBuilder.interruptionStatus;
        cause = interruptionBuilder.cause;
        startDate = interruptionBuilder.startDate;
        startTime = interruptionBuilder.startTime;
        endDate = interruptionBuilder.endDate;
        endTime = interruptionBuilder.endTime;
    }


    public void initializeInterruption() {
        creationTimestamp = LocalDateTime.now();
    }

    public void validateInterruption() {
        validateInitialInterruption();
        validateInterruptionType();
        validateFaultNature();
        validateInterruptionStatus();
        validateInterruptionHrs();

    }

    private void validateInterruptionHrs() {
        //1. If interruption is in "Restored" status,then both the start date & time and end date & time should be present.
        //2. The End date & time should be at a later point of time than the start date & time
        //3. If interruption is in "Not Restored" status, then only the start date & time should be present.
        //4. The start date & time and end date & time must not be in a future point of time
        if (interruptionStatus.equals(InterruptionStatus.RESTORED)){
            if (startDate == null || startTime == null || endDate == null || endTime == null) {
                throw new InterruptionValidationException("Interruption in 'Restored' status must include both start date & time and end date & time");
            }
            LocalDateTime startDateTime = LocalDateTime.of(startDate,startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate,endTime);
            if (startDateTime.isAfter(endDateTime)){
                throw new InterruptionValidationException("The 'Restored' interruption end date & time should be greater than the end & time");
            }
            if (startDateTime.isAfter(LocalDateTime.now()) || endDateTime.isAfter(LocalDateTime.now())) {
                throw new InterruptionValidationException("The start date & time or the end date & time can not be in future");
            }
        } else {
            if (startDate == null || startTime == null || endDate != null || endTime != null) {
                throw new InterruptionValidationException("Interruption in 'Not Restored' status must include both start date & time only");
            }
            LocalDateTime startDateTime = LocalDateTime.of(startDate,startTime);
            if (startDateTime.isAfter(LocalDateTime.now())) {
                throw new InterruptionValidationException("The start date & time can not be in future");
            }
        }
    }

    private void validateInterruptionStatus() {
        if (interruptionType.equals(InterruptionType.TRANSIENT_TRIPPING) || interruptionType.equals(InterruptionType.SOURCE_CHANGEOVER)){
            if (interruptionStatus.equals(InterruptionStatus.NOT_RESTORED)) {
                throw new InterruptionValidationException("Interruption of type 'Transient Tripping' or 'Source Changeover' must be in 'Restored' status");
            }
        } else {
            if (interruptionStatus.equals(InterruptionStatus.RESTORED)) {
                throw new InterruptionValidationException("Interruption of type other than 'Transient Tripping' and 'Source Changeover' must be in 'Not Restored' status");
            }
        }

    }

    private void validateFaultNature() {
        if (FaultNature.findByFaultNature(faultNature) == null) {
            throw new InterruptionValidationException("The interruption must have fault nature as one of these values: EF/OC/EF_OC/HIGH_SET_OC");
        }
    }

    private void validateInterruptionType() {
        if (InterruptionType.findByInterruptionType(interruptionType) == null){
            throw new InterruptionValidationException("The interruption does not have a valid interruption type");
        }
    }

    private void validateInitialInterruption() {
        if (creationTimestamp != null || getId() != null) {
            throw new InterruptionValidationException("The interruption object is not in the correct state for initialization");
        }
    }

    public void restoreInterruption(LocalDate endDate, LocalTime endTime, String restoredBy) {
        interruptionStatus = InterruptionStatus.RESTORED;
        restorationTimeStamp = LocalDateTime.now();
        this.endDate = endDate;
        this.endTime = endTime;
        this.restoredBy = restoredBy;
    }

    public AssetId getFaultyAssetId() {
        return faultyAssetId;
    }

    public OfficeId getSubstationOfficeId() {
        return substationOfficeId;
    }

    public InterruptionType getInterruptionType() {
        return interruptionType;
    }

    public FaultNature getFaultNature() {
        return faultNature;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getRestoredBy() {
        return restoredBy;
    }

    public void setRestoredBy(String restoredBy) {
        this.restoredBy = restoredBy;
    }

    public LocalDateTime getRestorationTimeStamp() {
        return restorationTimeStamp;
    }

    public void setRestorationTimeStamp(LocalDateTime restorationTimeStamp) {
        this.restorationTimeStamp = restorationTimeStamp;
    }

    public InterruptionStatus getInterruptionStatus() {
        return interruptionStatus;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public static InterruptionBuilder newBuilder() {
        return new InterruptionBuilder();
    }


    public static final class InterruptionBuilder {
        private InterruptionId interruptionId;
        private AssetId faultyAssetId;
        private OfficeId substationOfficeId;
        private InterruptionType interruptionType;
        private FaultNature faultNature;
        private String createdBy;
        private LocalDateTime creationTimestamp;
        private String restoredBy;
        private LocalDateTime restorationTimeStamp;
        private InterruptionStatus interruptionStatus;
        private String cause;
        private LocalDate startDate;
        private LocalTime startTime;
        private LocalDate endDate;
        private LocalTime endTime;

        private InterruptionBuilder() {

        }

        public InterruptionBuilder interruptionId(InterruptionId val) {
            this.interruptionId = val;
            return this;
        }

        public InterruptionBuilder faultyAssetId(AssetId faultyAssetId) {
            this.faultyAssetId = faultyAssetId;
            return this;
        }

        public InterruptionBuilder substationOfficeId(OfficeId substationOfficeId) {
            this.substationOfficeId = substationOfficeId;
            return this;
        }

        public InterruptionBuilder interruptionType(InterruptionType interruptionType) {
            this.interruptionType = interruptionType;
            return this;
        }

        public InterruptionBuilder faultNature(FaultNature faultNature) {
            this.faultNature = faultNature;
            return this;
        }

        public InterruptionBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public InterruptionBuilder creationTimestamp(LocalDateTime val) {
            this.creationTimestamp = val;
            return this;
        }

        public InterruptionBuilder restoredBy(String val) {
            this.restoredBy = val;
            return this;
        }

        public InterruptionBuilder restorationTimeStamp(LocalDateTime val) {
            this.restorationTimeStamp = val;
            return this;
        }

        public InterruptionBuilder interruptionStatus(InterruptionStatus val) {
            this.interruptionStatus = val;
            return this;
        }

        public InterruptionBuilder cause(String val) {
            this.cause = val;
            return this;
        }

        public InterruptionBuilder startDate(LocalDate val) {
            this.startDate = val;
            return this;
        }

        public InterruptionBuilder startTime(LocalTime val) {
            this.startTime = val;
            return this;
        }

        public InterruptionBuilder endDate(LocalDate val) {
            this.endDate = val;
            return this;
        }

        public InterruptionBuilder endTime(LocalTime val) {
            this.endTime = val;
            return this;
        }

        public Interruption build() {
            return new Interruption(this);
        }
    }
}
