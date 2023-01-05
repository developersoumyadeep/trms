package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.Asset;

import java.util.Optional;

public interface AssetRepository {
    Optional<Asset> findAsset(String assetId);
}
