package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.entity.Feeder;

import java.util.List;

public interface FeederService {
    List<Feeder> getFeedersBySubstationOfficeId(String substationOfficeId);
}
