package com.wbsedcl.hr.management.domain.dto.create;


import com.wbsedcl.hr.management.domain.entity.*;
import com.wbsedcl.hr.management.domain.valueobject.Address;
import com.wbsedcl.trms.domain.valueobject.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class CreateEmployeeCommand {
    private final String firstName;
    private final String lastName;
    private final String fathersName;
    private final String employeeAddressLine1;
    private final String employeeAddressLine2;
    private final String policeStation;
    private final String postOffice;
    private final String district;
    private final String pinCode;
    private final String designation;
    private final String positionId;
    private final String officeId;
    private final String controllingOfficerUserId;
    private final String mobileNumber;
    private final String emailAddress;
    private final EmployeeType employeeType;
    private final LocalDate dateOfRetirement;
    private final LocalDate dateOfCurrentExtensionContractStart;
    private final LocalDate dateOfCurrentExtensionContractExpiry;
    private final LocalDate dateOfJoiningService;
    private final LocalDate dateOfBirth;
    private final EmployeeSkillLevel employeeSkillLevel;
    private final String employerVendorId;
}
