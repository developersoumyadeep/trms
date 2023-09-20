package com.wbsedcl.hr.management.domain;

import com.wbsedcl.hr.management.domain.entity.*;
import com.wbsedcl.hr.management.domain.event.EmployeeCreatedEvent;
import com.wbsedcl.hr.management.domain.event.OfficeCreatedEvent;
import com.wbsedcl.hr.management.domain.event.VendorCreatedEvent;
import com.wbsedcl.hr.management.domain.valueobject.Age;

public interface HRManagementDomainService {
    public OfficeCreatedEvent validateAndInitiateNewOffice(Office office);
    public EmployeeCreatedEvent validateAndInitiateNewEmployee(Employee employee);
    public VendorCreatedEvent validateAndInitiateNewVendor(Vendor vendor);
    public Employee editEmployeeContactDetails(Employee employee);
    public Employee runEmployeeJoiningAction(Employee employee);
    public Employee runEmployeeRetirementAction(Employee employee);
    public Employee runEmployeeTransferAction(Employee employee);
    public Employee runEmployeeExtensionContractRenewal(Employee employee);
    public Employee runEmployeeExtensionContractExpiry(Employee employee);
    public Age getEmployeeAge(Employee employee);
    public Employee updateEmployeeSkillLevel(Employee employee, EmployeeSkillLevel employeeSkillLevel);
    public Employee updateEmployeeEmployerDetails(Employee employee, Vendor vendor);
    public Employee updateEmployeePositionId(Employee employee, String positionId);

}
