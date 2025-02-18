package com.management.utils;
import java.util.Scanner;

/**
 * Utility class that provides methods for validating user input from the console.
 */
public class UserInputValidation {
    private static final Scanner scanner = new Scanner(System.in);

    private UserInputValidation() {}

    /**
     * Prompts the user for an integer input and validates that it is a valid integer.
     * If the input is invalid, the user will be prompted again until a valid integer is entered.
     *
     * @return The valid integer input from the user.
     */
    public static int getValidInteger() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter it again!");
            scanner.next();
        }
        int validInt = scanner.nextInt();
        scanner.nextLine();
        return validInt;
    }

    /**
     * Prompts the user for a string input and validates that it is not empty.
     * If the input is empty or only whitespace, the user will be prompted again until a valid string is entered.
     *
     * @return The valid string input from the user.
     */
    public static String getValidString() {
        String userInput = scanner.nextLine();
        while (userInput.trim().isEmpty()) {
            System.out.println("Input cannot be empty. Please enter a valid string.");
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    /**
     * Prompts the user for a long input and validates that it is a valid long number.
     * If the input is invalid, the user will be prompted again until a valid long is entered.
     *
     * @return The valid long input from the user.
     */
    public static long getValidLong() {
        while (!scanner.hasNextLong()) {
            System.out.println("Invalid input! Please enter it again!");
            scanner.next();
        }
        long validLong = scanner.nextLong();
        scanner.nextLine();
        return validLong;
    }

    /**
     * Closes the scanner instance to release the resources used by the scanner.
     */
    public static void closeScanner(){
        scanner.close();
    }
}
