package com.example.barcodescanner;


import com.example.barcodescanner.customer.SharedViewModel;
import junit.framework.TestCase;
import org.junit.Before;

public class SharedViewModelTest extends TestCase {

    private SharedViewModel sharedViewModel;

    @Before
    public void setUp() {
        sharedViewModel = new SharedViewModel(null);
    }

    public void testGetProductModels() {
        assertNotNull(sharedViewModel.getProductModels());
    }

    public void testIsDataFetched() {
        assertNotNull(sharedViewModel.isDataFetched());
    }
}