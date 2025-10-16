package com.wardrobe.style.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SeasonType {
    SPRING, SUMMER, AUTUMN, WINTER;

    @JsonCreator
    public static SeasonType fromJson(String value) {
        return value == null ? null : SeasonType.valueOf(value.trim().toUpperCase());
    }
}