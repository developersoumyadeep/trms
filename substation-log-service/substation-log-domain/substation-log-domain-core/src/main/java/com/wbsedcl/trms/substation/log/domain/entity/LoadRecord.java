package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.exception.LoadRecordValidationException;
import com.wbsedcl.trms.substation.log.domain.valueobject.FeederId;
import com.wbsedcl.trms.substation.log.domain.valueobject.LoadRecordId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class LoadRecord extends BaseEntity<LoadRecordId> implements AggregateRoot {
    private final FeederId feederId;
    private final OfficeId substationOfficeId;
    private LocalDate date;
    private LocalTime time;
    private final double load;
    private final UserId  recordedBy;


    private LoadRecord(LoadRecordBuilder builder) {
        setId(builder.id);
        feederId = builder.feederId;
        substationOfficeId = builder.substationOfficeId;
        date = builder.date;
        time = builder.time;
        load = builder.load;
        recordedBy = builder.recordedBy;
    }

    public void initialize(){
        setId(new LoadRecordId(UUID.randomUUID()));
        date = LocalDate.now();
        time = LocalTime.now();
    }

    public void validate() {
        validateInitialLoadRecord();
        validateLoad();
    }

    public void validateInitialLoadRecord() {
        if (getId() != null || date != null || time != null) {
            throw new LoadRecordValidationException("The load record is not in a correct state for initialization");
        }
    }

    public void validateLoad(){
        if (load < 0) {
            throw new LoadRecordValidationException("The load record must have zero or positive value");
        }
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

    public double getLoad() {
        return load;
    }

    public UserId getRecordedBy() {
        return recordedBy;
    }

    public static LoadRecordBuilder newBuilder() {
        return new LoadRecordBuilder();
    }

    public static final class LoadRecordBuilder {
        private LoadRecordId id;
        private FeederId feederId;
        private OfficeId substationOfficeId;
        private LocalDate date;
        private LocalTime time;
        private double load;

        private UserId recordedBy;

        private LoadRecordBuilder() {

        }

        public LoadRecordBuilder loadRecordId(LoadRecordId loadRecordId) {
            this.id = loadRecordId;
            return this;
        }

        public LoadRecordBuilder feederId(FeederId feederId) {
            this.feederId = feederId;
            return this;
        }

        public LoadRecordBuilder substationOfficeId(OfficeId substationOfficeId){
            this.substationOfficeId = substationOfficeId;
            return this;
        }

        public LoadRecordBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public LoadRecordBuilder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public LoadRecordBuilder load(Double load) {
            this.load = load;
            return this;
        }

        public LoadRecordBuilder recordedBy(UserId userId){
            this.recordedBy = userId;
            return this;
        }

        public LoadRecord build() {
            return new LoadRecord(this);
        }

    }
}
