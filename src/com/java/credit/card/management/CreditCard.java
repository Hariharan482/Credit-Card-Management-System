package com.java.credit.card.management;

import java.util.Scanner;

enum CreditCardStatus{
    ACTIVE,
    BLOCKED,
    CLOSED
}

class CreditCard {
    private final long cardNumber;
    private final int cvv;
    private int pin;
    private long limit;
    private long balance;
    private CreditCardStatus status;
    private Bank bank;
    private String cardType;

    public CreditCard(long cardNumber,int cvv,int pin,CreditCardStatus status,CardType cardType,Bank bank){
        this.cardNumber=cardNumber;
        this.cvv=cvv;
        this.pin=pin;
        this.status=status;
        this.cardType=cardType.getCardTypeName();
        this.limit=cardType.getCardLimit();
        this.bank=bank;
        this.balance=this.limit;
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

    public String getCardType() {
        return cardType;
    }

    public CreditCardStatus getStatus() {
        return status;
    }

    private boolean verifyCVV(int inputCVV){
        return this.cvv==inputCVV;
    }

    private boolean verifyPin(int inputPin) {
        return this.pin == inputPin;
    }

    protected void changePin(int inputPin,int CVV){
        if (!verifyPin(inputPin) || !verifyCVV(CVV)) {
            System.out.println("Incorrect PIN/CVV.. PIN can't be changed");
            return;
        }
        System.out.println("Enter new PIN");
        Scanner scanner=new Scanner(System.in);
        this.pin= scanner.nextInt();
        System.out.println("PIN Successfully changed!!");
    }

    protected void spend(int inputPin, long amount) {
        if (status != CreditCardStatus.ACTIVE) {
            System.out.println("Card is "+status+"Cannot proceed with the transaction.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Amount to spend greater than zero!");
            return;
        }
        if (!verifyPin(inputPin)) {
            System.out.println("Incorrect PIN!!");
            return;
        }
        if (balance < amount) {
            System.out.println("Insufficient balance!");
            return;
        }
        balance -= amount;
        System.out.println("Successfully spent. Your remaining balance: " + balance);
    }

    protected void viewBalance(int inputPin) {
        if (verifyPin(inputPin)) {
            System.out.println("Current balance of your credit card number-"+this.getCardNumber()+"is :"+balance);
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    protected void deposit(int inputPin, long amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero!");
            return;
        }
        if (!verifyPin(inputPin)) {
            System.out.println("Incorrect PIN.");
            return;
        }
        if (status == CreditCardStatus.BLOCKED || status == CreditCardStatus.CLOSED) {
            System.out.println("Card is " + status + ". Cannot deposit.");
            return;
        }
        balance += amount;
        System.out.println("Successfully deposited. Your new balance: " + balance);
    }

    private void updateCardStatus(int userPreference) {
        CreditCardStatus newStatus=switch (userPreference){
            case 1-> CreditCardStatus.CLOSED;
            case 2-> CreditCardStatus.BLOCKED;
            default -> null;
        };
        if(newStatus==null){
            System.out.println("Invalid credit card status");
            return;
        }
        this.status = newStatus;
        System.out.println("Card status updated to: " + this.status);
    }

    protected void updateCardStatusByAdmin(int userPreference){
        this.updateCardStatus(userPreference);
    }

    protected void updateCardStatusByCustomer(int userPreference,int inputPin){
        if (!verifyPin(inputPin)) {
            System.out.println("Incorrect PIN.");
            return;
        }
        this.updateCardStatus(userPreference);
    }

}
