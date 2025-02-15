package com.management.creditcard.implementation;

import com.management.creditcard.enums.CreditCardStatus;
import com.management.creditcard.model.*;
import com.management.creditcard.service.AdminService;
import com.management.creditcard.service.AppUserManagerService;
import com.management.utils.UserInputValidation;

import java.util.ArrayList;
import java.util.Random;

public record AdminServiceImpl(Admin admin) implements AdminService {

    @Override
    public void addAdmin() {
        System.out.println("Enter admin name");
        String adminName = UserInputValidation.getValidString();
        System.out.println("Enter admin password");
        int loginPassword = UserInputValidation.getValidInteger();
        Admin admin = new Admin(this.admin.getBank(), adminName, loginPassword);
        if (this.admin.getBank().addBankAdmin(admin)) {
            System.out.println("Admin successfully added");
            System.out.println("Admin ID for Admin name " + admin.name + " is" + admin.getAdminId());
            return;
        }
        System.out.println("Sorry!! There was a problem in adding Admin account");

    }

    @Override
    public void addCustomer() {
        System.out.println("Enter identification number");
        long identificationNumber = UserInputValidation.getValidLong();
        int userId = this.admin.generateUserAccountID(identificationNumber);
        if (userId == 0) {
            return;
        }
        System.out.println("Enter customer name");
        String customerName = UserInputValidation.getValidString();
        System.out.println("Enter customer password");
        int loginPassword = UserInputValidation.getValidInteger();
        int customerID = this.admin.getBank().getCurrentAvailableCustomerId();
        Customer customer = new Customer(userId, customerName, loginPassword, customerID, this.admin.getBank());
        if (this.admin.getBank().setCustomer(customer)) {
            System.out.println("Customer successfully added");
            System.out.println("Your Customer ID:" + customerID);
            AppUserManagerService appUserManagerService=new AppUserManagerServiceImpl(this.admin.getAppUserManager());
            int userID = appUserManagerService.getUserId(identificationNumber);
            if (userID == 0)
                return;
            appUserManagerService.linkCustomerAccount(userID, customer);
            return;
        }
        System.out.println("Sorry!! There was a problem in adding Customer account");

    }

    @Override
    public void viewAllCustomers() {
        if (!this.admin.getBank().getCustomers().isEmpty()) {
            System.out.println("Customers of " + this.admin.getBank().getBankName());
            String[] headers = {"Customer ID", "Customer Name"};
            System.out.printf("%-15s %-15s%n", headers[0], headers[1]);
            for (Customer customer : this.admin.getBank().getCustomers()) {
                System.out.printf("%-15d %-15s%n", customer.getCustomerId(), customer.name);
            }
            return;
        }
        System.out.println("Add a customer to view data");

    }

    @Override
    public void viewAllIssuedCreditCards() {
        System.out.println("Issued Credit Cards of " + this.admin.getBank().getBankName());
        for (Customer customer : this.admin.getBank().getCustomers()) {
            if (!customer.getCreditCards().isEmpty()) {
                String[] headers = {"Customer ID", "Customer Name", "Card Type", "Card Number", "Limit", "Balance", "Credit Card Status"};
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", headers[0], headers[1], headers[2], headers[3], headers[4], headers[5], headers[6]);
                for (CreditCard creditCard : customer.getCreditCards()) {
                    System.out.printf("%-15d %-15s %-15s %-15s %-15s %-15s %-15s%n", customer.getCustomerId(), customer.name, creditCard.getCardType(), creditCard.getCardNumber(), creditCard.getLimit(), creditCard.getBalance(), creditCard.getStatus());
                }
            } else {
                System.out.println("Customer yet to have a credit card!");
            }
        }
    }

    @Override
    public void issueNewCreditCard(long identificationNumber) {
        AppUserManagerService appUserManagerService=new AppUserManagerServiceImpl(this.admin.getAppUserManager());
        BankServiceImpl bankService=new BankServiceImpl(admin().getBank());
        int userId = appUserManagerService.getUserId(identificationNumber);
        Customer customer = bankService.isUserHavingAccount(userId);
        if (userId == 0 || customer == null) {
            System.out.println("User doesn't have an account in " + this.admin.getBank().getBankName() + "! Please create before issuing an credit card");
            return;
        }
        if (appUserManagerService.getLinkedCreditCardsCount(userId)) {
            Random random = new Random();
            int cvv = 100 + random.nextInt(900);
            int secretPin = 1000 + random.nextInt(9000);
            long creditCardNumber = Bank.getAvailableCreditCardNumber();
            System.out.println("Select the card type:");
            int i = 1;
            ArrayList<CardType> cardTypes = this.admin.getBank().getCardTypes();
            for (CardType cardType : cardTypes) {
                System.out.println((i++) + "." + cardType.getCardTypeName());
            }
            int selectedOption = UserInputValidation.getValidInteger();
            CardType cardType = cardTypes.get(selectedOption - 1);
            System.out.println("CARD TYPE : " + cardType.getCardTypeName());
            System.out.println("CARD NUMBER : " + creditCardNumber);
            System.out.println("CARD CVV : " + cvv);
            System.out.println("CARD SECRET PIN : " + secretPin);
            System.out.println("Press 1 to issue this card to customer");
            int keyPressed = UserInputValidation.getValidInteger();
            if (keyPressed == 1) {
                CreditCard creditCard = new CreditCard(creditCardNumber, cvv, secretPin, CreditCardStatus.ACTIVE, cardType, this.admin.getBank());
                customer.addCreditCard(creditCard);
                return;
            }
            System.out.println("Operation cancelled!");
        } else {
            System.out.println("User has the maximum number of active credit cards!! We can't add anymore credit cards!");
        }
    }

    @Override
    public void blockOrCancelCreditCard(int userPreference, int customerID) {
        BankServiceImpl bankService=new BankServiceImpl(admin().getBank());
        Customer customer = bankService.isUserHavingAccount(customerID);
        if (customer != null) {
            CustomerServiceImpl customerService=new CustomerServiceImpl(customer);
            ArrayList<CreditCard> customerCards = customerService.getActiveCreditCards();
            if (customerCards.isEmpty()) {
                System.out.println("Customer doesn't have Credit Cards to block");
                return;
            }
            int numberOfCards = 0;
            for (CreditCard creditCard : customerCards) {
                System.out.println((++numberOfCards) + "->" + creditCard.getCardType() + ":" + creditCard.getCardNumber());
            }
            System.out.println("Select the card to be blocked/closed!");
            int selectedOption = UserInputValidation.getValidInteger();
            if (selectedOption > 0 && selectedOption <= numberOfCards) {
                CreditCard selectedCreditCard = customerCards.get(selectedOption - 1);
                CreditCardServiceImpl creditCardService=new CreditCardServiceImpl(selectedCreditCard);
                creditCardService.updateCardStatusByAdmin(userPreference);
            } else {
                System.out.println("Invalid selection!..Returning to admin menu!");
            }
            return;
        }
        System.out.println("Customer ID doesn't exists!!");

    }

    @Override
    public void viewBlockedOrClosedCreditCards() {
        BankServiceImpl bankService=new BankServiceImpl(admin().getBank());
        bankService.retrieveBlockedOrClosedCards();
    }
}
