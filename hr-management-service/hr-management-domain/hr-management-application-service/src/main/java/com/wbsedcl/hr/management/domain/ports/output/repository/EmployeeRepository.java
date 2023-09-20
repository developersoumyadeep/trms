package com.wbsedcl.hr.management.domain.ports.output.repository;

import com.wbsedcl.hr.management.domain.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findEmployeeById(String userId);

}
