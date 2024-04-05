package com.example.barcodescanner.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment implements ProductRecyclerViewInterface{

    private List<ProductModel> notFavouritesList;
    List<StoreModel> storeList;
    RecyclerView favRecView, secondView;
    ProductCarouselAdapter myAdapter;
    ProductShopAdapter myAdapter2;
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
        notFavouritesList = new ArrayList<>();

        marketsGenerated.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                myAdapter = new ProductCarouselAdapter(storeList);
                myAdapter2 = new ProductShopAdapter(storeList, ProductFragment.this);
                favRecView.setAdapter(myAdapter);
                secondView.setAdapter(myAdapter2);
            }
        });

        favouritesButton = rootView.findViewById(R.id.cbHeart);
        favouritesButton.setChecked(isFavourite(item));

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
                DatabaseReference referenceCustomers = referenceUsers.child("Customers");
                DatabaseReference referenceCurrentUser = referenceCustomers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                DatabaseReference referenceFavouritesList = referenceCurrentUser.child("Favourites List");

                ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
                if (notFavouritesList.contains(item)) {
                    notFavouritesList.remove(item);
                    referenceFavouritesList.child(item.getProductBarcode()).setValue(item);
                } else {
                    notFavouritesList.add(item);
                    referenceFavouritesList.child(item.getProductBarcode()).removeValue();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : notFavouritesList) {
            tempFavouritesList.remove(pm);
        }
        userListViewModel.setFavouritesList(tempFavouritesList);
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
        ProductShopAdapter.VideoViewHolder viewHolder = (ProductShopAdapter.VideoViewHolder) secondView.findViewHolderForAdapterPosition(position);
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