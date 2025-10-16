package com.wardrobe.style.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ItemType {
    TOPS, BOTTOMS, DRESSES, OUTWEAR, SHOES, ACCESSORIES;

    @JsonCreator
    public static ItemType fromString(String value) {
        return ItemType.valueOf(value.toUpperCase());
    }
}

