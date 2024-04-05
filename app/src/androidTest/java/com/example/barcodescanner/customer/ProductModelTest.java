package com.example.barcodescanner.customer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import junit.framework.TestCase;

//this is copy pasted from phind.com, idk if this works at all it wont run on my computer
public class ProductModelTest {

    private ProductModel productModel;

    @Before
    public void setUp() {
        List<Integer> imageList = Arrays.asList(1, 2, 3);
        productModel = new ProductModel("Test Product", "10.99", imageList, "Electronics", "10%", "5", "1234567890");
    }

    @Test
    public void testConstructor() {
        Assert.assertNotNull(productModel);
        Assert.assertEquals("Test Product", productModel.getProductName());
        Assert.assertEquals("10.99", productModel.getProductPrice());
        Assert.assertEquals(Arrays.asList(1, 2, 3), productModel.getProductImageList());
        Assert.assertEquals("Electronics", productModel.getCategory());
        Assert.assertEquals("10%", productModel.getDiscount());
        Assert.assertEquals("5", productModel.getProductAmount());
        Assert.assertEquals("1234567890", productModel.getProductBarcode());
    }

    @Test
    public void testGetProductImage() {
        Assert.assertEquals(1, productModel.getProductImage(0));
        Assert.assertEquals(2, productModel.getProductImage(1));
        Assert.assertEquals(3, productModel.getProductImage(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetProductImageOutOfBounds() {
        productModel.getProductImage(3);
    }

    @Test
    public void testSettersAndGetters() {
        productModel.setProductName("New Product");
        Assert.assertEquals("New Product", productModel.getProductName());

        productModel.setProductPrice("15.99");
        Assert.assertEquals("15.99", productModel.getProductPrice());

        productModel.setCategory("Books");
        Assert.assertEquals("Books", productModel.getCategory());

        productModel.setDiscount("5%");
        Assert.assertEquals("5%", productModel.getDiscount());

        productModel.setProductAmount("10");
        Assert.assertEquals("10", productModel.getProductAmount());

        productModel.setProductBarcode("9876543210");
        Assert.assertEquals("9876543210", productModel.getProductBarcode());
    }
}