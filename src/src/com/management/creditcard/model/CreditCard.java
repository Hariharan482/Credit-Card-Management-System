package com.management.creditcard.model;

import com.management.creditcard.enums.CreditCardStatus;
import com.management.utils.UserInputValidation;

public class CreditCard {
    private final long cardNumber;
    private final int cvv;
    private int pin;
    private long limit;
    private long balance;
    private CreditCardStatus status;
    private Bank bank;
    private String cardType;

    public CreditCard(long cardNumber, int cvv, int pin, CreditCardStatus status, CardType cardType, Bank bank) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.pin = pin;
        this.status = status;
        this.cardType = cardType.getCardTypeName();
        this.limit = cardType.getCardLimit();
        this.bank = bank;
        this.balance = this.limit;
    }

    public Bank getBank() {
        return bank;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public long getLimit() {
        return limit;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getCardType() {
        return cardType;
    }

    public CreditCardStatus getStatus() {
        return status;
    }

    public void setStatus(CreditCardStatus creditCardStatus){
        this.status=creditCardStatus;
    }

    public boolean verifyCVV(int inputCVV) {
        return this.cvv == inputCVV;
    }

    public boolean verifyPin(int inputPin) {
        return this.pin == inputPin;
    }

    public void changePin(int inputPin, int CVV) {
        if (!verifyPin(inputPin) || !verifyCVV(CVV)) {
            System.out.println("Incorrect PIN/CVV.. PIN can't be changed");
            return;
        }
        System.out.println("Enter new PIN");
        this.pin= UserInputValidation.getValidInteger();
        System.out.println("PIN Successfully changed!!");
    }
}
