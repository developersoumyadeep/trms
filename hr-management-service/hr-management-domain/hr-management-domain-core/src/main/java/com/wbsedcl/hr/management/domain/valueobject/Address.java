package com.wbsedcl.hr.management.domain.valueobject;

public class Address {
    private String addressLine1;
    private String addressLine2;
    private String policeStation;
    private String postOffice;
    private String district;
    private String pinCode;

    public Address(String addressLine1, String addressLine2, String policeStation, String postOffice, String district, String pinCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.policeStation = policeStation;
        this.postOffice = postOffice;
        this.district = district;
        this.pinCode = pinCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
