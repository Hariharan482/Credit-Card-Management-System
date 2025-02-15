package com.management.creditcard.implementation;

import com.management.creditcard.enums.CreditCardStatus;
import com.management.creditcard.model.Admin;
import com.management.creditcard.model.Bank;
import com.management.creditcard.model.CreditCard;
import com.management.creditcard.model.Customer;
import com.management.creditcard.service.BankService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public record BankServiceImpl(Bank bank) implements BankService {

    @Override
    public boolean isValidAdmin(int id, int password) {
        for (Admin admin : this.bank.getBankAdmins()) {
            if (admin.getAdminId() == id && admin.validatePassword(password))
                return true;
        }
        return false;
    }

    @Override
    public boolean isValidCustomer(int id, int password) {
        for (Customer customer : this.bank.getCustomers()) {
            if (customer.getCustomerId() == id && customer.validatePassword(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Admin getAdmin(int id, int password) {
        for (Admin admin : this.bank.getBankAdmins()) {
            if (admin.getAdminId() == id && admin.validatePassword(password))
                return admin;
        }
        return null;
    }

    public Customer isUserHavingAccount(int userID) {
        for (Customer customer : this.bank.getCustomers()) {
            if (customer.getAppUserId() == userID) {
                return customer;
            }
        }
        return null;
    }

    public void addCreditCard(int userID, CreditCard card) {
        for (Customer customer : this.bank.getCustomers()) {
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
        for (Customer customer : this.bank.getCustomers()) {
            for (CreditCard card : customer.getCreditCards()) {
                if (card.getStatus() == CreditCardStatus.BLOCKED || card.getStatus() == CreditCardStatus.CLOSED) {
                    blockedOrClosedCards.add(card);
                }
            }
        }
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            String fileName = System.getProperty("user.dir") + "/Files/Blocked_Closed_CreditCards_" + LocalDateTime.now().format(dateTimeFormatter) + ".txt";
            if (blockedOrClosedCards.isEmpty()) {
                System.out.println("No cards are blocked/Closed");
                return;
            }
            this.generateFile(blockedOrClosedCards, fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
