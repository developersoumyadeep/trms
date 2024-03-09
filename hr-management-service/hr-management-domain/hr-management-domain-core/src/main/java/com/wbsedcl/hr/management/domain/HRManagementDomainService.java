package com.wbsedcl.hr.management.domain;

import com.wbsedcl.hr.management.domain.entity.*;
import com.wbsedcl.hr.management.domain.event.EmployeeCreatedEvent;
import com.wbsedcl.hr.management.domain.event.OfficeCreatedEvent;
import com.wbsedcl.hr.management.domain.event.VendorCreatedEvent;
import com.wbsedcl.hr.management.domain.valueobject.Age;

public interface HRManagementDomainService {
    OfficeCreatedEvent validateAndInitiateNewOffice(Office office);
    EmployeeCreatedEvent validateAndInitiateNewEmployee(Employee employee);
    VendorCreatedEvent validateAndInitiateNewVendor(Vendor vendor);
    Employee editEmployeeContactDetails(Employee employee);
    Employee runEmployeeJoiningAction(Employee employee);
    Employee runEmployeeRetirementAction(Employee employee);
    Employee runEmployeeTransferAction(Employee employee);
    Employee runEmployeeExtensionContractRenewal(Employee employee);
    Employee runEmployeeExtensionContractExpiry(Employee employee);
    Age getEmployeeAge(Employee employee);
    Employee updateEmployeeSkillLevel(Employee employee, EmployeeSkillLevel employeeSkillLevel);
    Employee updateEmployeeEmployerDetails(Employee employee, Vendor vendor);
    Employee updateEmployeePositionId(Employee employee, String positionId);

}
