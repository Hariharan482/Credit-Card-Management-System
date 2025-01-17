package com.java.credit.card.management;

import com.java.cart.management.Cart;
import com.java.cart.management.ShopMart;

import java.util.ArrayList;
import java.util.Scanner;

class AppUser extends User {
    private final int userId;
    private int userPassword;
    private static int currentAvailableUserId = 1;
    private final ArrayList<Customer> customerAccounts;
    private final Cart userCart;

    AppUser(long identificationNumber, int userPassword) {
        this.userId = getCurrentAvailableUserId();
        this.userPassword = userPassword;
        setIdentificationNumber(identificationNumber);
        this.customerAccounts = new ArrayList<>();
        this.userCart = new Cart();
    }

    protected int getCurrentAvailableUserId() {
        return currentAvailableUserId++;
    }

    protected int getUserId() {
        return userId;
    }

    protected int getUserPassword() {
        return userPassword;
    }

    protected void setUserPassword(int userPassword) {
        this.userPassword = userPassword;
    }

    protected void addCustomerAccount(Customer customer) {
        boolean isSuccess = customerAccounts.add(customer);
        if (isSuccess) {
            System.out.println("Customer account linked successfully!");
            return;
        }
        System.out.println("Sorry unable to add customer,Try again later!");
    }

    protected boolean getLinkedCreditCardsCount() {
        int cardsCount = 0;
        for (Customer customer : this.customerAccounts) {
            cardsCount += customer.getActiveCreditCards().size();
        }
        return (cardsCount < 5 && cardsCount >= 0);
    }

    private void manageAccounts(ArrayList<Customer> userCustomerAccounts) {
        if (userCustomerAccounts.isEmpty()) {
            System.out.println("Link a bank account to manage");
            return;
        }
        int i = 0;
        System.out.println("Select one of the following account to view more details");
        for (Customer customer : userCustomerAccounts) {
            System.out.println((++i) + "-> " + customer.getBankOfCustomer() + ":" + customer.getAccountNumber());
        }
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        if (selectedOption > 0 && selectedOption <= userCustomerAccounts.size()) {
            Customer customer = userCustomerAccounts.get(selectedOption - 1);
            System.out.println("Enter Customer Account password");
            int inputPassword = scanner.nextInt();
            customer.getCustomerAccountDetails(inputPassword);
            return;
        }
        System.out.println("Invalid selection!..Returning to admin menu!");
    }

    protected void getCustomerAccounts() {
        this.manageAccounts(this.customerAccounts);
    }

    private ArrayList<CreditCard> getActiveCreditCardsOfLinkedAccounts() {
        ArrayList<CreditCard> userActiveCreditCards = new ArrayList<>();
        int numberOfCards = 0;
        for (Customer customer : this.customerAccounts) {
            for (CreditCard creditCard : customer.getActiveCreditCards()) {
                System.out.println((++numberOfCards) + "-> " + creditCard.getCardType() + ":" + creditCard.getCardNumber());
                userActiveCreditCards.add(creditCard);
            }
        }
        return userActiveCreditCards;
    }

    protected void blockOrCancelCreditCard(int userPreference) {
        if (this.customerAccounts.isEmpty()) {
            System.out.println("Link a bank account to manage");
            return;
        }
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to be blocked/closed!");
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCard selectedCreditCard = userActiveCreditCards.get(selectedOption - 1);
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = scanner.nextInt();
            selectedCreditCard.updateCardStatusByCustomer(userPreference, inputPin);
            return;
        }
        System.out.println("Invalid selection!..Returning to admin menu!");
    }

    protected void viewBalanceOfCreditCard() {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to view balance");
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCard selectedCreditCard = userActiveCreditCards.get(selectedOption - 1);
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = scanner.nextInt();
            selectedCreditCard.viewBalance(inputPin);
            return;
        }
        System.out.println("Invalid selection!..Returning to customer menu!");
    }

    protected void depositAmountToCreditCard() {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to deposit ");
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCard selectedCreditCard = userActiveCreditCards.get(selectedOption - 1);
            System.out.println("Enter the amount to be deposited");
            long depositAmount = scanner.nextLong();
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = scanner.nextInt();
            selectedCreditCard.deposit(inputPin, depositAmount);
            return;
        }
        System.out.println("Invalid selection!..Returning to customer menu!");
    }

    protected void changeCreditCardPIN() {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to change PIN ");
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCard selectedCreditCard = userActiveCreditCards.get(selectedOption - 1);
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = scanner.nextInt();
            System.out.println("Enter CVV of your credit card");
            int inputCVV = scanner.nextInt();
            selectedCreditCard.changePin(inputPin, inputCVV);
            return;
        }
        System.out.println("Invalid selection!..Returning to customer menu!");

    }

    private boolean payToVendor() {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to spend ");
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCard selectedCreditCard = userActiveCreditCards.get(selectedOption - 1);
            System.out.println("Enter the amount to be spend");
            long depositAmount = scanner.nextLong();
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = scanner.nextInt();
            return selectedCreditCard.spend(inputPin, depositAmount);
        }
        System.out.println("Invalid selection!..Returning to customer menu!");
        return false;
    }

    protected void spendMoney(int userPreference, int appUserID) {
        switch (userPreference) {
            case 1 -> {
                if (this.payToVendor()) {
                    System.out.println("Transaction Successful..!");
                    return;
                }
                System.out.println("Transaction failed..!");
            }
            case 2 -> {
                this.listAvailableProducts(appUserID);
            }
            default -> {
                System.out.println("Invalid Selection");
            }
        }
        ;
    }

    private void listAvailableProducts(int appUserID) {
        new ShopMart().getMenu(this.getCart(), appUserID);
    }

    private Cart getCart() {
        return this.userCart;
    }

    protected boolean buyProducts(long cartAmount) {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to spend");
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCard selectedCreditCard = userActiveCreditCards.get(selectedOption - 1);
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = scanner.nextInt();
            return selectedCreditCard.spend(inputPin, cartAmount);
        }
        System.out.println("Invalid selection!..Returning to ShopMart menu!");
        return false;
    }
}
