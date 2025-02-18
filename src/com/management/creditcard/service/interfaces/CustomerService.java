package com.management.creditcard.service.interfaces;

import com.management.creditcard.model.CreditCard;

import java.util.ArrayList;

public interface CustomerService {
    ArrayList<CreditCard> getActiveCreditCards();
    void listActiveCreditCards();
    void getCustomerAccountDetails(int inputPassword);
}
