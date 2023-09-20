package com.wbsedcl.hr.management.domain.event;

import com.wbsedcl.trms.domain.event.DomainEvent;
import com.wbsedcl.hr.management.domain.entity.Employee;

import java.time.LocalDateTime;

public class EmployeeCreatedEvent implements DomainEvent<Employee> {
    private Employee employee;
    private LocalDateTime creationTimeStamp;

    public EmployeeCreatedEvent(Employee employee, LocalDateTime creationTimeStamp) {
        this.employee = employee;
        this.creationTimeStamp = creationTimeStamp;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }
}
