package com.wbsedcl.hr.management.domain.ports.output.repository;

import com.wbsedcl.hr.management.domain.entity.Office;

import java.util.Optional;

public interface OfficeRepository {
    Optional<Office> findOffice(String officeId);
}
