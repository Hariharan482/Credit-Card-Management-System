package com.management.creditcard.implementation;

import com.management.creditcard.enums.CreditCardStatus;
import com.management.creditcard.model.CreditCard;
import com.management.creditcard.service.CreditCardService;

public record CreditCardServiceImpl(CreditCard creditCard) implements CreditCardService {

    private void updateCardStatus(int userPreference) {
        CreditCardStatus newStatus = switch (userPreference) {
            case 1 -> CreditCardStatus.CLOSED;
            case 2 -> CreditCardStatus.BLOCKED;
            default -> null;
        };
        if (newStatus == null) {
            System.out.println("Invalid credit card status");
            return;
        }
        this.creditCard.setStatus(newStatus);
        System.out.println("Card status updated to: " + this.creditCard.getStatus());
    }

    @Override
    public void updateCardStatusByCustomer(int userPreference, int inputPin) {
        if (!this.creditCard.verifyPin(inputPin)) {
            System.out.println("Incorrect PIN.");
            return;
        }
        this.updateCardStatus(userPreference);
    }

    @Override
    public void updateCardStatusByAdmin(int userPreference) {
        this.updateCardStatus(userPreference);
    }

    @Override
    public void deposit(int inputPin, long amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero!");
            return;
        }
        if (!this.creditCard.verifyPin(inputPin)) {
            System.out.println("Incorrect PIN..!!");
            return;
        }
        CreditCardStatus status = this.creditCard.getStatus();
        if (status == CreditCardStatus.BLOCKED || status == CreditCardStatus.CLOSED) {
            System.out.println("Card is " + status + ". Cannot deposit.");
            return;
        }
        long amountDue = this.creditCard.getLimit() - this.creditCard.getBalance();
        if (amountDue < amount) {
            System.out.println("The Amount " + amount + " is greater than the amount due " + amountDue + "..!Please double-check the payment amount.");
            return;
        }
        this.creditCard.setBalance(this.creditCard.getBalance() + amount);
        System.out.println("Successfully deposited. Your new balance: " + this.creditCard.getBalance());

    }

    @Override
    public boolean spend(int inputPin, long amount, int CVV) {
        if (this.creditCard.getStatus() != CreditCardStatus.ACTIVE) {
            System.out.println("Card is " + this.creditCard.getStatus() + "..! Cannot proceed with the transaction.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Amount to be spend should be greater than zero!");
            return false;
        }
        if (!this.creditCard.verifyPin(inputPin) || !this.creditCard.verifyCVV(CVV)) {
            System.out.println("Incorrect PIN/CVV..!!");
            return false;
        }
        if (this.creditCard.getBalance() < amount) {
            System.out.println("Insufficient balance!");
            return false;
        }
        this.creditCard.setBalance(this.creditCard.getBalance() - amount);
        System.out.println("Successfully spent. Your remaining balance: " + this.creditCard.getBalance());
        return true;
    }

    @Override
    public void viewBalance(int inputPin) {
        if (this.creditCard.verifyPin(inputPin)) {
            System.out.println("Current balance of your credit card number-" + this.creditCard.getCardNumber() + "is :" + this.creditCard.getBalance());
        } else {
            System.out.println("Incorrect PIN..!!");
        }
    }
}
