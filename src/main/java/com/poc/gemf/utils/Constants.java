package com.poc.gemf.utils;

public enum Constants {
    SUCCESS("success"),
    FAILURE("failed");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
