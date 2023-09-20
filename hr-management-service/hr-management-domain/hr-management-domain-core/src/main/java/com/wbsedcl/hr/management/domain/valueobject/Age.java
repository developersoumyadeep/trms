package com.wbsedcl.hr.management.domain.valueobject;

public class Age {
    private int years;
    private int months;

    public Age(int years, int months) {
        this.years = years;
        this.months = months;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }
}
