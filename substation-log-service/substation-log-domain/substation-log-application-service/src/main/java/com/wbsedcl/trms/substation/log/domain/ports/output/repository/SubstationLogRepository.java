package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.Interruption;

public interface SubstationLogRepository {
    Interruption save(Interruption interruption);
}
