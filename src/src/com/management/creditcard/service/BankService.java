package com.management.creditcard.service;

import com.management.creditcard.model.Admin;
import com.management.creditcard.model.CreditCard;
import com.management.creditcard.model.Customer;

public interface BankService {
    boolean isValidAdmin(int id, int password);
    boolean isValidCustomer(int id, int password);
    Admin getAdmin(int id, int password);
    Customer isUserHavingAccount(int userID);
    void addCreditCard(int userID, CreditCard card);
    void retrieveBlockedOrClosedCards();
}
