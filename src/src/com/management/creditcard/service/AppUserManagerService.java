package com.management.creditcard.service;

import com.management.creditcard.model.Bank;
import com.management.creditcard.model.Customer;

public interface AppUserManagerService {
    boolean isExistingUser(long identificationNumber);
    void addBankAdministrator(Bank bank);
    boolean addUserAccount(long identificationNumber);
    boolean isValidUser(int id, int password);
    int getUserId(long identificationNumber);
    void linkCustomerAccount(int userID, Customer customer);
    boolean getLinkedCreditCardsCount(int userID);
    void getLinkedBankAccounts(int appUserID);
    void blockOrCancelCreditCard(int appUserID, int userPreference);
    void viewBalanceOfCreditCard(int appUserID);
    void depositAmountToCreditCard(int appUserID);
    void changeCreditCardPIN(int appUserID);
    void spendMoney(int appUserID, int selectedSpendPreference);
    boolean buyProducts(int appUserID, long productPrice);
}
