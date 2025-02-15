package com.management.creditcard.implementation;

import com.management.creditcard.enums.CreditCardStatus;
import com.management.creditcard.model.CreditCard;
import com.management.creditcard.model.Customer;
import com.management.creditcard.service.CustomerService;

import java.util.ArrayList;

public record CustomerServiceImpl(Customer customer) implements CustomerService {

    @Override
    public ArrayList<CreditCard> getActiveCreditCards() {
        ArrayList<CreditCard> creditCardList = this.customer.getCreditCards();
        ArrayList<CreditCard> activeCreditCards = new ArrayList<>();
        for (CreditCard card : creditCardList) {
            if (card.getStatus() == CreditCardStatus.ACTIVE) {
                activeCreditCards.add(card);
            }
        }
        return activeCreditCards;
    }

    @Override
    public void listActiveCreditCards() {
        System.out.println();
        String[] headers = {"Card Type", "Card Number", "Limit", "Balance", "Credit Card Status"};
        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", headers[0], headers[1], headers[2], headers[3], headers[4]);
        for (CreditCard creditCard : this.getActiveCreditCards()) {
            System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", creditCard.getCardType(), creditCard.getCardNumber(), creditCard.getLimit(), creditCard.getBalance(), creditCard.getStatus());
        }
    }

    @Override
    public void getCustomerAccountDetails(int inputPassword) {
        if (this.customer.validatePassword(inputPassword)) {
            System.out.println("Bank name:" + this.customer.getBankOfCustomer());
            System.out.println("Customer name:" + this.customer.getCustomerName());
            System.out.println("Customer Account number:" + this.customer.getAccountNumber());
            System.out.println("Active Credit Cards:");
            this.listActiveCreditCards();
            return;
        }
        System.out.println("Operation failed, Incorrect Password!!");
    }
}
