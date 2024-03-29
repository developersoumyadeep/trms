package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.valueobject.InterruptionId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import java.util.logging.Logger;


public class Interruption extends BaseEntity<InterruptionId> implements AggregateRoot {

    Logger logger = Logger.getLogger(Interruption.class.getName());
    private final Feeder faultyFeeder;
    private final FeederId sourceChangeOverFromFeederId;
    private final FeederId sourceChangeOverToFeederId;
    private final OfficeId substationOfficeId;
    private final InterruptionType interruptionType;
    private final FaultNature faultNature;
    private final UserId createdBy;
    private final LocalDate startDate;
    private final LocalTime startTime;
    private LocalDateTime creationTimeStamp;
    private UserId restoredBy;
    private LocalDateTime restorationTimeStamp;
    private InterruptionStatus interruptionStatus;
    private String cause;
    private LocalDate endDate;
    private LocalTime endTime;

    private Interruption(InterruptionBuilder interruptionBuilder) {
        setId(interruptionBuilder.interruptionId);
        faultyFeeder = interruptionBuilder.faultyFeeder;
        substationOfficeId = interruptionBuilder.substationOfficeId;
        interruptionType = interruptionBuilder.interruptionType;
        faultNature = interruptionBuilder.faultNature;
        createdBy = interruptionBuilder.createdBy;
        creationTimeStamp = interruptionBuilder.creationTimestamp;
        restoredBy = interruptionBuilder.restoredBy;
        restorationTimeStamp = interruptionBuilder.restorationTimeStamp;
        interruptionStatus = interruptionBuilder.interruptionStatus;
        cause = interruptionBuilder.cause;
        startDate = interruptionBuilder.startDate;
        startTime = interruptionBuilder.startTime;
        endDate = interruptionBuilder.endDate;
        endTime = interruptionBuilder.endTime;
        sourceChangeOverFromFeederId = interruptionBuilder.sourceChangeOverFromFeederId;
        sourceChangeOverToFeederId = interruptionBuilder.sourceChangeOverToFeederId;
    }


    public void initializeInterruption() {
        setId(new InterruptionId(UUID.randomUUID()));
        creationTimeStamp = LocalDateTime.now();
    }

    public void validateInterruption() {
        validateInitialInterruption();
        validateInterruptionType();
        validateFaultNature();
        validateInterruptionStatus();
        validateInterruptionHrs();
    }

    private void validateInterruptionHrs() {
        logger.info("validating interruption hours for feeder " + getFaultyFeeder().getFeederName());
        //1. If interruption is in "Restored" status,then both the start date & time and end date & time should be present.
        //2. The End date & time should be at a later point of time than the start date & time
        //3. If interruption is in "Not Restored" status, then only the start date & time should be present.
        //4. The start date & time and end date & time must not be in a future point of time
        if (interruptionStatus.equals(InterruptionStatus.RESTORED)) {
            if (startDate == null || startTime == null || endDate == null || endTime == null) {
                throw new InterruptionValidationException("Interruption in 'Restored' status must include both start date & time and end date & time");
            }
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            if (startDateTime.isAfter(endDateTime)) {
                logger.info("The 'Restored' interruption end date & time should be greater than the start date & time");
                throw new InterruptionValidationException("The 'Restored' interruption end date & time should be greater than the start date & time");
            }
            if (startDateTime.isAfter(LocalDateTime.now()) || endDateTime.isAfter(LocalDateTime.now())) {
                throw new InterruptionValidationException("The start date & time or the end date & time can not be in future");
            }
        } else {
            if (startDate == null || startTime == null || endDate != null || endTime != null) {
                throw new InterruptionValidationException("Interruption in 'Not Restored' status must include both start date & time only");
            }
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            if (startDateTime.isAfter(LocalDateTime.now())) {
                throw new InterruptionValidationException("The start date & time can not be in future");
            }
        }
    }

    private void validateInterruptionStatus() {
        logger.info("validating interruption status for feeder " + faultyFeeder.getFeederName());
        if (interruptionType.equals(InterruptionType.TRANSIENT_TRIPPING)) {
            if (interruptionStatus.equals(InterruptionStatus.NOT_RESTORED)) {
                throw new InterruptionValidationException("Interruption of type 'Transient Tripping' must be in 'Restored' status");
            }
        } else if (interruptionType.equals(InterruptionType.EMERGENCY_SHUTDOWN) || interruptionType.equals(InterruptionType.LOAD_SHEDDING)
                || interruptionType.equals(InterruptionType.SUSTAINED_FAULT) || interruptionType.equals(InterruptionType.PLANNED_SHUTDOWN)) {
            if (interruptionStatus.equals(InterruptionStatus.RESTORED)) {
                throw new InterruptionValidationException("Interruption of type 'Emergency shutdown', 'Breakdown', 'Planned shutdown', 'Load shedding' must be in 'Not Restored' status");
            }
        }

    }

