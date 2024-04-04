package com.example.barcodescanner.customer;

import static com.example.barcodescanner.customer.CategoriesFragment.getScreenWidth;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {


    List<StoreModel> storeList;
    RecyclerView favRecView, secondView;
    MyAdapter4 myAdapter;
    MyAdapter5 myAdapter2;
    CheckBox favouritesButton;
    UserListViewModel userListViewModel;
    ProductModel item;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        container.clearDisappearingChildren();
        storeList = generateMarkets();

        favRecView = rootView.findViewById(R.id.carousel);
        favRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        secondView = rootView.findViewById(R.id.difStore);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        secondView.setLayoutManager(layoutManager);

        userListViewModel = ((MainActivity) getActivity()).getUserListViewModel();
        item = userListViewModel.getFavouritesList().getValue().get(0);

        myAdapter = new MyAdapter4(storeList);
        myAdapter2 = new MyAdapter5(storeList);
        favRecView.setAdapter(myAdapter);
        secondView.setAdapter(myAdapter2);
        favouritesButton = rootView.findViewById(R.id.cbHeart);
        favouritesButton.setChecked(isFavourite(item));

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
                if (!tempFavouritesList.contains(item)) {
                    tempFavouritesList.add(item);
                    userListViewModel.setFavouritesList(tempFavouritesList);
                } else {
                    tempFavouritesList.remove(item);
                    userListViewModel.setFavouritesList(tempFavouritesList);
                }
            }
        });

        return rootView;
    }

    private List<StoreModel> generateMarkets(){
        List<StoreModel> item = new ArrayList<>();
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        item.add(new StoreModel("deneme", "denenmis", generateImages()));
        return item;
    }

    private List<Integer> generateImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        return images;
    }

    private boolean isFavourite(ProductModel item) {
        ArrayList<ProductModel> favouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : favouritesList) {
            if (pm.getProductName().equals(item.getProductName())) {
                return true;
            }
        }
        return false;
    }


}