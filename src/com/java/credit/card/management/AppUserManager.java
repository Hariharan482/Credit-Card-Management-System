package com.java.credit.card.management;

import com.java.utils.UserInputValidation;

import java.util.ArrayList;

class AppUserManager {
    private static final ArrayList<AppUser> userAccounts = new ArrayList<>();
    private static int rootPassword = 1234;

    protected ArrayList<AppUser> getUsers(boolean isRootUser) {
        if (isRootUser && checkRootUser())
            return userAccounts;
        System.out.println("Unauthorised operation");
        return null;
    }

    public void setRootPassword(int password, boolean isRootUser) {
        if (isRootUser && checkRootUser()) {
            rootPassword = password;
            System.out.println("Password Successfully changed!!");
        } else {
            System.out.println("Unauthorised operation");
        }
    }

    public boolean checkRootUser() {
        System.out.println("Enter the current root password to proceed");
        int userEnteredPassword= UserInputValidation.getValidInteger();
        return userEnteredPassword == rootPassword;
    }

    public boolean isExistingUser(long identificationNumber) {
        for (AppUser userAccount : userAccounts) {
            if (userAccount.getIdentificationNumber() == identificationNumber) {
                System.out.println("User account Already Exists!!");
                return true;
            }
        }
        return false;
    }

    private boolean addUser(AppUser user) {
        boolean isSuccess = userAccounts.add(user);
        if (isSuccess) {
            System.out.println("User account for Credit manager is created Successfully!!");
            System.out.println("UserId for Identification Number " + user.getIdentificationNumber() + " is: " + user.getUserId());
            return true;
        }
        System.out.println("Sorry,there was a problem creating your account!!");
        return false;
    }

    protected void addBankAdministrator(Bank bank) {
        if (this.checkRootUser()) {
            System.out.println("Enter the name of Admin:");
            String name=UserInputValidation.getValidString();
            System.out.println("Enter the password:");
            int password=UserInputValidation.getValidInteger();
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

    protected boolean addUserAccount(long identificationNumber) {
        if (isExistingUser(identificationNumber)) {
            return false;
        }
        System.out.println("Enter the password for the new credit manager user account:");
        int userPassword=UserInputValidation.getValidInteger();
        return this.addUser(new AppUser(identificationNumber, userPassword));
    }

    public boolean isValidUser(int id, int password) {
        for (AppUser userAccount : userAccounts) {
            if (userAccount.getUserId() == id && userAccount.getUserPassword() == password)
                return true;
        }
        return false;
    }

    protected int getUserId(long identificationNumber) {
        for (AppUser userAccount : userAccounts) {
            if (userAccount.getIdentificationNumber() == identificationNumber) {
                return userAccount.getUserId();
            }
        }
        System.out.println("Enter the password for the new credit manager user account:");
        int userPassword=UserInputValidation.getValidInteger();
        AppUser user = new AppUser(identificationNumber, userPassword);
        if (this.addUser(user)) {
            return user.getUserId();
        } else {
            System.out.println("There was a problem in getting userId.. Please try again later!!");
            return 0;
        }

    }

    protected void linkCustomerAccount(int userID, Customer customer) {
        for (AppUser user : userAccounts) {
            if (user.getUserId() == userID) {
                user.addCustomerAccount(customer);
                return;
            }
        }
        System.out.println("No user accounts found to link!");
    }

    protected boolean getLinkedCreditCardsCount(int userID) {
        for (AppUser user : userAccounts) {
            if (user.getUserId() == userID) {
                return user.getLinkedCreditCardsCount();
            }
        }
        return false;
    }

    private AppUser getAppUser(int appUserID) {
        for (AppUser user : userAccounts) {
            if (user.getUserId() == appUserID)
                return user;
        }
        return null;
    }

    protected void getLinkedBankAccounts(int appUserID) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        user.getCustomerAccounts();
    }

    protected void blockOrCancelCreditCard(int appUserID, int userPreference) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        user.blockOrCancelCreditCard(userPreference);
    }

    protected void viewBalanceOfCreditCard(int appUserID) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        user.viewBalanceOfCreditCard();
    }

    protected void depositAmountToCreditCard(int appUserID) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        user.depositAmountToCreditCard();
    }

    protected void changeCreditCardPIN(int appUserID) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        user.changeCreditCardPIN();
    }

    protected void spendMoney(int appUserID, int selectedSpendPreference) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return;
        }
        user.spendMoney(selectedSpendPreference, appUserID);
    }

    protected boolean buyProducts(int appUserID, long productPrice) {
        AppUser user = this.getAppUser(appUserID);
        if (user == null) {
            System.out.println("No such user");
            return false;
        }
        return user.buyProducts(productPrice);
    }
}