    private void validateFaultNature() {
        logger.info("validating interruption fault nature for feeder " + faultyFeeder.getFeederName());
        if (interruptionType == InterruptionType.SOURCE_CHANGEOVER || interruptionType == InterruptionType.PLANNED_SHUTDOWN || interruptionType == InterruptionType.EMERGENCY_SHUTDOWN || interruptionType == InterruptionType.LOAD_SHEDDING || interruptionType == InterruptionType.MAIN_POWER_FAIL) {
            if (faultNature != null) {
                throw new InterruptionValidationException("Interruption of type source change over, planned or emergency shutdowns, and main power fail can not have any fault nature attribute set");
            }
        } else {
            logger.info("Input fault nature " + faultNature);
            if (FaultNature.findByFaultNature(faultNature) == null) {
                throw new InterruptionValidationException("The interruption must have fault nature as one of these values: EF/OC/EF_OC/HIGH_SET_OC");
            }
        }

    }

    private void validateInterruptionType() {
        logger.info("validating interruption type for feeder :" + faultyFeeder.getId().getValue());
        if (InterruptionType.findByInterruptionType(interruptionType) == null) {
            throw new InterruptionValidationException("The interruption does not have a valid interruption type");
        } else if (faultyFeeder.getFeederType() == FeederType.INCOMING_33kV && interruptionType == InterruptionType.MAIN_POWER_FAIL) {
            throw new InterruptionValidationException("Interruption type of main power fail should be used only for outgoing 11kV, outgoing 33kV, 11kV incomers and PTRs");
        }


    }

    private void validateInitialInterruption() {
        logger.info("validating initial interruption for feeder " + faultyFeeder.getFeederName());
        if (creationTimeStamp != null || getId() != null) {
            throw new InterruptionValidationException("The interruption object is not in the correct state for initialization");
        }
    }

    public void restoreInterruption(LocalDate endDate, LocalTime endTime, UserId restoredBy, String cause) {
        logger.info("restoring interruption with end date " + endDate + " end time " + endTime + " restoredByUserId " + restoredBy + " for feeder " + faultyFeeder.getFeederName());
        interruptionStatus = InterruptionStatus.RESTORED;
        restorationTimeStamp = LocalDateTime.now();
        this.endDate = endDate;
        this.endTime = endTime;
        this.restoredBy = restoredBy;
        this.cause = cause;
    }


    public Feeder getFaultyFeeder() {
        return faultyFeeder;
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

    public UserId getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public UserId getRestoredBy() {
        return restoredBy;
    }

    public void setRestoredBy(UserId restoredBy) {
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

    public void setInterruptionStatus(InterruptionStatus interruptionStatus) {
        this.interruptionStatus = interruptionStatus;
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

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public FeederId getSourceChangeOverFromFeederId() {
        return sourceChangeOverFromFeederId;
    }

    public FeederId getSourceChangeOverToFeederId() {
        return sourceChangeOverToFeederId;
    }


    public static InterruptionBuilder newBuilder() {
        return new InterruptionBuilder();
    }


    public static final class InterruptionBuilder {

        private InterruptionId interruptionId;
        private Feeder faultyFeeder;

        private FeederId sourceChangeOverFromFeederId;
        private FeederId sourceChangeOverToFeederId;
        private OfficeId substationOfficeId;
        private InterruptionType interruptionType;
        private FaultNature faultNature;
        private UserId createdBy;
        private LocalDateTime creationTimestamp;
        private UserId restoredBy;
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

        public InterruptionBuilder faultyFeeder(Feeder faultyFeeder) {
            this.faultyFeeder = faultyFeeder;
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

        public InterruptionBuilder createdBy(UserId createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public InterruptionBuilder creationTimeStamp(LocalDateTime val) {
            this.creationTimestamp = val;
            return this;
        }

        public InterruptionBuilder restoredBy(UserId val) {
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

        public InterruptionBuilder sourceChangeOverFromFeederId(FeederId sourceChangeOverFromFeederId) {
            this.sourceChangeOverFromFeederId = sourceChangeOverFromFeederId;
            return this;
        }

        public InterruptionBuilder sourceChangeOverToFeederId(FeederId sourceChangeOverToFeederId) {
            this.sourceChangeOverToFeederId = sourceChangeOverToFeederId;
            return this;
        }

        public Interruption build() {
            return new Interruption(this);
        }
    }

    @Override
    public String toString() {
        return "Interruption {interruptionId =" + (getId() == null ? "Id not generated" : getId().getValue()) + " faultyFeederName =" + faultyFeeder.getFeederName() + "}";
    }
}
