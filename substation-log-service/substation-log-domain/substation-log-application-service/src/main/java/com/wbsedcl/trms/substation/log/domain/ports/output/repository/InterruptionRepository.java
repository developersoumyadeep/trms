package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.Interruption;

import java.util.List;

public interface InterruptionRepository {
    List<Interruption> findAllInterruptionsBySubstationOfficeId(String substationOfficeId);
}
