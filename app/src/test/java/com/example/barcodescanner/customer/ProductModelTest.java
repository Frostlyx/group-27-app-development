package com.example.barcodescanner.customer;

import org.junit.Assert;
import org.junit.Before;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class ProductModelTest extends TestCase {
    ProductModel testProductModel;

    @Before
    public void setUp() {
        List<Integer> testImageList = Collections.emptyList(); // Example image list
        testProductModel = new ProductModel("", "", testImageList, "", "", "", "");
    }

    public void testSettersAndGetters() {
        testProductModel.setProductName("New Product");
        Assert.assertEquals("New Product", testProductModel.getProductName());

        testProductModel.setProductPrice("15.99");
        Assert.assertEquals("15.99", testProductModel.getProductPrice());

        testProductModel.setCategory("Books");
        Assert.assertEquals("Books", testProductModel.getCategory());

        testProductModel.setDiscount("5%");
        Assert.assertEquals("5%", testProductModel.getDiscount());

        testProductModel.setProductAmount("10");
        Assert.assertEquals("10", testProductModel.getProductAmount());

        testProductModel.setProductBarcode("9876543210");
        Assert.assertEquals("9876543210", testProductModel.getProductBarcode());
    }
}