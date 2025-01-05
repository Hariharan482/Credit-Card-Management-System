package com.java.credit.card.management;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Bank {
    public String bankName;
    private final ArrayList<Customer> customers;
    private final ArrayList<Admin> bankAdmins;
    private int currentAvailableCustomerId=1;
    private int currentAvailableAdminId=1;
    private int rootPassword=999;
    private final ArrayList<String> cardTypes;
    private static long currentCreditCardNumber;

    Bank(){
        this.bankAdmins =new ArrayList<>();
        this.customers=new ArrayList<>();
        this.cardTypes=new ArrayList<>();
    }

    public void setBankName(String name){
        this.bankName=name;
    }

    public String getBankName(){
        return this.bankName;
    }

    public int getCurrentAvailableCustomerId() {
        return (this.currentAvailableCustomerId)++;
    }

    public int getCurrentAvailableAdminId(){
        return (this.currentAvailableAdminId)++;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public boolean setCustomer(Customer customer) {
        return this.customers.add(customer);
    }

    public ArrayList<Admin> getBankAdmins() {
        return bankAdmins;
    }

    public boolean addBankAdmin(Admin bankAdmin) {
        return this.bankAdmins.add(bankAdmin);
    }

    public boolean isValidAdmin(int id,int password){
        for(Admin admin:this.bankAdmins){
            if(admin.getAdminId()==id && admin.validatePassword(password))
                return true;
        }
        return false;
    }

    public boolean isValidCustomer(int id,int password){
        for(Customer customer:this.customers){
            if(customer.getCustomerId() == id && customer.validatePassword(password)){
                return true;
            }
        }
        return false;
    }

    public boolean checkRootUser(){
        System.out.println("Enter the current bank root user password to proceed");
        Scanner scanner=new Scanner(System.in);
        int userEnteredPassword=scanner.nextInt();
        return userEnteredPassword == rootPassword;
    }

    public void setRootPassword(int rootPassword){
        if(this.checkRootUser()){
            this.rootPassword=rootPassword;
            System.out.println("Password Successfully changed!!");
        }
        else {
            System.out.println("Unauthorised operation");
        }
    }

    public Admin getAdmin(int id,int password){
        for(Admin admin:this.bankAdmins){
            if(admin.getAdminId()==id && admin.validatePassword(password))
                return admin;
        }
        return null;
    }

    public ArrayList<String> getCardTypes() {
        return cardTypes;
    }

    public void addCardType(String cardType) {
        this.cardTypes.add(cardType);
    }

    public static long getAvailableCreditCardNumber() {
        Random random = new Random();
        currentCreditCardNumber= 100000000000L + (long)(random.nextDouble() * 900000000000L);
        return currentCreditCardNumber;
    }

    protected Customer isUserHavingAccount(int userID){
        for(Customer customer:this.customers){
            if(customer.getAppUserId()==userID){
                return customer;
            }
        }
        return null;
    }

    protected void addCreditCard(int userID,CreditCard card){
        for(Customer customer:this.customers){
            if(customer.getAppUserId()==userID){
                customer.addCreditCard(card);
                return;
            }
        }
        System.out.println("No user accounts found to link!");
    }
}
