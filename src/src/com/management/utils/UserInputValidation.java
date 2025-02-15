package com.management.utils;
import java.util.Scanner;

public class UserInputValidation {
    private static final Scanner scanner = new Scanner(System.in);

    private UserInputValidation() {}

    public static int getValidInteger() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter it again!");
            scanner.next();
        }
        int validInt = scanner.nextInt();
        scanner.nextLine();
        return validInt;
    }

    public static String getValidString() {
        String userInput = scanner.nextLine();
        while (userInput.trim().isEmpty()) {
            System.out.println("Input cannot be empty. Please enter a valid string.");
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    public static long getValidLong() {
        while (!scanner.hasNextLong()) {
            System.out.println("Invalid input! Please enter it again!");
            scanner.next();
        }
        long validLong = scanner.nextLong();
        scanner.nextLine();
        return validLong;
    }

    public static void closeScanner(){
        scanner.close();
    }
}
