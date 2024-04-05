package com.example.barcodescanner.ui.login;

import junit.framework.TestCase;

public class ValidityCheckerTest extends TestCase {

    ValidityChecker validityChecker = new ValidityChecker();

    public void testIsStringValid() {
        assertTrue(validityChecker.isStringValid("a"));
        assertFalse(validityChecker.isStringValid(""));
        assertFalse(validityChecker.isStringValid(null));
    }

    public void testIsKvkValid() {
        int n = 1;
        for (int i = 0; i <= 10; i++) {
            if (i != 8) {
                assertFalse(validityChecker.isKvkValid(n));
            } else {
                assertTrue(validityChecker.isKvkValid(n));
            }
            i *= 10;
        }
        assertFalse(validityChecker.isKvkValid(null));
    }

    public void testIsBarcodeValid() {
    }

    public void testIsEmailValid() {
    }

    public void testIsPasswordValid() {
    }

    public void testIsConfirmValid() {
    }

    public void testIsPriceValid() {
    }
}