package com.management.creditcard.model;

import com.management.creditcard.implementation.AppUserManagerServiceImpl;
import com.management.utils.UserInputValidation;

public class Admin extends User{
    private final int adminId;
    private int password;
    private final Bank bank;
    private final AppUserManager appUserManager = new AppUserManager();

    public Admin(Bank bank, String name, int password) {
        this.bank = bank;
        this.name = name;
        this.adminId = this.bank.getCurrentAvailableAdminId();
        this.password = password;
    }

    public int generateUserAccountID(long identificationNumber) {
        AppUserManagerServiceImpl appUserService=new AppUserManagerServiceImpl(this.appUserManager);
        return appUserService.getUserId(identificationNumber);
    }

    public boolean validatePassword(int inputPassword) {
        return this.password == inputPassword;
    }

    public void setPassword(int password) {
        System.out.println("Enter the current admin password to proceed");
        int userEnteredPassword= UserInputValidation.getValidInteger();
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

    public Bank getBank() {
        return bank;
    }

    public AppUserManager getAppUserManager() {
        return appUserManager;
    }
}
