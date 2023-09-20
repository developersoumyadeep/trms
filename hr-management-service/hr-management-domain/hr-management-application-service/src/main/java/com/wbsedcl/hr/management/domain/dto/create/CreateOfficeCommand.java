package com.wbsedcl.hr.management.domain.dto.create;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateOfficeCommand {
    private String officeId;
    private String officeName;
    private String officeAddressLine1;
    private String officeAddressLine2;
    private String policeStation;
    private String postOffice;
    private String district;
    private String pinCode;
}
