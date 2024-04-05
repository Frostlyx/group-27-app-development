package com.example.barcodescanner.ui.login;

import com.example.barcodescanner.customer.ProductModel;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public class ReadWriteCustomerDetailsTest extends TestCase {

    private ReadWriteCustomerDetails customerDetails;

    @Before
    public void setUp() {
        // Prepare a list of ProductModel for testing
        List<ProductModel> shoppingList = Arrays.asList(
                new ProductModel("Product1", "10.99", Arrays.asList(1, 2, 3), "Electronics", "10%", "5", "1234567890"),
                new ProductModel("Product2", "20.99", Arrays.asList(4, 5, 6), "Books", "5%", "3", "0987654321")
        );

        List<ProductModel> favouriteList = Arrays.asList(
                new ProductModel("Favourite1", "5.99", Arrays.asList(7, 8, 9), "Clothing", "20%", "2", "1122334455"),
                new ProductModel("Favourite2", "6.99", Arrays.asList(10, 11, 12), "Food", "", "20", "6622334455"),
                new ProductModel("Favourite3", "7.99", Arrays.asList(13, 14, 15), "Drinks", "10%", "1", "7722334455")
        );

        // Initialize the ReadWriteCustomerDetails object
        customerDetails = new ReadWriteCustomerDetails("TestUser", "test@example.com", "password123", shoppingList, favouriteList);
    }

    public void testConstructorAndGetters() {
        assertEquals("TestUser", customerDetails.username);
        assertEquals("test@example.com", customerDetails.email);
        assertEquals("password123", customerDetails.password);
        assertEquals(2, customerDetails.ShoppingList.size());
        assertEquals(3, customerDetails.FavouriteList.size());
    }
}
