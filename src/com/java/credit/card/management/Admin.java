package com.java.credit.card.management;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Admin extends User {
    private final int adminId;
    private int password;
    private final Bank bank;
    private final AppUserManager appUserManager = new AppUserManager();

    Admin(Bank bank, String name, int password) {
        this.bank = bank;
        this.name = name;
        this.adminId = this.bank.getCurrentAvailableAdminId();
        this.password = password;
    }

    public int generateUserAccountID(long identificationNumber) {
        return this.appUserManager.getUserId(identificationNumber);
    }


    public void addAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter admin name");
        String adminName = scanner.nextLine();
        System.out.println("Enter admin password");
        int loginPassword = scanner.nextInt();
        Admin admin = new Admin(this.bank, adminName, loginPassword);
        if (this.bank.addBankAdmin(admin)) {
            System.out.println("Admin successfully added");
            System.out.println("Admin ID for Admin name " + admin.name + " is" + admin.getAdminId());
            return;
        }
        System.out.println("Sorry!! There was a problem in adding Admin account");
    }

    public void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter identification number");
        long identificationNumber = scanner.nextLong();
        int userId = this.generateUserAccountID(identificationNumber);
        if (userId == 0) {
            return;
        }
        scanner.nextLine();
        System.out.println("Enter customer name");
        String customerName = scanner.nextLine();
        System.out.println("Enter customer password");
        int loginPassword = scanner.nextInt();
        int customerID = this.bank.getCurrentAvailableCustomerId();
        Customer customer = new Customer(userId, customerName, loginPassword, customerID, this.bank);
        if (bank.setCustomer(customer)) {
            System.out.println("Customer successfully added");
            System.out.println("Your Customer ID:" + customerID);
            int userID = this.appUserManager.getUserId(identificationNumber);
            if (userID == 0)
                return;
            this.appUserManager.linkCustomerAccount(userID, customer);
            return;
        }
        System.out.println("Sorry!! There was a problem in adding Customer account");
    }

    public void viewAllCustomers() {
        System.out.println("Customers of " + this.bank.getBankName());
        String[] headers = {"Customer ID", "Customer Name"};
        System.out.printf("%-15s %-15s%n", headers[0], headers[1]);
        for (Customer customer : this.bank.getCustomers()) {
            System.out.printf("%-15d %-15s%n", customer.getCustomerId(), customer.name);
        }
    }

    public void viewAllIssuedCreditCards() {
        System.out.println("Issued Credit Cards of " + this.bank.getBankName());
        for (Customer customer : this.bank.getCustomers()) {
            String[] headers = {"Customer ID", "Customer Name", "Card Type", "Card Number", "Limit", "Balance", "Credit Card Status"};
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", headers[0], headers[1], headers[2], headers[3], headers[4], headers[5], headers[6]);
            for (CreditCard creditCard : customer.getCreditCards()) {
                System.out.printf("%-15d %-15s %-15s %-15s %-15s %-15s %-15s%n", customer.getCustomerId(), customer.name, creditCard.getCardType(), creditCard.getCardNumber(), creditCard.getLimit(), creditCard.getBalance(), creditCard.getStatus());
            }
        }
    }

    public boolean validatePassword(int inputPassword) {
        return this.password == inputPassword;
    }

    public void setPassword(int password) {
        System.out.println("Enter the current admin password to proceed");
        Scanner scanner = new Scanner(System.in);
        int userEnteredPassword = scanner.nextInt();
        if (this.validatePassword(userEnteredPassword)) {
            this.password = password;
            System.out.println("Password Successfully changed!!");
            return;
        }
        System.out.println("Unauthorised operation");
    }

    public int getAdminId() {
        return adminId;
    }

    protected void issueNewCreditCard(long identificationNumber) {
        int userId = this.appUserManager.getUserId(identificationNumber);
        Customer customer = this.bank.isUserHavingAccount(userId);
        if (userId == 0 || customer == null) {
            System.out.println("User doesn't have an account in " + this.bank.getBankName() + "! Please create before issuing an credit card");
            return;
        }
        if (this.appUserManager.getLinkedCreditCardsCount(userId)) {
            Random random = new Random();
            int cvv = 100 + random.nextInt(900);
            int secretPin = 1000 + random.nextInt(9000);
            long creditCardNumber = Bank.getAvailableCreditCardNumber();
            System.out.println("Select the card type:");
            int i = 1;
            ArrayList<CardType> cardTypes = this.bank.getCardTypes();
            for (CardType cardType : cardTypes) {
                System.out.println((i++) + "." + cardType.getCardTypeName());
            }
            Scanner scanner = new Scanner(System.in);
            int selectedOption = scanner.nextInt();
            CardType cardType = cardTypes.get(selectedOption - 1);
            System.out.println("CARD TYPE : " + cardType.getCardTypeName());
            System.out.println("CARD NUMBER : " + creditCardNumber);
            System.out.println("CARD CVV : " + cvv);
            System.out.println("CARD SECRET PIN : " + secretPin);
            System.out.println("Press 1 to issue this card to customer");
            int keyPressed = scanner.nextInt();
            if (keyPressed == 1) {
                CreditCard creditCard = new CreditCard(creditCardNumber, cvv, secretPin, CreditCardStatus.ACTIVE, cardType, this.bank);
                customer.addCreditCard(creditCard);
                return;
            }
            System.out.println("Operation cancelled!");
        } else {
            System.out.println("User has the maximum number of active credit cards!! We can't add anymore credit cards!");
        }
    }

    protected void blockOrCancelCreditCard(int userPreference, int customerID) {
        Customer customer = this.bank.isUserHavingAccount(customerID);
        if (customer != null) {
            ArrayList<CreditCard> customerCards = customer.getActiveCreditCards();
            int numberOfCards = 0;
            for (CreditCard creditCard : customerCards) {
                System.out.println((++numberOfCards) + "->" + creditCard.getCardType() + ":" + creditCard.getCardNumber());
            }
            System.out.println("Select the card to be blocked/closed!");
            Scanner scanner = new Scanner(System.in);
            int selectedOption = scanner.nextInt();
            if (selectedOption > 0 && selectedOption <= numberOfCards) {
                CreditCard selectedCreditCard = customerCards.get(selectedOption - 1);
                selectedCreditCard.updateCardStatusByAdmin(userPreference);
            } else {
                System.out.println("Invalid selection!..Returning to admin menu!");
            }
            return;
        }
        System.out.println("Customer ID doesn't exists!!");
    }

    protected void viewBlockedOrClosedCreditCards() {
        this.bank.retrieveBlockedOrClosedCards();
    }
}
