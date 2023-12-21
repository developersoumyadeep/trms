package com.wbsedcl.trms.feeder.management.domain.entity;

public enum FeederType {
    INCOMING_33KV, OUTGOING_33KV, PTR_BAY, OUTGOING_11KV, INCOMING_11KV;

    public static FeederType findByFeederType(FeederType feederType) {
        FeederType result = null;

        for (FeederType type : values()) {
            if (type.equals(feederType)) {
                result = type;
                break;
            }
        }
        return result;
    }
}
