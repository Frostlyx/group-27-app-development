package com.example.barcodescanner.customer;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ProductCarouselAdapterTest extends TestCase {

    public void testGetItemCount() {

        List<StoreModel> marketList = new ArrayList<>();
        ProductCarouselAdapter productCarouselAdapter = new ProductCarouselAdapter(marketList);
        assertEquals(0, productCarouselAdapter.getItemCount());

        List<Integer> imageList = new ArrayList<>();
        imageList.add(1);
        marketList.add(new StoreModel("a", "b", imageList));
        assertEquals(1, productCarouselAdapter.getItemCount());

    }
}