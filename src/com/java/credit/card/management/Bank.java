package com.java.credit.card.management;

import com.java.utils.UserInputValidation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;


class Bank {
    public String bankName;
    private final ArrayList<Customer> customers;
    private final ArrayList<Admin> bankAdmins;
    private int currentAvailableCustomerId = 1;
    private int currentAvailableAdminId = 1;
    private int rootPassword = 999;
    private final ArrayList<CardType> cardTypes;
    private static long currentCreditCardNumber;

    Bank() {
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

    public boolean setCustomer(Customer customer) {
        return this.customers.add(customer);
    }

    public ArrayList<Admin> getBankAdmins() {
        return bankAdmins;
    }

    public boolean addBankAdmin(Admin bankAdmin) {
        return this.bankAdmins.add(bankAdmin);
    }

    public boolean isValidAdmin(int id, int password) {
        for (Admin admin : this.bankAdmins) {
            if (admin.getAdminId() == id && admin.validatePassword(password))
                return true;
        }
        return false;
    }

    public boolean isValidCustomer(int id, int password) {
        for (Customer customer : this.customers) {
            if (customer.getCustomerId() == id && customer.validatePassword(password)) {
                return true;
            }
        }
        return false;
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

    public Admin getAdmin(int id, int password) {
        for (Admin admin : this.bankAdmins) {
            if (admin.getAdminId() == id && admin.validatePassword(password))
                return admin;
        }
        return null;
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

    protected Customer isUserHavingAccount(int userID) {
        for (Customer customer : this.customers) {
            if (customer.getAppUserId() == userID) {
                return customer;
            }
        }
        return null;
    }

    protected void addCreditCard(int userID, CreditCard card) {
        for (Customer customer : this.customers) {
            if (customer.getAppUserId() == userID) {
                customer.addCreditCard(card);
                return;
            }
        }
        System.out.println("No user accounts found to link!");
    }

    private void generateFile(ArrayList<CreditCard> cards, String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));) {
            for (CreditCard card : cards) {
                bufferedWriter.write("Card type:" + card.getCardType());
                bufferedWriter.write(",Card number:" + card.getCardNumber());
                bufferedWriter.write(",Card status:" + card.getStatus());
                bufferedWriter.newLine();
            }
            System.out.println("File generated successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void retrieveBlockedOrClosedCards() {
        ArrayList<CreditCard> blockedOrClosedCards = new ArrayList<>();
        for (Customer customer : this.customers) {
            for (CreditCard card : customer.getCreditCards()) {
                if (card.getStatus() == CreditCardStatus.BLOCKED || card.getStatus() == CreditCardStatus.CLOSED) {
                    blockedOrClosedCards.add(card);
                }
            }
        }
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            String fileName = System.getProperty("user.dir") + "/Files/Blocked_Closed_CreditCards_" + LocalDateTime.now().format(dateTimeFormatter) + ".txt";
            if(blockedOrClosedCards.isEmpty()){
                System.out.println("No cards are blocked/Closed");
                return;
            }
            this.generateFile(blockedOrClosedCards, fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
