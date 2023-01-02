package com.wbsedcl.trms.substation.log.domain.entity;

public enum InterruptionType {
    TRANSIENT_TRIPPING, MAIN_POWER_FAIL, LOAD_SHEDDING, PLANNED_SHUTDOWN, EMERGENCY_SHUTDOWN, SUSTAINED_FAULT, SOURCE_CHANGEOVER;

    public static InterruptionType findByInterruptionType(InterruptionType interruptionType) {
        InterruptionType result = null;

        for (InterruptionType type : values()) {
            if (type.equals(interruptionType)) {
                result = type;
                break;
            }
        }
        return result;
    }
}
