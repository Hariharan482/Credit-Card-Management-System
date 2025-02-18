package com.management.creditcard.model;

import com.management.utils.UserInputValidation;

import java.util.ArrayList;
import java.util.Random;

public class Customer extends User{
    private final ArrayList<CreditCard> creditCards;
    private final int customerId;
    private int password;
    private final int appUserId;
    private final Bank bank;
    private final long accountNumber;

    public Customer(int userId, String customerName, int password, int customerId, Bank bank) {
        this.appUserId = userId;
        this.name = customerName;
        this.password = password;
        this.customerId = customerId;
        this.creditCards = new ArrayList<>();
        this.bank = bank;
        this.accountNumber = this.generateAccountNumber();
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return this.name;
    }

    public boolean validatePassword(int inputPassword) {
        return this.password == inputPassword;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public String getBankOfCustomer() {
        return this.bank.getBankName();
    }

    private long generateAccountNumber() {
        Random random = new Random();
        return 100000000000L + (long) (random.nextDouble() * 900000000000L);
    }

    public long getAccountNumber() {
        return this.accountNumber;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void addCreditCard(CreditCard creditCard) {
        boolean isSuccess = this.creditCards.add(creditCard);
        if (isSuccess) {
            System.out.println("Credit card with number:" + creditCard.getCardNumber() + " is successfully added!");
            return;
        }
        System.out.println("There was a problem in adding cards.. Please try again later!!");
    }

    public void setPassword(int password) {
        System.out.println("Enter the current customer password to proceed");
        int userEnteredPassword= UserInputValidation.getValidInteger();
        if (this.validatePassword(userEnteredPassword)) {
            this.password = password;
            System.out.println("Password Successfully changed!!");
            return;
        }
        System.out.println("Unauthorised operation");
    }
}
