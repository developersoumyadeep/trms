package com.wbsedcl.trms.feeder.management.domain.ports.output.repository;

import com.wbsedcl.trms.feeder.management.domain.entity.Office;


import java.util.Optional;

public interface OfficeRepository {
    Optional<Office> findOffice(String officeId);
}
