package com.java.credit.card.management;

import com.java.utils.UserInputValidation;

import java.util.ArrayList;

public class CreditManager {
    private static IOBank ioBank = new IOBank();
    private static YesBank yesBank = new YesBank();
    private static final AppUserManager appUserManager = new AppUserManager();

    private void isRootUser() {
        if (appUserManager.checkRootUser()) {
            System.out.println("Hello Root User!!");
            this.getRootUserMenu();
        } else {
            System.out.println("Incorrect Credentials");
        }
    }

    public static boolean buyProducts(int appUserID, long cartAmount) {
        return appUserManager.buyProducts(appUserID, cartAmount);
    }

    private Bank getPreferedBank() {
        System.out.println("Please select your Bank");
        System.out.println("1-> Yes Bank");
        System.out.println("2-> IO Bank");
        System.out.println("Press any number to return to MainMenu");
        int bankId=UserInputValidation.getValidInteger();
        return switch (bankId) {
            case 1 -> {
                yield yesBank;
            }
            case 2 -> {
                yield ioBank;
            }
            default -> {
                yield null;
            }
        };
    }

    private void isValidAdmin() {
        Bank bank = this.getPreferedBank();
        if (bank == null) {
            return;
        }
        System.out.println("Please enter your Admin ID");
        int id=UserInputValidation.getValidInteger();
        System.out.println("Enter your password");
        int password=UserInputValidation.getValidInteger();
        if (bank.isValidAdmin(id, password)) {
            System.out.println("Successfully logged in!!");
            Admin admin = bank.getAdmin(id, password);
            this.getBankAdministratorMenu(bank, admin);
        } else {
            System.out.println("Incorrect Credentials");
        }
    }

    private void isValidCustomer() {
        System.out.println("Enter your app user Id");
        int id=UserInputValidation.getValidInteger();
        System.out.println("Enter your app user password");
        int password=UserInputValidation.getValidInteger();
        if (appUserManager.isValidUser(id, password)) {
            System.out.println("Successfully Logged In");
            this.getApplicationUserMenu(id);
        } else {
            System.out.println("Incorrect user credentials");
        }
    }

    private void registerUser() {
        System.out.println("Enter user's identification number");
        long identificationNumber=UserInputValidation.getValidLong();
        if (appUserManager.addUserAccount(identificationNumber)) {
            System.out.println("Please login with provided credentials");
        }
    }

