package com.example.barcodescanner.ui.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegisterStoreOwnerFormStateTest {

    @Test
    public void testIsDataValid_WhenAllErrorsNull_ReturnsTrue() {
        // Arrange
        RegisterStoreOwnerFormState formState = new RegisterStoreOwnerFormState(null, null,
                null, null, null, null, null, null);

        // Act
        boolean isValid = formState.isDataValid();

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testIsDataValid_WhenAnyErrorNotNull_ReturnsFalse() {
        // Arrange
        RegisterStoreOwnerFormState formState = new RegisterStoreOwnerFormState(null, null,
                1, null, null, null, null, null);

        // Act
        boolean isValid = formState.isDataValid();

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testGetUsernameError_ReturnsCorrectValue() {
        // Arrange
        RegisterStoreOwnerFormState formState = new RegisterStoreOwnerFormState(1, null,
                null, null, null, null, null, null);

        // Act
        Integer usernameError = formState.getUsernameError();

        // Assert
        assertEquals((Integer) 1, usernameError);
    }

    @Test
    public void testGetStoreNameError_ReturnsCorrectValue() {
        // Arrange
        RegisterStoreOwnerFormState formState = new RegisterStoreOwnerFormState(null, 2,
                null, null, null, null, null, null);

        // Act
        Integer storeNameError = formState.getStoreNameError();

        // Assert
        assertEquals((Integer) 2, storeNameError);
    }

    @Test
    public void testGetEmailError_ReturnsCorrectValue() {
        // Arrange
        RegisterStoreOwnerFormState formState = new RegisterStoreOwnerFormState(null, null,
                3, null, null, null, null, null);

        // Act
        Integer emailError = formState.getEmailError();

        // Assert
        assertEquals((Integer) 3, emailError);
    }

    @Test
    public void testGetConfirmEmailError_ReturnsCorrectValue() {
        // Arrange
        RegisterStoreOwnerFormState formState = new RegisterStoreOwnerFormState(null, null,
                null, 4, null, null, null, null);

        // Act
        Integer confirmEmailError = formState.getConfirmEmailError();

        // Assert
        assertEquals((Integer) 4, confirmEmailError);
    }

    // Add more test cases for other error fields...

}
