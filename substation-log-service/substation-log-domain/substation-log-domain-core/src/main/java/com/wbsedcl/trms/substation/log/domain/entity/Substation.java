package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;

import java.util.List;

public class Substation extends Office{
    private String substationContactNumber;

    private List<Feeder> incomingSourceFeeders;

    public Substation (OfficeId officeId, String officeText, String substationContactNumber, List<Feeder> incomingSourceFeeders) {
        super(officeId,officeText);
        this.substationContactNumber = substationContactNumber;
        this.incomingSourceFeeders = incomingSourceFeeders;
    }

    public String getSubstationContactNumber() {
        return substationContactNumber;
    }

    public void setSubstationContactNumber(String substationContactNumber) {
        this.substationContactNumber = substationContactNumber;
    }

    public List<Feeder> getIncomingSourceFeeders() {
        return incomingSourceFeeders;
    }

    public void setIncomingSourceFeeders(List<Feeder> incomingSourceFeeders) {
        this.incomingSourceFeeders = incomingSourceFeeders;
    }

    public void addNewIncomingSourceFeeder(Feeder feeder) {
        if (feeder.getFeederType() == FeederType.INCOMING_33kV) {
            incomingSourceFeeders.add(feeder);
        }
    }
}
