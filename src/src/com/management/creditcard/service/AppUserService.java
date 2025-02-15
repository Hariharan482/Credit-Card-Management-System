package com.management.creditcard.service;

public interface AppUserService {
    boolean getLinkedCreditCardsCount();
    void getCustomerAccounts();
    void blockOrCancelCreditCard(int userPreference);
    void viewBalanceOfCreditCard();
    void depositAmountToCreditCard();
    void changeCreditCardPIN();
    void spendMoney(int userPreference, int appUserID);
    boolean buyProducts(long cartAmount);
}
