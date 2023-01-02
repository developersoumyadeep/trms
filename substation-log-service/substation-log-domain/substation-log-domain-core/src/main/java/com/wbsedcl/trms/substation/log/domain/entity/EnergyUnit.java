package com.wbsedcl.trms.substation.log.domain.entity;

public enum EnergyUnit {
    WH,KWH,MWH;

    public static EnergyUnit findByEnergyUnit(EnergyUnit energyUnit) {
        EnergyUnit result = null;

        for (EnergyUnit unit: values()) {
            if (unit.equals(energyUnit)){
                result = unit;
                break;
            }
        }
        return result;
    }
}
