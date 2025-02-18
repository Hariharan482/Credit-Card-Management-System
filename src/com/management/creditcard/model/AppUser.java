package com.management.creditcard.model;

import com.management.cart.model.Cart;

import java.util.ArrayList;

public class AppUser extends User{
    private final int userId;
    private int userPassword;
    private static int currentAvailableUserId = 1;
    private final ArrayList<Customer> customerAccounts;
    private final Cart userCart;

    public AppUser(long identificationNumber, int userPassword) {
        this.userId = getCurrentAvailableUserId();
        this.userPassword = userPassword;
        setIdentificationNumber(identificationNumber);
        this.customerAccounts = new ArrayList<>();
        this.userCart = new Cart();
    }

    protected int getCurrentAvailableUserId() {
        return currentAvailableUserId++;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserPassword() {
        return userPassword;
    }

    protected void setUserPassword(int userPassword) {
        this.userPassword = userPassword;
    }

    public void addCustomerAccount(Customer customer) {
        boolean isSuccess = customerAccounts.add(customer);
        if (isSuccess) {
            System.out.println("Customer account linked successfully!");
            return;
        }
        System.out.println("Sorry unable to add customer,Try again later!");
    }

    public ArrayList<Customer> getAppUserCustomerAccounts() {
        return customerAccounts;
    }

    public Cart getUserCart() {
        return userCart;
    }


}
