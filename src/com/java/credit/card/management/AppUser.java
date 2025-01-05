package com.java.credit.card.management;

import java.util.ArrayList;

class AppUser extends User{
    private final int userId;
    private int userPassword;
    private static int currentAvailableUserId=1;
    private final ArrayList<Customer> customerAccounts;

    AppUser(long identificationNumber,int userPassword){
        this.userId=getCurrentAvailableUserId();
        this.userPassword=userPassword;
        setIdentificationNumber(identificationNumber);
        this.customerAccounts=new ArrayList<>();
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

    public void setUserPassword(int userPassword) {
        this.userPassword = userPassword;
    }

    protected void addCustomerAccount(Customer customer){
        boolean isSuccess=customerAccounts.add(customer);
        if(isSuccess){
            System.out.println("Customer account linked successfully!");
            return;
        }
        System.out.println("Sorry unable to add customer,Try again later!");
    }

    protected boolean getLinkedCreditCardsCount(){
        int cardsCount=0;
        for(Customer customer:this.customerAccounts){
            cardsCount+=customer.getActiveCreditCards().size();
        }
        return (cardsCount<5 && cardsCount>=0);
    }

    protected void listCredit(){}
}