    private void getRootUserMenu() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Select the operation to perform");
            System.out.println("1-> Get application users");
            System.out.println("2-> Change root password");
            System.out.println("3-> Register an user");
            System.out.println("4-> Register an admin for a bank");
            System.out.println("9-> Logout");
            int operation=UserInputValidation.getValidInteger();
            switch (operation) {
                case 1:
                    ArrayList<AppUser> applicationUsers = appUserManager.getUsers(true);
                    if (applicationUsers != null) {
                        System.out.println("Application Users List");
                        for (AppUser user : applicationUsers) {
                            System.out.println("Id:" + user.getUserId() + " ID Number:" + user.getIdentificationNumber());
                        }
                        return;
                    }
                    System.out.println("Register an user to view their data");
                    break;
                case 2:
                    System.out.println("Please enter the new root password to set");
                    int password=UserInputValidation.getValidInteger();
                    appUserManager.setRootPassword(password, true);
                    break;
                case 3:
                    this.registerUser();
                    break;
                case 4:
                    Bank bank = this.getPreferedBank();
                    if(bank!=null)
                       appUserManager.addBankAdministrator(bank);
                    break;
                case 9:
                    System.out.println("Successfully logged out!");
                    isExit = true;
                    break;
                default:
                    System.out.println("Enter a valid operation need to be done");
                    break;
            }
        }
    }

    private void getApplicationUserMenu(int appUserID) {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Select the operation to perform");
            System.out.println("1-> Manage bank accounts");
            System.out.println("2-> Deposit an amount");
            System.out.println("3-> Close/Block a credit card");
            System.out.println("4-> View balance");
            System.out.println("5-> Spend an amount");
            System.out.println("6-> Change PIN");
            System.out.println("9-> Logout");
            int operation=UserInputValidation.getValidInteger();
            switch (operation) {
                case 1:
                    appUserManager.getLinkedBankAccounts(appUserID);
                    break;
                case 2:
                    appUserManager.depositAmountToCreditCard(appUserID);
                    break;
                case 3:
                    System.out.println("1-> Close the credit card");
                    System.out.println("2-> Block the credit card");
                    System.out.println("Press any key to return to admin menu");
                    int userPreference=UserInputValidation.getValidInteger();
                    if (userPreference == 1 || userPreference == 2) {
                        appUserManager.blockOrCancelCreditCard(appUserID, userPreference);
                    }
                    break;
                case 4:
                    appUserManager.viewBalanceOfCreditCard(appUserID);
                    break;
                case 5:
                    System.out.println("1-> Pay a vendor now!");
                    System.out.println("2-> ShopMart");
                    System.out.println("Press any key to return to admin menu");
                    int selectedSpendPreference=UserInputValidation.getValidInteger();
                    if (selectedSpendPreference == 1 || selectedSpendPreference == 2)
                        appUserManager.spendMoney(appUserID, selectedSpendPreference);
                    break;
                case 6:
                    appUserManager.changeCreditCardPIN(appUserID);
                    System.out.println();
                    break;
                case 9:
                    System.out.println("Successfully logged out!");
                    isExit = true;
                    break;
                default:
                    System.out.println("Enter a valid operation need to be done");
                    break;
            }
        }
    }

    private void getBankAdministratorMenu(Bank bank, Admin admin) {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Select the operation to perform");
            System.out.println("1-> View all customer data");
            System.out.println("2-> Change password");
            System.out.println("3-> Register a customer");
            System.out.println("4-> Register an admin");
            System.out.println("5-> View all issued credit cards");
            System.out.println("6-> Issue new Credit Card");
            System.out.println("7-> Close/Block a credit card");
            System.out.println("8-> Retrieve file with details of blocked/closed credit cards");
            System.out.println("9-> Logout");
            int operation=UserInputValidation.getValidInteger();
            switch (operation) {
                case 1:
                    admin.viewAllCustomers();
                    break;
                case 2:
                    System.out.println("1-> Change bank root admin user password");
                    System.out.println("2-> Change your admin account password");
                    System.out.println("Press any key to return to admin menu");
                    int selectedPreference=UserInputValidation.getValidInteger();
                    if (selectedPreference == 1) {
                        System.out.println("Please enter the new root password to set for Bank root user");
                        int newPassword=UserInputValidation.getValidInteger();
                        bank.setRootPassword(newPassword);
                        return;
                    }
                    if (selectedPreference == 2) {
                        System.out.println("Please enter the new admin password to set");
                        int password=UserInputValidation.getValidInteger();
                        admin.setPassword(password);
                        return;
                    }
                    break;
                case 3:
                    admin.addCustomer();
                    break;
                case 4:
                    admin.addAdmin();
                    break;
                case 5:
                    admin.viewAllIssuedCreditCards();
                    break;
                case 6:
                    System.out.println("Enter user's identification number");
                    long identificationNumber=UserInputValidation.getValidLong();
                    admin.issueNewCreditCard(identificationNumber);
                    break;
                case 7:
                    System.out.println("1-> Close the credit card");
                    System.out.println("2-> Block the credit card");
                    System.out.println("Press any key to return to admin menu");
                    int userPreference=UserInputValidation.getValidInteger();
                    if (userPreference == 1 || userPreference == 2) {
                        System.out.println("Enter CustomerID");
                        int customerID=UserInputValidation.getValidInteger();
                        admin.blockOrCancelCreditCard(userPreference, customerID);
                    }
                    break;
                case 8:
                    admin.viewBlockedOrClosedCreditCards();
                    break;
                case 9:
                    System.out.println("Successfully logged out!");
                    isExit = true;
                    break;
                default:
                    System.out.println("Enter a valid operation need to be done");
                    break;
            }
        }
    }

    public void getMainMenu() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Select the operation to perform");
            System.out.println("1-> Login as a an user");
            System.out.println("2-> Login as a Bank Administrator");
            System.out.println("3-> Register an user");
            System.out.println("4-> Login as a root user");
            System.out.println("0-> Exit");
            int operation=UserInputValidation.getValidInteger();
            switch (operation) {
                case 1:
                    this.isValidCustomer();
                    break;
                case 2:
                    this.isValidAdmin();
                    break;
                case 3:
                    this.registerUser();
                    break;
                case 4:
                    this.isRootUser();
                    break;
                case 0:
                    isExit = true;
                    System.out.println("Thanks for using! Have a great day!!");
                    break;
                default:
                    System.out.println("Enter a valid operation need to be done");
                    break;
            }
        }
        UserInputValidation.closeScanner();
    }

}
