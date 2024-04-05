package com.example.barcodescanner.ui.login;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import junit.framework.TestCase;

public class LoginFormStateTest extends TestCase {

    public void testLoginFormState_ValidData_ReturnsTrue() {
        LoginFormState loginFormState = new LoginFormState(null, null);
        assertTrue(loginFormState.isDataValid());
    }

    public void testLoginFormState_InvalidUsername_ReturnsFalse() {
        LoginFormState loginFormState = new LoginFormState(1,null);
        assertFalse(loginFormState.isDataValid());
    }

    public void testLoginFormState_InvalidPassword_ReturnsFalse() {
        LoginFormState loginFormState = new LoginFormState(null, 1);
        assertFalse(loginFormState.isDataValid());
    }

    public void testLoginFormState_InvalidUsernameAndPassword_ReturnsFalse() {
        LoginFormState loginFormState = new LoginFormState(1,1);
        assertFalse(loginFormState.isDataValid());
    }
}
