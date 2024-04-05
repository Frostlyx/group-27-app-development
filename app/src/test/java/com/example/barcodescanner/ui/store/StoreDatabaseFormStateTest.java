package com.example.barcodescanner.ui.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StoreDatabaseFormStateTest {

    @Test
    public void testIsDataValid_WhenAllErrorsNull_ReturnsTrue() {
        // Arrange
        StoreDatabaseFormState formState = new StoreDatabaseFormState(null, null, null, null);

        // Act
        boolean isValid = formState.isDataValid();

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testIsDataValid_WhenAnyErrorNotNull_ReturnsFalse() {
        // Arrange
        StoreDatabaseFormState formState = new StoreDatabaseFormState(null, null, 1, null);

        // Act
        boolean isValid = formState.isDataValid();

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testGetNameError_ReturnsCorrectValue() {
        // Arrange
        StoreDatabaseFormState formState = new StoreDatabaseFormState(1, null, null, null);

        // Act
        Integer nameError = formState.getNameError();

        // Assert
        assertEquals((Integer) 1, nameError);
    }

    @Test
    public void testGetBarcodeError_ReturnsCorrectValue() {
        // Arrange
        StoreDatabaseFormState formState = new StoreDatabaseFormState(null, 2, null, null);

        // Act
        Integer barcodeError = formState.getBarcodeError();

        // Assert
        assertEquals((Integer) 2, barcodeError);
    }

    @Test
    public void testGetAmountError_ReturnsCorrectValue() {
        // Arrange
        StoreDatabaseFormState formState = new StoreDatabaseFormState(null, null, 3, null);

        // Act
        Integer amountError = formState.getAmountError();

        // Assert
        assertEquals((Integer) 3, amountError);
    }

    @Test
    public void testGetPriceError_ReturnsCorrectValue() {
        // Arrange
        StoreDatabaseFormState formState = new StoreDatabaseFormState(null, null, null, 4);

        // Act
        Integer priceError = formState.getPriceError();

        // Assert
        assertEquals((Integer) 4, priceError);
    }
}
