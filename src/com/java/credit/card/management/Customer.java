package com.java.credit.card.management;

import java.util.ArrayList;
import java.util.Scanner;

class Customer extends User{
    private final ArrayList<CreditCard> creditCards;
    private final int customerId;
    private int password;
    private final int appUserId;

    Customer(int userId,String customerName, int password,int customerId){
        this.appUserId=userId;
        this.name=customerName;
        this.password=password;
        this.customerId=customerId;
        this.creditCards=new ArrayList<>();
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public ArrayList<CreditCard> getActiveCreditCards() {
        ArrayList<CreditCard> creditCardList=getCreditCards();
        ArrayList<CreditCard> activeCreditCards=new ArrayList<>();
        for (CreditCard card:creditCardList){
            if(card.getStatus()!=CreditCardStatus.CLOSED){
                activeCreditCards.add(card);
            }
        }
        return activeCreditCards;
    }

    public void addCreditCard(CreditCard creditCard) {
        boolean isSuccess=this.creditCards.add(creditCard);
        if(isSuccess){
            System.out.println("Credit card with number:"+creditCard.getCardNumber()+" is successfully added!");
            return;
        }
        System.out.println("There was a problem in adding cards.. Please try again later!!");
    }

    public int getCustomerId() {
        return customerId;
    }

    public boolean validatePassword(int inputPassword) {
        return this.password==inputPassword;
    }

    public void setPassword(int password) {
        System.out.println("Enter the current customer password to proceed");
        Scanner scanner=new Scanner(System.in);
        int userEnteredPassword=scanner.nextInt();
        if(this.validatePassword(userEnteredPassword)){
            this.password = password;
            System.out.println("Password Successfully changed!!");
            return;
        }
        System.out.println("Unauthorised operation");
    }

    public int getAppUserId() {
        return appUserId;
    }

}
