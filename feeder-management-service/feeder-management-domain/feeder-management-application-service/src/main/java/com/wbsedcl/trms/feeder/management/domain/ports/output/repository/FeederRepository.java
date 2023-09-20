package com.wbsedcl.trms.feeder.management.domain.ports.output.repository;

import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;

import java.util.Optional;

public interface FeederRepository {

    Feeder saveFeeder(Feeder feeder);
    Optional<Feeder> findFeeder(String feederId);

}
