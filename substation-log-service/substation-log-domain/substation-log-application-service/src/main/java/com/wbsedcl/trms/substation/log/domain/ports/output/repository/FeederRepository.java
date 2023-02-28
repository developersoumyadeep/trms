package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.Feeder;

import java.util.Optional;

public interface FeederRepository {
    Optional<Feeder> findFeeder(String feederId);
}
