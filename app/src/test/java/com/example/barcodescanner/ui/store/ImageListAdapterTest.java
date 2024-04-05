package com.example.barcodescanner.ui.store;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapterTest extends TestCase {

    public void testGetItemCount() {

        List<Integer> imageList = new ArrayList<>();
        ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
        assertEquals(0, imageListAdapter.getItemCount());

        imageList.add(1);
        assertEquals(1, imageListAdapter.getItemCount());

    }
}