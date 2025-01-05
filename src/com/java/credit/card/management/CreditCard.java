package com.java.credit.card.management;

enum CreditCardStatus{
    ACTIVE,
    BLOCKED,
    CLOSED
}

class CreditCard {
    private long cardNumber;
    private int cvv;
    private int pin;
    private int limit;
    private int balance;
    private CreditCardStatus status;
    private Bank bank;
    private String cardType;

    public CreditCard(long cardNumber,int cvv,int pin,CreditCardStatus status,String cardType,Bank bank){
        this.cardNumber=cardNumber;
        this.cvv=cvv;
        this.pin=pin;
        this.status=status;
        this.cardType=cardType;
        this.bank=bank;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getLimit() {
        return limit;
    }

    public int getBalance() {
        return balance;
    }

    public String getCardType() {
        return cardType;
    }

    public CreditCardStatus getStatus() {
        return status;
    }
}
