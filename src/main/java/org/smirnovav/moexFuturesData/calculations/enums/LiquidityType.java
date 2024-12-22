package org.smirnovav.moexFuturesData.calculations.enums;

public enum LiquidityType {
    VAL(1),
    VOL(2);


    private int liquidityType;


    LiquidityType(int liquidityType) {
        this.liquidityType = liquidityType;
    }

    public int getLiquidityType() {
        return liquidityType;
    }
}
