package com.green.andrii.greenrest.models;

public enum Packing {
    KILOGRAM("кг"),
    THING("шт"),
    PACK("уп");

    private final String key;

    Packing(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static Packing getPackingByText(String text) {
        for (Packing packing : Packing.values()) {
            if (packing.getKey().equals(text)) {
                return packing;
            }
        }
        throw new IllegalArgumentException("Invalid packing value - \"" + text + "\"");
    }
}
