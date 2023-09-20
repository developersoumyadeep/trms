package com.wbsedcl.hr.management.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.hr.management.domain.exception.OfficeValidationException;
import com.wbsedcl.hr.management.domain.valueobject.Address;

import java.util.ArrayList;
import java.util.List;

public class Office extends BaseEntity<OfficeId> implements AggregateRoot {
    private final String officeName;
    private final Address officeAddress;
    private final Office parentOffice;
    private List<Office> subOffices;
    private List<Employee> employees;
    private final OfficeType officeType;
    private final ManningType manningType;

    private Office(OfficeBuilder officeBuilder) {
        setId(officeBuilder.officeId);
        this.officeName = officeBuilder.officeName;
        this.officeAddress = officeBuilder.officeAddress;
        this.parentOffice = officeBuilder.parentOffice;
        this.subOffices = officeBuilder.subOffices;
        this.employees = officeBuilder.employees;
        this.officeType = officeBuilder.officeType;
        this.manningType = officeBuilder.manningType;
    }

    public String getOfficeName() {
        return officeName;
    }

    public Address getOfficeAddress() {
        return officeAddress;
    }

    public Office getParentOffice() {
        return parentOffice;
    }

    public List<Office> getSubOffices() {
        return subOffices;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public OfficeType getOfficeType() {return  officeType;}

    public ManningType getManningType() {return manningType;}

    public void initializeOffice() {
        subOffices = new ArrayList<>();
        employees = new ArrayList<>();
    }

    public void validateOffice() {
        validateOfficeName();
        validateOfficeAddress();
        validateParentOffice();
        validateSubOffices();
        validateEmployees();
        validateOfficeType();
    }

    private void validateOfficeName() {
        if (officeName == null || officeName.length() <= 5) {
            throw new OfficeValidationException("Invalid office name");
        }
    }

    private void validateOfficeAddress() {
        if (officeAddress == null ){
            throw new OfficeValidationException("Office address can not be null");
        }
    }


    private void validateParentOffice() {
        if (officeType == OfficeType.HQ && parentOffice != null) {
            throw new OfficeValidationException("Head quarters can't have any parent office");
        }
        if (officeType != OfficeType.HQ && parentOffice == null) {
            throw new OfficeValidationException("Any office other than Head Quarters must have a valid parent office");
        }
    }

    private void validateSubOffices() {
        if (officeType == OfficeType.REGIONAL_OFFICE || officeType == OfficeType.ZONAL_OFFICE || officeType == OfficeType.HQ || officeType == OfficeType.DIVISION_OFFICE) {
            if (subOffices.size() == 0) {
                throw new OfficeValidationException("The controlling office must contain one or more sub-offices");
            }
        } else if (subOffices.size() != 0) {
            throw new OfficeValidationException("Substations can't have sub-offices");
        }

    }

    private void validateEmployees() {
         if (employees == null) {
             throw new OfficeValidationException("The employees object is uninitialized");
         }
         if (employees.size() == 0 && manningType != ManningType.OUTSOURCED) {
             throw new OfficeValidationException("The departmentally manned office must have at least one employee");
         }
    }

    private void validateOfficeType() {
        if (officeType!=OfficeType.SUBSTATION && officeType != OfficeType.DIVISION_OFFICE && officeType != OfficeType.REGIONAL_OFFICE && officeType != OfficeType.ZONAL_OFFICE && officeType != OfficeType.HQ) {
            throw new OfficeValidationException("Invalid office type");
        }
    }

    public static final class OfficeBuilder {
        private OfficeId officeId;
        private String officeName;
        private Address officeAddress;
        private Office parentOffice;
        private List<Office> subOffices;
        private List<Employee> employees;
        private OfficeType officeType;
        private ManningType manningType;

        private OfficeBuilder() {

        }

        public OfficeBuilder officeId(OfficeId officeId) {
            this.officeId = officeId;
            return this;
        }

        public OfficeBuilder officeName(String officeName) {
            this.officeName = officeName;
            return this;
        }

        public OfficeBuilder officeAddress(Address officeAddress) {
            this.officeAddress = officeAddress;
            return this;
        }

        public OfficeBuilder parentOffice(Office parentOffice) {
            this.parentOffice = parentOffice;
            return this;
        }

        public OfficeBuilder subOffices(List<Office> subOffices) {
            this.subOffices = subOffices;
            return this;
        }

        public OfficeBuilder employees(List<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public OfficeBuilder officeType(OfficeType officeType) {
            this.officeType = officeType;
            return this;
        }

        public OfficeBuilder manningType(ManningType manningType) {
            this.manningType = manningType;
            return this;
        }

        public Office build() {
            return new Office(this);
        }
    }


}
