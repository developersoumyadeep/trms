package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.FeederLoadingHistoryId;
import java.time.LocalDate;
import java.time.LocalTime;

public class FeederLoadingHistory extends BaseEntity<FeederLoadingHistoryId> implements AggregateRoot {
    private String feederId;
    private LocalDate loadedFromDate;
    private LocalTime loadedFromTime;
    private LocalDate loadedToDate;
    private LocalTime loadedToTime;

    private FeederLoadingHistory(FeederLoadingHistoryBuilder builder) {
        setId(builder.feederLoadingHistoryId);
        this.feederId = builder.feederId;
        this.loadedFromDate = builder.loadedFromDate;
        this.loadedFromTime = builder.loadedFromTime;
        this.loadedToDate = builder.loadedToDate;
        this.loadedToTime = builder.loadedToTime;
    }

    public String getFeederId() {
        return feederId;
    }

    public LocalDate getLoadedFromDate() {
        return loadedFromDate;
    }

    public LocalTime getLoadedFromTime() {
        return loadedFromTime;
    }

    public LocalDate getLoadedToDate() {
        return loadedToDate;
    }

    public LocalTime getLoadedToTime() {
        return loadedToTime;
    }

    public void setFeederId(String feederId) {
        this.feederId = feederId;
    }

    public void setLoadedFromDate(LocalDate loadedFromDate) {
        this.loadedFromDate = loadedFromDate;
    }

    public void setLoadedFromTime(LocalTime loadedFromTime) {
        this.loadedFromTime = loadedFromTime;
    }

    public void setLoadedToDate(LocalDate loadedToDate) {
        this.loadedToDate = loadedToDate;
    }

    public void setLoadedToTime(LocalTime loadedToTime) {
        this.loadedToTime = loadedToTime;
    }

    public static FeederLoadingHistoryBuilder builder() {
        return new FeederLoadingHistoryBuilder();
    }
   public static final class FeederLoadingHistoryBuilder {
        private FeederLoadingHistoryId feederLoadingHistoryId;
        private String feederId;
        private LocalDate loadedFromDate;
        private LocalTime loadedFromTime;
        private LocalDate loadedToDate;
        private LocalTime loadedToTime;

        public FeederLoadingHistoryBuilder feederLoadingHistoryId(FeederLoadingHistoryId feederLoadingHistoryId) {
            this.feederLoadingHistoryId = feederLoadingHistoryId;
            return this;
        }

        public FeederLoadingHistoryBuilder feederId(String feederId) {
            this.feederId = feederId;
            return this;
        }

        public FeederLoadingHistoryBuilder loadedFromDate(LocalDate loadedFromDate) {
            this.loadedFromDate = loadedFromDate;
            return this;
        }

        public FeederLoadingHistoryBuilder loadedFromTime (LocalTime loadedFromTime) {
            this.loadedFromTime = loadedFromTime;
            return this;
        }

        public FeederLoadingHistoryBuilder loadedToDate(LocalDate loadedToDate) {
            this.loadedToDate = loadedToDate;
            return this;
        }

        public FeederLoadingHistoryBuilder loadedToTime(LocalTime loadedToTime) {
            this.loadedToTime = loadedToTime;
            return this;
        }

        public FeederLoadingHistory build() {
            return new FeederLoadingHistory(this);
        }
    }




}
