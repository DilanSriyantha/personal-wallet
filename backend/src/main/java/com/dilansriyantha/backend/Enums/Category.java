package com.dilansriyantha.backend.Enums;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    HOUSE_RENT(0),
    UTILITY_BILL(1),
    DOMESTIC_GAS(2),
    GROCERIES(3),
    MEDICINE(4),
    ENTERTAINMENT(5),
    TRANSPORT(6),
    TELECOMMUNICATION(7),
    SPECIAL_EXPENSES(8),
    OTHER_EXPENSES(9),
    SPECIAL_INCOME(10),
    OTHER_INCOME(11);

    private int value;
    private static final Map<Integer, Category> map = new HashMap<>();

    private Category(int value) {
        this.value = value;
    }

    static {
        for(var category : Category.values())
            map.put(category.value, category);
    }

    public static Category valueOf(int value) {
        return map.get(value);
    }
}
