package com.management.creditcard.service.implementation;

import com.management.cart.ShopMart;
import com.management.cart.model.Cart;
import com.management.creditcard.model.AppUser;
import com.management.creditcard.model.CreditCard;
import com.management.creditcard.model.Customer;
import com.management.creditcard.service.interfaces.AppUserService;
import com.management.utils.UserInputValidation;

import java.util.ArrayList;

public record AppUserServiceImpl(AppUser appUser) implements AppUserService {

    @Override
    public boolean getLinkedCreditCardsCount() {
        int cardsCount = 0;
        for (Customer customer : this.appUser.getAppUserCustomerAccounts()) {
            CustomerServiceImpl customerService=new CustomerServiceImpl(customer);
            cardsCount += customerService.getActiveCreditCards().size();
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
        int selectedOption = UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= userCustomerAccounts.size()) {
            System.out.println("Enter Customer Account password");
            int inputPassword = UserInputValidation.getValidInteger();
            CustomerServiceImpl customerService=new CustomerServiceImpl(userCustomerAccounts.get(selectedOption - 1));
            customerService.getCustomerAccountDetails(inputPassword);
            return;
        }
        System.out.println("Invalid selection!..Returning to admin menu!");
    }

    @Override
    public void getCustomerAccounts() {
        this.manageAccounts(this.appUser.getAppUserCustomerAccounts());
    }

    private ArrayList<CreditCard> getActiveCreditCardsOfLinkedAccounts() {
        ArrayList<CreditCard> userActiveCreditCards = new ArrayList<>();
        int numberOfCards = 0;
        for (Customer customer : this.appUser.getAppUserCustomerAccounts()) {
            CustomerServiceImpl customerService=new CustomerServiceImpl(customer);
            for (CreditCard creditCard : customerService.getActiveCreditCards()) {
                System.out.println((++numberOfCards) + "-> " + creditCard.getCardType() + ":" + creditCard.getCardNumber());
                userActiveCreditCards.add(creditCard);
            }
        }
        return userActiveCreditCards;
    }

    @Override
    public void blockOrCancelCreditCard(int userPreference) {
        if (this.appUser.getAppUserCustomerAccounts().isEmpty()) {
            System.out.println("Link a bank account to manage");
            return;
        }
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to be blocked/closed!");
        int selectedOption = UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCardServiceImpl creditCardService=new CreditCardServiceImpl(userActiveCreditCards.get(selectedOption - 1));
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = UserInputValidation.getValidInteger();
            creditCardService.updateCardStatusByCustomer(userPreference, inputPin);
            return;
        }
        System.out.println("Invalid selection!..Returning to admin menu!");

    }

    @Override
    public void viewBalanceOfCreditCard() {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to view balance");
        int selectedOption = UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCardServiceImpl creditCardService=new CreditCardServiceImpl(userActiveCreditCards.get(selectedOption - 1));
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = UserInputValidation.getValidInteger();
            creditCardService.viewBalance(inputPin);
            return;
        }
        System.out.println("Invalid selection!..Returning to customer menu!");

    }

    @Override
    public void depositAmountToCreditCard() {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to deposit ");
        int selectedOption = UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCardServiceImpl creditCardService=new CreditCardServiceImpl(userActiveCreditCards.get(selectedOption - 1));
            System.out.println("Enter the amount to be deposited");
            long depositAmount = UserInputValidation.getValidLong();
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = UserInputValidation.getValidInteger();
            creditCardService.deposit(inputPin, depositAmount);
            return;
        }
        System.out.println("Invalid selection!..Returning to customer menu!");

    }

    @Override
    public void changeCreditCardPIN() {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to change PIN ");
        int selectedOption = UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCard selectedCreditCard = userActiveCreditCards.get(selectedOption - 1);
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = UserInputValidation.getValidInteger();
            System.out.println("Enter CVV of your credit card");
            int inputCVV = UserInputValidation.getValidInteger();
            selectedCreditCard.changePin(inputPin, inputCVV);
            return;
        }
        System.out.println("Invalid selection!..Returning to customer menu!");
    }

    private boolean payToVendor() {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to spend ");
        int selectedOption = UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCardServiceImpl creditCardService=new CreditCardServiceImpl(userActiveCreditCards.get(selectedOption - 1));
            System.out.println("Enter the amount to be spend");
            long depositAmount = UserInputValidation.getValidLong();
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = UserInputValidation.getValidInteger();
            System.out.println("Enter CVV of your credit card");
            int inputCVV = UserInputValidation.getValidInteger();
            return creditCardService.spend(inputPin, depositAmount, inputCVV);
        }
        System.out.println("Invalid selection!..Returning to customer menu!");
        return false;
    }

    @Override
    public void spendMoney(int userPreference, int appUserID) {
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
        };
    }

    private void listAvailableProducts(int appUserID) {
        new ShopMart().getMenu(this.getCart(), appUserID);
    }

    private Cart getCart() {
        return this.appUser.getUserCart();
    }

    @Override
    public boolean buyProducts(long cartAmount) {
        ArrayList<CreditCard> userActiveCreditCards = this.getActiveCreditCardsOfLinkedAccounts();
        System.out.println("Select the card to spend");
        int selectedOption = UserInputValidation.getValidInteger();
        if (selectedOption > 0 && selectedOption <= userActiveCreditCards.size()) {
            CreditCardServiceImpl creditCardService=new CreditCardServiceImpl(userActiveCreditCards.get(selectedOption - 1));
            System.out.println("Enter Credit Card pin to proceed your operation");
            int inputPin = UserInputValidation.getValidInteger();
            System.out.println("Enter CVV of your credit card");
            int inputCVV = UserInputValidation.getValidInteger();
            return creditCardService.spend(inputPin, cartAmount, inputCVV);
        }
        System.out.println("Invalid selection!..Returning to ShopMart menu!");
        return false;
    }
}
