package com.dilansriyantha.backend.Enums;

import java.util.HashMap;
import java.util.Map;

public enum FinanceType {
    INCOME(0),
    EXPENSE(1);

    private int value;
    private static final Map<Integer, FinanceType> map = new HashMap<>();

    private FinanceType(int value) {
        this.value = value;
    }

    static {
        for(var financeType : FinanceType.values())
            map.put(financeType.value, financeType);
    }

    public static FinanceType valueOf(int value) {
        return map.get(value);
    }
}
