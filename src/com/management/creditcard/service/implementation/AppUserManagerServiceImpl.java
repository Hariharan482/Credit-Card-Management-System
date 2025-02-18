package com.management.creditcard.service.implementation;

import com.management.creditcard.model.*;
import com.management.creditcard.service.interfaces.AppUserManagerService;
import com.management.utils.UserInputValidation;

public record AppUserManagerServiceImpl(AppUserManager appUserManager) implements AppUserManagerService {

    private boolean addUser(AppUser user) {
        boolean isSuccess = this.appUserManager.getUserAccounts().add(user);
        if (isSuccess) {
            System.out.println("User account for Credit manager is created Successfully!!");
            System.out.println("UserId for Identification Number " + user.getIdentificationNumber() + " is: " + user.getUserId());
            return true;
        }
        System.out.println("Sorry,there was a problem creating your account!!");
        return false;
    }

    private AppUser getAppUser(int appUserID) {
        for (AppUser user : appUserManager.getUserAccounts()) {
            if (user.getUserId() == appUserID)
                return user;
        }
        return null;
    }

    @Override
    public boolean isExistingUser(long identificationNumber) {
        for (AppUser userAccount : this.appUserManager.getUserAccounts()) {
            if (userAccount.getIdentificationNumber() == identificationNumber) {
                System.out.println("User account Already Exists!!");
                return true;
            }
        }
        return false;
    }

    @Override
    public void addBankAdministrator(Bank bank) {
        if (this.appUserManager.checkRootUser()) {
            System.out.println("Enter the name of Admin:");
            String name = UserInputValidation.getValidString();
            System.out.println("Enter the password:");
            int password = UserInputValidation.getValidInteger();
            Admin admin = new Admin(bank, name, password);
            if (bank.addBankAdmin(admin)) {
                System.out.println("Admin ID: " + admin.getAdminId() + " for Bank " + bank.getBankName() + " is Successfully Added!");
                return;
            }
            System.out.println("Sorry,there was a problem creating admin account!!");
        } else {
            System.out.println("Invalid credentials,Unauthorised operation!");
        }
    }

    @Override
    public boolean addUserAccount(long identificationNumber) {
        if (isExistingUser(identificationNumber)) {
            return false;
        }
        System.out.println("Enter the password for the new credit manager user account:");
        int userPassword = UserInputValidation.getValidInteger();
        return this.addUser(new AppUser(identificationNumber, userPassword));
    }

    @Override
    public boolean isValidUser(int id, int password) {
        for (AppUser userAccount : this.appUserManager.getUserAccounts()) {
            if (userAccount.getUserId() == id && userAccount.getUserPassword() == password)
                return true;
        }
        return false;
    }

    @Override
    public int getUserId(long identificationNumber) {
        for (AppUser userAccount : this.appUserManager.getUserAccounts()) {
            if (userAccount.getIdentificationNumber() == identificationNumber) {
                return userAccount.getUserId();
            }
        }
        System.out.println("Enter the password for the new credit manager user account:");
        int userPassword = UserInputValidation.getValidInteger();
        AppUser user = new AppUser(identificationNumber, userPassword);
        if (this.addUser(user)) {
            return user.getUserId();
        } else {
            System.out.println("There was a problem in getting userId.. Please try again later!!");
            return 0;
        }
    }

    @Override
    public void linkCustomerAccount(int userID, Customer customer) {
        for (AppUser user : this.appUserManager.getUserAccounts()) {
            if (user.getUserId() == userID) {
                user.addCustomerAccount(customer);
                return;
            }
        }
        System.out.println("No user accounts found to link!");
    }

    @Override
    public boolean getLinkedCreditCardsCount(int userID) {
        for (AppUser user : this.appUserManager.getUserAccounts()) {
            if (user.getUserId() == userID) {
                return new AppUserServiceImpl(user).getLinkedCreditCardsCount();
            }
        }
        return false;
    }

    @Override
    public void getLinkedBankAccounts(int appUserID) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        new AppUserServiceImpl(user).getCustomerAccounts();
    }

    @Override
    public void blockOrCancelCreditCard(int appUserID, int userPreference) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        new AppUserServiceImpl(user).blockOrCancelCreditCard(userPreference);
    }

    @Override
    public void viewBalanceOfCreditCard(int appUserID) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        new AppUserServiceImpl(user).viewBalanceOfCreditCard();
    }

    @Override
    public void depositAmountToCreditCard(int appUserID) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        new AppUserServiceImpl(user).depositAmountToCreditCard();
    }

    @Override
    public void changeCreditCardPIN(int appUserID) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        new AppUserServiceImpl(user).changeCreditCardPIN();
    }

    @Override
    public void spendMoney(int appUserID, int selectedSpendPreference) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        new AppUserServiceImpl(user).spendMoney(selectedSpendPreference, appUserID);
    }

    @Override
    public boolean buyProducts(int appUserID, long productPrice) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return false;
        }
        return new AppUserServiceImpl(user).buyProducts(productPrice);
    }
}
