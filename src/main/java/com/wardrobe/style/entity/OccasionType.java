package com.wardrobe.style.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OccasionType {
    CASUAL, FORMAL, PARTY, WORK, SPORTS, TRAVEL;

    @JsonCreator
    public static OccasionType fromJson(String value) {
        if (value == null) {
            return null;
        }
        return OccasionType.valueOf(value.trim().toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return this.name().toLowerCase();
    }
}