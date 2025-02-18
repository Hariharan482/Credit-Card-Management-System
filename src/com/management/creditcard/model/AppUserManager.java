package com.management.creditcard.model;

import com.management.utils.UserInputValidation;

import java.util.ArrayList;

public class AppUserManager {
    private static final ArrayList<AppUser> userAccounts = new ArrayList<>();
    private static int rootPassword = 1234;

    public ArrayList<AppUser> getUsers(boolean isRootUser) {
        if (isRootUser && checkRootUser())
            return userAccounts;
        System.out.println("Unauthorised operation");
        return null;
    }

    public boolean checkRootUser() {
        System.out.println("Enter the current root password to proceed");
        int userEnteredPassword= UserInputValidation.getValidInteger();
        return userEnteredPassword == rootPassword;
    }

    public void setRootPassword(int password, boolean isRootUser) {
        if (isRootUser && checkRootUser()) {
            rootPassword = password;
            System.out.println("Password Successfully changed!!");
        } else {
            System.out.println("Unauthorised operation");
        }
    }

    public ArrayList<AppUser> getUserAccounts(){
        return userAccounts;
    }

    public boolean addUser(AppUser user) {
        boolean isSuccess = userAccounts.add(user);
        if (isSuccess) {
            System.out.println("User account for Credit manager is created Successfully!!");
            System.out.println("UserId for Identification Number " + user.getIdentificationNumber() + " is: " + user.getUserId());
            return true;
        }
        System.out.println("Sorry,there was a problem creating your account!!");
        return false;
    }
}
