package com.management.creditcard.model;

public class User {
    public String name;
    private long identificationNumber;

    protected void setIdentificationNumber(long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public long getIdentificationNumber() {
        return this.identificationNumber;
    }
}
