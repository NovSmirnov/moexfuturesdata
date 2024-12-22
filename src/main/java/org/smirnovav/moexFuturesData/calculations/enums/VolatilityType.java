package org.smirnovav.moexFuturesData.calculations.enums;

public enum VolatilityType {
    VOL(1),
    LEV(2);


    private int volatilityType;

    VolatilityType(int volatilityType) {
        this.volatilityType = volatilityType;
    }

    public int getVolatilityType() {
        return volatilityType;
    }
}
