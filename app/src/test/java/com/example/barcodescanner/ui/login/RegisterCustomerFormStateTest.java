package com.example.barcodescanner.ui.login;

import junit.framework.TestCase;

public class RegisterCustomerFormStateTest extends TestCase {

    public void testRegisterFormState_ValidData_ReturnsTrue() {
        RegisterCustomerFormState registerFormState = new RegisterCustomerFormState(null, null, null, null, null);
        assertTrue(registerFormState.isDataValid());
    }

    public void testRegisterFormState_InvalidUsername_ReturnsFalse() {
        RegisterCustomerFormState registerFormState = new RegisterCustomerFormState(1, null, null, null, null);
        assertFalse(registerFormState.isDataValid());
    }

    public void testRegisterFormState_InvalidEmail_ReturnsFalse() {
        RegisterCustomerFormState registerFormState = new RegisterCustomerFormState(null, 1, null, null, null);
        assertFalse(registerFormState.isDataValid());
    }

    public void testRegisterFormState_InvalidConfirmEmail_ReturnsFalse() {
        RegisterCustomerFormState registerFormState = new RegisterCustomerFormState(null, null, 1, null, null);
        assertFalse(registerFormState.isDataValid());
    }

    public void testRegisterFormState_InvalidPassword_ReturnsFalse() {
        RegisterCustomerFormState registerFormState = new RegisterCustomerFormState(null, null, null, 1, null);
        assertFalse(registerFormState.isDataValid());
    }

    public void testRegisterFormState_InvalidConfirmPassword_ReturnsFalse() {
        RegisterCustomerFormState registerFormState = new RegisterCustomerFormState(null, null, null, null, 1);
        assertFalse(registerFormState.isDataValid());
    }

    public void testRegisterFormState_SomeFieldsNonNull_ReturnsFalse() {
        RegisterCustomerFormState registerFormState = new RegisterCustomerFormState(null, 1, null, 1, 1);
        assertFalse(registerFormState.isDataValid());
    }

    public void testRegisterFormState_AllFieldsNonNull_ReturnsFalse() {
        RegisterCustomerFormState registerFormState = new RegisterCustomerFormState(1, 1, 1, 1, 1);
        assertFalse(registerFormState.isDataValid());
    }

}