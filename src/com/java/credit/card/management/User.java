package com.java.credit.card.management;

class User {
    public String name;
    private long identificationNumber;

    protected void setIdentificationNumber(long identificationNumber){
        this.identificationNumber =identificationNumber;
    }

    protected long getIdentificationNumber(){
        return this.identificationNumber;
    }
}
