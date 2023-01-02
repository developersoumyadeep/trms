package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.AssetId;

public class Asset extends BaseEntity<AssetId> implements AggregateRoot {
    public Asset() {
    }

    public Asset(AssetId assetId) {
        super.setId(assetId);
    }
}
