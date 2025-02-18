package com.management.creditcard.model;

import com.management.utils.UserInputValidation;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String bankName;
    private final ArrayList<Customer> customers;
    private final ArrayList<Admin> bankAdmins;
    private int currentAvailableCustomerId = 1;
    private int currentAvailableAdminId = 1;
    private int rootPassword = 999;
    private final ArrayList<CardType> cardTypes;
    private static long currentCreditCardNumber;

    protected Bank() {
        this.bankAdmins = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.cardTypes = new ArrayList<>();
    }

    public void setBankName(String name) {
        this.bankName = name;
    }

    public String getBankName() {
        return this.bankName;
    }

    public int getCurrentAvailableCustomerId() {
        return (this.currentAvailableCustomerId)++;
    }

    public int getCurrentAvailableAdminId() {
        return (this.currentAvailableAdminId)++;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Admin> getBankAdmins() {
        return bankAdmins;
    }

    public boolean setCustomer(Customer customer) {
        return this.customers.add(customer);
    }

    public boolean addBankAdmin(Admin bankAdmin) {
        return this.bankAdmins.add(bankAdmin);
    }

    public ArrayList<CardType> getCardTypes() {
        return cardTypes;
    }

    public void addCardType(CardType cardType) {
        this.cardTypes.add(cardType);
    }

    public static long getAvailableCreditCardNumber() {
        Random random = new Random();
        currentCreditCardNumber = 100000000000L + (long) (random.nextDouble() * 900000000000L);
        return currentCreditCardNumber;
    }

    public boolean checkRootUser() {
        System.out.println("Enter the current bank root user password to proceed");
        int userEnteredPassword= UserInputValidation.getValidInteger();
        return userEnteredPassword == rootPassword;
    }

    public void setRootPassword(int rootPassword) {
        if (this.checkRootUser()) {
            this.rootPassword = rootPassword;
            System.out.println("Password Successfully changed!!");
        } else {
            System.out.println("Unauthorised operation");
        }
    }

}
