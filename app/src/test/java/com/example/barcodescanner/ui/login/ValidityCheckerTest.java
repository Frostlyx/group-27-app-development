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
        int n = 1;
        for (int i = 0; i <= 20; i++) {
            if (i != 8) {
                assertFalse(validityChecker.isBarcodeValid(Integer.toString(n)));
            } else {
                assertTrue(validityChecker.isBarcodeValid(Integer.toString(n)));
            }
            i *= 10;
        }
        assertFalse(validityChecker.isKvkValid(null));
    }

    public void testIsEmailValid() {
        String s ="a@b.c";
        while (true) {
            s = s.substring(0, s.length() - 1);
            assertFalse(validityChecker.isEmailValid(s));
            if (s.isEmpty()) {
                break;
            }
        }
        assertFalse(validityChecker.isEmailValid(null));
    }

    public void testIsPasswordValid() {
        assertTrue(validityChecker.isPasswordValid("Aaaaaaaaaa"));
        assertFalse(validityChecker.isPasswordValid("AAAAAAAAAAA"));
        assertFalse(validityChecker.isPasswordValid("aaaaaaaaaaaa"));
        assertFalse(validityChecker.isPasswordValid("AAAaaa"));
        assertFalse(validityChecker.isPasswordValid(null));
    }

    public void testIsConfirmValid() {
        String a = "aaa";
        String b = "bbb";
        assertTrue(validityChecker.isConfirmValid(a, a));
        assertTrue(validityChecker.isConfirmValid(b, b));
        assertFalse(validityChecker.isConfirmValid(null, null));
        assertFalse(validityChecker.isConfirmValid(a, b));
        assertFalse(validityChecker.isConfirmValid(b, a));
        assertFalse(validityChecker.isConfirmValid(null, b));
        assertFalse(validityChecker.isConfirmValid(null, a));
        assertFalse(validityChecker.isConfirmValid(a, null));
        assertFalse(validityChecker.isConfirmValid(b, null));
    }

    public void testIsPriceValid() {
        assertTrue(validityChecker.isPriceValid(1.0));
        assertTrue(validityChecker.isPriceValid(1.1));
        assertTrue(validityChecker.isPriceValid(1.11));
        assertFalse(validityChecker.isPriceValid(1.111));
        assertFalse(validityChecker.isPriceValid(null));
        assertTrue(validityChecker.isPriceValid(0.0));
        assertFalse(validityChecker.isPriceValid(-1.0));
    }
}