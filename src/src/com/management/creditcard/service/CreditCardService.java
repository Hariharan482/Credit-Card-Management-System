package com.management.creditcard.service;

public interface CreditCardService {
    void updateCardStatusByCustomer(int userPreference, int inputPin);
    void updateCardStatusByAdmin(int userPreference);
    void deposit(int inputPin, long amount);
    boolean spend(int inputPin, long amount, int CVV);
    void viewBalance(int inputPin);
}
