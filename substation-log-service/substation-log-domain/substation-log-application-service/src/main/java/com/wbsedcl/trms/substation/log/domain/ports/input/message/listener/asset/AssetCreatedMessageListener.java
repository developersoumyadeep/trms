package com.wbsedcl.trms.substation.log.domain.ports.input.message.listener.asset;

import com.wbsedcl.trms.substation.log.domain.dto.message.AssetCreatedResponse;

public interface AssetCreatedMessageListener {
    void newAssetCreated(AssetCreatedResponse assetCreatedResponse);
}
