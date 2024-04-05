package com.example.barcodescanner.ui.login;

import android.util.Patterns;

public class ValidityChecker {

    /**
     * Checks if a string is valid.
     *
     * @param s The string to be checked for validity.
     * @return True if the string is not null and not empty after trimming whitespace, false otherwise.
     */
    public boolean isStringValid(String s) {
        return s != null && !s.trim().isEmpty();
    }

    /**
     * Checks if a KVK (Kamer van Koophandel) number is valid.
     *
     * @param kvk The integer representing the KVK number to be checked for validity.
     * @return True if the KVK number is not null and has a length of 8 digits, false otherwise.
     */
    public boolean isKvkValid(Integer kvk) {
        return kvk != null && Integer.toString(kvk).length() == 8;
    }

    /**
     * Checks if a barcode is valid.
     *
     * @param barcode The barcode string to be checked for validity.
     * @return True if the barcode is not null and has a length of 13 characters, false otherwise.
     */
    public boolean isBarcodeValid(String barcode) {
        return barcode != null && barcode.length() == 13;
    }

    /**
     * Validates the format of the email address using a regular expression.
     *
     * @param email Email address to be validated.
     * @return True if the email address matches the pattern, false otherwise.
     */
    public boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validates the format of the password. Password must contain at least one uppercase letter,
     * one lowercase letter, and be at least 8 characters long.
     *
     * @param password Password to be validated.
     * @return True if the password meets the requirements, false otherwise.
     */
    public boolean isPasswordValid(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        if (password == null || password.trim().length() < 8) {
            return false;
        }
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
        }
        return (hasUpperCase && hasLowerCase);
    }

    /**
     * Checks if two strings have the same value.
     *
     * @param string1 First string to compare.
     * @param string2 Second string to compare.
     * @return True if the strings have the same value, false otherwise.
     */
    public boolean isConfirmValid(String string1, String string2) {
        return string1.equals(string2);
    }

    /**
     * Checks if a price is valid.
     * @param price The price to be checked for validity.
     * @return True if the price is not null, greater than zero, and has at most two decimal places, false otherwise.
     */
    public boolean isPriceValid(Double price) {
        if (price == null || price <= 0) {
            return false;
        }
        String[] div = price.toString().split("\\.");
        return div[1].length() <= 2;
    }
}