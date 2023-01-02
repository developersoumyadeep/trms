package com.wbsedcl.trms.substation.log.domain.entity;

public enum FaultNature {
    EF_OC, EF, OC, HIGH_SET_OC;

    public static FaultNature findByFaultNature(FaultNature faultNature) {
        FaultNature result = null;

        for (FaultNature nature : values()) {
            if (nature.equals(faultNature)) {
                result = nature;
                break;
            }
        }
        return result;
    }
}
