package com.mediscreen.webapp.model;

public enum RiskLevel {
    NONE("None", 0),
    BORDERLINE("Borderline", 1),
    IN_DANGER("In danger", 2),
    EARLY_ONSET("Early onset", 3);

    private final String value;
    private final int numericValue;
    RiskLevel(String value, int numericValue) {
        this.value = value;
        this.numericValue = numericValue;
    }

    public String value() {
        return this.value;
    }

    public int numericValue() {
        return this.numericValue;
    }
}
