package com.wbsedcl.hr.management.domain.entity;

import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.hr.management.domain.valueobject.Address;

import java.time.LocalDate;

public class Employee extends User {
    private final String firstName;
    private final String lastName;
    private final String fathersName;
    private final Address employeeAddress;
    private final String designation;
    private final String positionId;
    private final Office office;
    private final Employee controllingOfficer;
    private final String mobileNumber;
    private final String emailAddress;
    private final EmployeeType employeeType;
    private final LocalDate dateOfRetirement;
    private final LocalDate dateOfCurrentExtensionContractStart;
    private final LocalDate dateOfCurrentExtensionContractExpiry;
    private final LocalDate dateOfJoiningService;
    private final LocalDate dateOfBirth;
    private final EmployeeSkillLevel employeeSkillLevel;
    private final Vendor employer;



    private Employee(EmployeeBuilder employeeBuilder) {
        setId(employeeBuilder.userId);
        this.firstName = employeeBuilder.firstName;
        this.lastName = employeeBuilder.lastName;
        this.fathersName = employeeBuilder.fathersName;
        this.employeeAddress = employeeBuilder.employeeAddress;
        this.designation = employeeBuilder.designation;
        this.positionId = employeeBuilder.positionId;
        this.office = employeeBuilder.office;
        this.controllingOfficer = employeeBuilder.controllingOfficer;
        this.mobileNumber = employeeBuilder.mobileNumber;
        this.emailAddress = employeeBuilder.emailAddress;
        this.employeeType = employeeBuilder.employeeType;
        this.dateOfRetirement = employeeBuilder.dateOfRetirement;
        this.dateOfCurrentExtensionContractStart = employeeBuilder.dateOfCurrentExtensionContractStart;
        this.dateOfCurrentExtensionContractExpiry = employeeBuilder.dateOfCurrentExtensionContractExpiry;
        this.dateOfJoiningService = employeeBuilder.dateOfJoiningService;
        this.dateOfBirth = employeeBuilder.dateOfBirth;
        this.employeeSkillLevel = employeeBuilder.employeeSkillLevel;
        this.employer = employeeBuilder.employer;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getEmployeeAddress() {
        return employeeAddress;
    }

    public String getDesignation() {
        return designation;
    }

    public String getPositionId() {
        return positionId;
    }

    public Office getOffice() {
        return office;
    }

    public Employee getControllingOfficer() {
        return controllingOfficer;
    }

    public String getFathersName() {
        return fathersName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public LocalDate getDateOfRetirement() {
        return dateOfRetirement;
    }

    public LocalDate getDateOfCurrentExtensionContractStart() {
        return dateOfCurrentExtensionContractStart;
    }

    public LocalDate getDateOfCurrentExtensionContractExpiry() {
        return dateOfCurrentExtensionContractExpiry;
    }

    public LocalDate getDateOfJoiningService() {
        return dateOfJoiningService;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public EmployeeSkillLevel getEmployeeSkillLevel() {
        return employeeSkillLevel;
    }

    public Vendor getEmployer() {
        return employer;
    }

    public static EmployeeBuilder newBuilder() {
        return new EmployeeBuilder();
    }

    public static final class EmployeeBuilder {
        private UserId userId;
        private String firstName;
        private String lastName;
        private String fathersName;
        private Address employeeAddress;
        private String designation;
        private String positionId;
        private Office office;
        private Employee controllingOfficer;
        public String mobileNumber;
        private String emailAddress;
        private EmployeeType employeeType;
        private LocalDate dateOfRetirement;
        private LocalDate dateOfCurrentExtensionContractStart;
        private LocalDate dateOfCurrentExtensionContractExpiry;
        private LocalDate dateOfJoiningService;
        private LocalDate dateOfBirth;
        private  EmployeeSkillLevel employeeSkillLevel;
        private Vendor employer;

        private EmployeeBuilder() {

        }

        public EmployeeBuilder userId(UserId userId) {
            this.userId = userId;
            return this;
        }

        public EmployeeBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EmployeeBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EmployeeBuilder fathersName(String fathersName) {
            this.fathersName = fathersName;
            return this;
        }

        public EmployeeBuilder employeeAddress(Address employeeAddress) {
            this.employeeAddress = employeeAddress;
            return this;
        }

        public EmployeeBuilder designation(String designation) {
            this.designation = designation;
            return this;
        }

        public EmployeeBuilder positionId(String positionId) {
            this.positionId = positionId;
            return this;
        }

        public EmployeeBuilder office(Office office) {
            this.office = office;
            return this;
        }

        public EmployeeBuilder controllingOfficer(Employee controllingOfficer) {
            this.controllingOfficer = controllingOfficer;
            return this;
        }

        public EmployeeBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public EmployeeBuilder emailAddress(String emailAddress){
            this.emailAddress = emailAddress;
            return this;
        }

        public EmployeeBuilder employeeType(EmployeeType employeeType){
            this.employeeType = employeeType;
            return this;

        }
        public EmployeeBuilder dateOfRetirement(LocalDate dateOfRetirement) {
            this.dateOfRetirement = dateOfRetirement;
            return this;
        }

        public EmployeeBuilder dateOfCurrentExtensionContractStart(LocalDate dateOfCurrentExtensionContractStart) {
            this.dateOfCurrentExtensionContractStart = dateOfCurrentExtensionContractStart;
            return this;
        }

        public EmployeeBuilder dateOfCurrentExtensionContractExpiry(LocalDate dateOfCurrentExtensionContractExpiry)
        {
            this.dateOfCurrentExtensionContractExpiry = dateOfCurrentExtensionContractExpiry;
            return this;
        }

        public EmployeeBuilder dateOfJoiningService(LocalDate dateOfJoiningService) {
            this.dateOfJoiningService = dateOfJoiningService;
            return this;
        }

        public EmployeeBuilder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public EmployeeBuilder employeeSkillLevel(EmployeeSkillLevel employeeSkillLevel) {
            this.employeeSkillLevel = employeeSkillLevel;
            return this;
        }

        public EmployeeBuilder employer(Vendor employer) {
            this.employer = employer;
            return this;
        }
        public Employee build() {
            return new Employee(this);
        }
    }


}
