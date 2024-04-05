package com.example.barcodescanner.customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.barcodescanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment implements ProductRecyclerViewInterface{


    List<StoreModel> storeList;
    RecyclerView favRecView, secondView;
    MyAdapter4 myAdapter;
    MyAdapter5 myAdapter2;
    CheckBox favouritesButton;
    UserListViewModel userListViewModel;
    ProductModel item;
    int position;
    TextView productName;
    TextView productCategory;
    TextView productPrice;
    private MutableLiveData<Boolean> marketsGenerated = new MutableLiveData<>();

    public ProductFragment(ProductModel item, int position) {
        this.item = item;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        container.clearDisappearingChildren();
        marketsGenerated.setValue(false);
        storeList = generateMarkets();
        productName = rootView.findViewById(R.id.pName);
        productCategory = rootView.findViewById(R.id.cName);
        productPrice = rootView.findViewById(R.id.bName);

        productName.setText(item.getProductName());
        productCategory.setText(item.getCategory());
        productPrice.setText(item.getProductPrice());

        favRecView = rootView.findViewById(R.id.carousel);
        favRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        secondView = rootView.findViewById(R.id.difStore);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        secondView.setLayoutManager(layoutManager);

        userListViewModel = ((MainActivity) getActivity()).getUserListViewModel();
//        item = userListViewModel.getFavouritesList().getValue().get(0);

        marketsGenerated.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                myAdapter = new MyAdapter4(storeList);
                myAdapter2 = new MyAdapter5(storeList, ProductFragment.this);
                favRecView.setAdapter(myAdapter);
                secondView.setAdapter(myAdapter2);
            }
        });

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
        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");

        List<StoreModel> market = new ArrayList<>();
        referenceStores.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot stores: dataSnapshot.getChildren()) {
                    if(stores.hasChild(item.getProductBarcode())){
                        DatabaseReference referenceStore = FirebaseDatabase.getInstance().getReference("Users");
                        DatabaseReference referenceID = referenceStore.child("Store Owners");
                        DatabaseReference referenceProductStore = referenceID.child(stores.getKey());
                        referenceProductStore.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot products) {
                                market.add(new StoreModel(products.child("Storename").getValue().toString(), products.child("location").getValue().toString(), generateImages()));
                                marketsGenerated.setValue(true);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return market;
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


    @Override
    public void onItemClick(int position) {
        StoreModel item = storeList.get(position);
        MyAdapter5.VideoViewHolder viewHolder = (MyAdapter5.VideoViewHolder) secondView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            viewHolder.image_view.setImageResource(item.getStoreImage(0));
            viewHolder.store_name.setText(item.getStoreName());
            viewHolder.location_name.setText(item.getStoreLocation());
        }
        if (getContext() != null && getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).replaceFragment(new StoreFragment(item, position), getContext().getString(R.string.product_page_title));
        }
    }

    @Override
    public void onFavouritesClick(int position) {

    }

    @Override
    public void onShoppingListClick(int position) {

    }

    @Override
    public void increment(ProductModel item) {

    }

    @Override
    public void decrement(ProductModel item) {

    }

    @Override
    public void check(ProductModel item) {

    }
}