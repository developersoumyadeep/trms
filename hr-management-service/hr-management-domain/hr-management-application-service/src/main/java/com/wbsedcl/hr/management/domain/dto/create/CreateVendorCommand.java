package com.wbsedcl.hr.management.domain.dto.create;

import com.wbsedcl.hr.management.domain.valueobject.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateVendorCommand {
    private String companyName;
    private String proprietorFirstName;
    private String proprietorLastName;
    private String vendorAddressLine1;
    private String vendorAddressLine2;
    private String policeStation;
    private String postOffice;
    private String district;
    private String pinCode;
    private String mobileNumber;
    private String emailAddress;
}
