package com.java.credit.card.management;

import com.java.utils.UserInputValidation;

import java.util.ArrayList;
import java.util.Random;


class Customer extends User {
    private final ArrayList<CreditCard> creditCards;
    private final int customerId;
    private int password;
    private final int appUserId;
    private final Bank bank;
    private final long accountNumber;

    Customer(int userId, String customerName, int password, int customerId, Bank bank) {
        this.appUserId = userId;
        this.name = customerName;
        this.password = password;
        this.customerId = customerId;
        this.creditCards = new ArrayList<>();
        this.bank = bank;
        this.accountNumber = this.generateAccountNumber();
    }

    private long generateAccountNumber() {
        Random random = new Random();
        return 100000000000L + (long) (random.nextDouble() * 900000000000L);
    }

    protected long getAccountNumber() {
        return this.accountNumber;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public ArrayList<CreditCard> getActiveCreditCards() {
        ArrayList<CreditCard> creditCardList = getCreditCards();
        ArrayList<CreditCard> activeCreditCards = new ArrayList<>();
        for (CreditCard card : creditCardList) {
            if (card.getStatus() == CreditCardStatus.ACTIVE) {
                activeCreditCards.add(card);
            }
        }
        return activeCreditCards;
    }

    public void addCreditCard(CreditCard creditCard) {
        boolean isSuccess = this.creditCards.add(creditCard);
        if (isSuccess) {
            System.out.println("Credit card with number:" + creditCard.getCardNumber() + " is successfully added!");
            return;
        }
        System.out.println("There was a problem in adding cards.. Please try again later!!");
    }

    public int getCustomerId() {
        return customerId;
    }

    protected String getCustomerName() {
        return this.name;
    }

    public boolean validatePassword(int inputPassword) {
        return this.password == inputPassword;
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

    public int getAppUserId() {
        return appUserId;
    }

    protected String getBankOfCustomer() {
        return this.bank.getBankName();
    }

    protected void listActiveCreditCards() {
        System.out.println();
        String[] headers = {"Card Type", "Card Number", "Limit", "Balance", "Credit Card Status"};
        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", headers[0], headers[1], headers[2], headers[3], headers[4]);
        for (CreditCard creditCard : this.getActiveCreditCards()) {
            System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", creditCard.getCardType(), creditCard.getCardNumber(), creditCard.getLimit(), creditCard.getBalance(), creditCard.getStatus());
        }
    }

    protected void getCustomerAccountDetails(int inputPassword) {
        if (this.validatePassword(inputPassword)) {
            System.out.println("Bank name:" + this.getBankOfCustomer());
            System.out.println("Customer name:" + this.getCustomerName());
            System.out.println("Customer Account number:" + this.getAccountNumber());
            System.out.println("Active Credit Cards:");
            this.listActiveCreditCards();
            return;
        }
        System.out.println("Operation failed, Incorrect Password!!");
    }

}
