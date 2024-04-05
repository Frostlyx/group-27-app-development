package com.example.barcodescanner.customer;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerViewAdapterTest extends TestCase {

    public void testOnCreateViewHolder() {
    }

    public void testOnBindViewHolder() {
    }

    public void testGetItemCount() {
        List<StoreModel> marketList = new ArrayList<>();
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(
                marketList, new CategoryRecyclerViewInterface() {
                    @Override
                    public void onItemClick(int position) {
                    }
                });
        assertEquals(0, categoryRecyclerViewAdapter.getItemCount());

        List<Integer> imageList = new ArrayList<>();
        imageList.add(1);
        marketList.add(new StoreModel("a", "b", imageList));
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter2 = new CategoryRecyclerViewAdapter(
                marketList, new CategoryRecyclerViewInterface() {
            @Override
            public void onItemClick(int position) {

            }
        });
        assertEquals(1, categoryRecyclerViewAdapter.getItemCount());
    }
}