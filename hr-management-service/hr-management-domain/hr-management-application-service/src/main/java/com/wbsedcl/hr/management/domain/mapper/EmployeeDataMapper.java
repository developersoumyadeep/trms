package com.wbsedcl.hr.management.domain.mapper;

import com.wbsedcl.hr.management.domain.dto.create.CreateEmployeeCommand;
import com.wbsedcl.hr.management.domain.entity.Employee;
import com.wbsedcl.hr.management.domain.entity.Office;
import com.wbsedcl.hr.management.domain.entity.Vendor;
import com.wbsedcl.hr.management.domain.ports.output.repository.EmployeeRepository;
import com.wbsedcl.hr.management.domain.ports.output.repository.OfficeRepository;
import com.wbsedcl.hr.management.domain.ports.output.repository.VendorRepository;
import com.wbsedcl.hr.management.domain.valueobject.Address;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataMapper {

    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;
    private final VendorRepository  vendorRepository;

    public EmployeeDataMapper(OfficeRepository officeRepository, EmployeeRepository employeeRepository, VendorRepository vendorRepository) {
        this.officeRepository = officeRepository;
        this.employeeRepository = employeeRepository;
        this.vendorRepository = vendorRepository;
    }

    public Employee createEmployeeCommandToEmployee(CreateEmployeeCommand command) {
            Address employeeAddress = new Address(command.getEmployeeAddressLine1(),
                    command.getEmployeeAddressLine2(),
                    command.getPoliceStation(),
                    command.getPostOffice(),
                    command.getDistrict(),
                    command.getPinCode()
                    );

            Office office = officeRepository.findOffice(command.getOfficeId()).get();
            Employee controllingOfficer = employeeRepository.findEmployeeById(command.getControllingOfficerUserId()).get();
            Vendor employingVendor =  vendorRepository.findVendorById(command.getEmployerVendorId()).get();

            return Employee.newBuilder()
                    .firstName(command.getFirstName())
                    .lastName(command.getLastName())
                    .fathersName(command.getFathersName())
                    .employeeAddress(employeeAddress)
                    .designation(command.getDesignation())
                    .positionId(command.getPositionId())
                    .office(office)
                    .controllingOfficer(controllingOfficer)
                    .mobileNumber(command.getMobileNumber())
                    .emailAddress(command.getEmailAddress())
                    .employeeType(command.getEmployeeType())
                    .dateOfRetirement(command.getDateOfRetirement())
                    .dateOfCurrentExtensionContractStart(command.getDateOfCurrentExtensionContractStart())
                    .dateOfCurrentExtensionContractExpiry(command.getDateOfCurrentExtensionContractExpiry())
                    .dateOfJoiningService(command.getDateOfJoiningService())
                    .dateOfBirth(command.getDateOfBirth())
                    .employeeSkillLevel(command.getEmployeeSkillLevel())
                    .employer(employingVendor)
                    .build();

    }
}
