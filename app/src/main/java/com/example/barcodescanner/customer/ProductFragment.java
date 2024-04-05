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

/**
 * Fragment class for displaying detailed information about a product, including its availability in different stores.
 */
public class ProductFragment extends Fragment implements ProductRecyclerViewInterface {

    // List to hold products that are not marked as favorites
    private List<ProductModel> notFavouritesList;
    List<StoreModel> storeList;
    // Recyclerviews to display carousel of images and other available stores
    RecyclerView favRecView, secondView;
    // Adapters to connect recyclerviews with store list and image list
    ProductCarouselAdapter myAdapter;
    ProductShopAdapter myAdapter2;
    CheckBox favouritesButton;
    UserListViewModel userListViewModel;
    ProductModel item;
    int position;
    // Textviews to display product information
    TextView productName;
    TextView productCategory;
    TextView productPrice;
    private MutableLiveData<Boolean> marketsGenerated = new MutableLiveData<>();

    // Constructor to initialize the fragment with a specific product and its position.
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

        // Sets product information
        productName.setText(item.getProductName());
        productCategory.setText(item.getCategory());
        productPrice.setText(item.getProductPrice());

        // Sets recyclerview carousel of images
        favRecView = rootView.findViewById(R.id.carousel);
        favRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Sets recyclerview of other stores
        secondView = rootView.findViewById(R.id.difStore);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        secondView.setLayoutManager(layoutManager);

        // Gets shared user list view model
        userListViewModel = ((MainActivity) requireActivity()).getUserListViewModel();
        notFavouritesList = new ArrayList<>();

        // Sets up an observer to listen for changes in the marketsGenerated LiveData object
        marketsGenerated.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                // Creates new adapters
                myAdapter = new ProductCarouselAdapter(storeList);
                myAdapter2 = new ProductShopAdapter(storeList, ProductFragment.this);
                // Sets adapters
                favRecView.setAdapter(myAdapter);
                secondView.setAdapter(myAdapter2);
            }
        });

        // Sets up favourite button
        favouritesButton = rootView.findViewById(R.id.cbHeart);
        favouritesButton.setChecked(isFavourite(item));

        favouritesButton.setOnClickListener(v -> {
            // Firebase references for user favorites
            DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
            DatabaseReference referenceCustomers = referenceUsers.child("Customers");
            DatabaseReference referenceCurrentUser = referenceCustomers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            DatabaseReference referenceFavouritesList = referenceCurrentUser.child("Favourites List");

            ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
            if (notFavouritesList.contains(item)) {
                // Remove from favorites
                notFavouritesList.remove(item);
                referenceFavouritesList.child(item.getProductBarcode()).setValue(item);
            } else {
                // Add to favorites
                notFavouritesList.add(item);
                referenceFavouritesList.child(item.getProductBarcode()).removeValue();
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

    // Generates a list of stores where the product is available.
    private List<StoreModel> generateMarkets() {
        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");

        List<StoreModel> market = new ArrayList<>();
        referenceStores.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot stores : dataSnapshot.getChildren()) {
                    if (stores.hasChild(item.getProductBarcode())) {
                        // Fetch store details
                        DatabaseReference referenceStore = FirebaseDatabase.getInstance().getReference("Users");
                        DatabaseReference referenceID = referenceStore.child("Store Owners");
                        DatabaseReference referenceProductStore = referenceID.child(stores.getKey());
                        referenceProductStore.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot products) {
                                // Add store to the market list
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

    // Generates a list of placeholder images for stores.
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

    // Checks if the given product is marked as a favorite.
    // Takes a product, returns either true or false
    private boolean isFavourite(ProductModel item) {
        ArrayList<ProductModel> favouritesList = userListViewModel.getFavouritesList().getValue();
        for (ProductModel pm : favouritesList) {
            if (pm.getProductName().equals(item.getProductName())) {
                return true;
            }
        }
        return false;
    }

    // Opens the store page of the store where the current product is being sold
    @Override
    public void onItemClick(int position) {
        StoreModel item = storeList.get(position);
        // Sets up store fragment
        ProductShopAdapter.VideoViewHolder viewHolder = (ProductShopAdapter.VideoViewHolder) secondView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            // Sets store information
            viewHolder.image_view.setImageResource(item.getStoreImage(0));
            viewHolder.store_name.setText(item.getStoreName());
            viewHolder.location_name.setText(item.getStoreLocation());
        }
        if (getContext() != null && getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).replaceFragment(new StoreFragment(item, position), getContext().getString(R.string.product_page_title));
        }
    }

    // Unused method from ProductRecyclerViewInterface
    @Override
    public void onFavouritesClick(int position) {

    }

    // Unused method from ProductRecyclerViewInterface
    @Override
    public void onShoppingListClick(int position) {

    }

    // Unused method from ProductRecyclerViewInterface
    @Override
    public void increment(ProductModel item) {

    }

    // Unused method from ProductRecyclerViewInterface
    @Override
    public void decrement(ProductModel item) {

    }

    // Unused method from ProductRecyclerViewInterface
    @Override
    public void check(ProductModel item) {

    }
}
