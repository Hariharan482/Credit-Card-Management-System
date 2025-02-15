package com.management.creditcard.service;

public interface AdminService {
    void addAdmin();
    void addCustomer();
    void viewAllCustomers();
    void viewAllIssuedCreditCards();
    void issueNewCreditCard(long identificationNumber);
    void blockOrCancelCreditCard(int userPreference, int customerID);
    void viewBlockedOrClosedCreditCards();
}
