package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.Office;

import java.util.Optional;

public interface OfficeRepository {
    Optional<Office> findOffice(String officeId);
}
