package com.example.barcodescanner.ui.store;

import com.example.barcodescanner.R;

import junit.framework.TestCase;

public class EditStoreFormStateTest extends TestCase {

    EditStoreFormState editStoreFormStateError = new EditStoreFormState(R.string.invalid_kvk);
    EditStoreFormState editStoreFormStateNull = new EditStoreFormState(null);
    public void testGetKvkError() {
        Integer expectedKvkError = R.string.invalid_kvk;
        Integer actualKvkError = editStoreFormStateError.getKvkError();
        assertEquals(expectedKvkError, actualKvkError);
    }

    public void testIsDataValid() {
        assertFalse(editStoreFormStateError.isDataValid());
        assertTrue(editStoreFormStateNull.isDataValid());
    }
}