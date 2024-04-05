package com.example.barcodescanner.customer;

import junit.framework.TestCase;

public class ChangePasswordFormStateTest extends TestCase {

    Integer passwordError = 3;
    Integer confirmPasswordError = 2;
    boolean isDataValid = (passwordError == null) && (confirmPasswordError == null);

    ChangePasswordFormState changePasswordFormState = new ChangePasswordFormState(passwordError, confirmPasswordError);
    public void testChangePasswordFormStateConstructor(){

        assertEquals(passwordError, changePasswordFormState.getPasswordError());
        assertEquals(confirmPasswordError, changePasswordFormState.getConfirmPasswordError());
        assertFalse(isDataValid);
    }

    public void testGetPasswordError() {
        assertEquals(passwordError, changePasswordFormState.getPasswordError());
    }

    public void testGetConfirmPasswordError() {
        assertEquals(confirmPasswordError, changePasswordFormState.getConfirmPasswordError());
    }

    public void testIsDataValid() {
        assertFalse(isDataValid);
    }
}