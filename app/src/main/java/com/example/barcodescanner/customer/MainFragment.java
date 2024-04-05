package com.example.barcodescanner.customer;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Customer side fragment to display products in the database.
 * Allows for searching by searchbar, via categories, or the barcode scanner.
 */
public class MainFragment extends Fragment implements ProductRecyclerViewInterface {

    ImageButton barcodeScannerButton;
    // recycler view that holds the products to show
    RecyclerView recyclerView;
    SearchView searchView;
    // adapter to connect the shared model to display on the recycler view
    ProductRecyclerViewAdapter adapter;
    // Shared model that holds the products to display
    private SharedViewModel sharedViewModel;
    private UserListViewModel userListViewModel;
    // Gets database references of user
    // like their shopping list, favourites list, and general information
    DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference referenceCustomers = referenceUsers.child("Customers");
    DatabaseReference referenceCurrentUser = referenceCustomers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    DatabaseReference referenceShoppingList = referenceCurrentUser.child("Shopping List");
    DatabaseReference referenceFavouritesList = referenceCurrentUser.child("Favourites List");

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view models
        sharedViewModel = ((MainActivity) getActivity()).getSharedViewModel();
        userListViewModel = ((MainActivity) getActivity()).getUserListViewModel();
        userListViewModel.setProductModels(sharedViewModel.getProductModels().getValue());
        List<Integer> imageList = generateImages();

        // Fetch data for favourites list from Firebase
        referenceFavouritesList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot products : dataSnapshot.getChildren()) {
                        ProductModel databaseItem = new ProductModel(
                                // Retrieves all relevant information from firebase to store in the shared model
                                products.child("productName").getValue().toString(),
                                products.child("productPrice").getValue().toString(),
                                imageList,
                                products.child("category").getValue().toString(),
                                products.child("discount").getValue().toString(),
                                products.child("productAmount").getValue().toString(),
                                products.child("productBarcode").getValue().toString());

                        // Checks whether the product already exists, if another product exists with the same barcode
                        boolean duplicate = false;
                        for (ProductModel pm : tempFavouritesList) {
                            if (pm.getProductBarcode().equals(databaseItem.getProductBarcode())) {
                                duplicate = true;
                            }
                        }
                        if (!duplicate) {
                            tempFavouritesList.add(databaseItem);
                            userListViewModel.setFavouritesList(tempFavouritesList);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

        // Fetch data for shopping list from Firebase
        referenceShoppingList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot products : dataSnapshot.getChildren()) {
                        ProductModel databaseItem = new ProductModel(
                                // Retrieves all relevant information from firebase to store in the shared model
                                products.child("productName").getValue().toString(),
                                products.child("productPrice").getValue().toString(),
                                imageList,
                                products.child("category").getValue().toString(),
                                products.child("discount").getValue().toString(),
                                products.child("productAmount").getValue().toString(),
                                products.child("productBarcode").getValue().toString());

                        // Checks whether the product already exists, if another product exists with the same barcode
                        boolean duplicate = false;
                        for (Map.Entry<ProductModel, Integer> entry : tempShoppingList.entrySet()) {
                            ProductModel pm = entry.getKey();
                            if (pm.getProductName().equals(databaseItem.getProductName())) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (!duplicate) {
                            tempShoppingList.put(databaseItem, 1);
                            userListViewModel.setShoppingList(tempShoppingList);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Sets up buttons
        barcodeScannerButton = view.findViewById(R.id.barcode_scanner_button);
        searchView = view.findViewById(R.id.search_view);

        // Sets up the recycler view
        recyclerView = view.findViewById(R.id.main_page_recyclerview);
        adapter = new ProductRecyclerViewAdapter(requireContext(), this);
        recyclerView.setAdapter(adapter);
        if (getScreenWidth() > 1200) {
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 4);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
        }

        // Button listeners
        barcodeScannerButton.setOnClickListener(v -> {
            scanCode();
        });

        // Filters the list of products based on text entered in the searchbar
        // Only finds products if the text entered exactly matches a product name, case insensitive
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.searchProduct(newText);
                return true;
            }
        });
    }

    // Barcode scanner method
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureAct.class);

        barLauncher.launch(options);
    }

    // Barcode scanner result launcher
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            // Handle barcode scanning result
            handleBarcodeScanResult(result.getContents());
        }
    });

    // Barcode scanning result handler
    // If the product exists in the database, based on barcode, the product page of the given product gets opened
    // Else, a toast message gets shown telling the product does not exist
    private void handleBarcodeScanResult(String barcode) {
        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
        referenceStores.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot stores : dataSnapshot.getChildren()) {
                        if (stores.hasChild(barcode)) {
                            // Open product details fragment for the scanned product
                            if (getContext() != null && getContext() instanceof MainActivity) {
                                ProductModel item = new ProductModel(
                                        // Retrieves all relevant information from firebase to store in the shared model
                                        stores.child(barcode).child("productName").getValue().toString(),
                                        stores.child(barcode).child("productPrice").getValue().toString(),
                                        generateImages(),
                                        stores.child(barcode).child("category").getValue().toString(),
                                        stores.child(barcode).child("discount").getValue().toString(),
                                        stores.child(barcode).child("productAmount").getValue().toString(),
                                        stores.child(barcode).child("productBarcode").getValue().toString());
                                ((MainActivity) getContext()).replaceFragment(new ProductFragment(item, 0), getContext().getString(R.string.product_page_title));
                                break;
                            }
                        } else {
                            Toast.makeText(requireContext(), "Product is not in the database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    // Handle clicking a product
    // Opens the product page of the corresponding clicked product
    @Override
    public void onItemClick(int position) {
        ProductModel item = userListViewModel.getProductModels().getValue().get(position);
        ProductRecyclerViewAdapter.MyViewHolder viewHolder = (ProductRecyclerViewAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            // Update UI with clicked item details
            viewHolder.productImage.setImageResource(item.getProductImage(0));
            viewHolder.productName.setText(item.getProductName());
            viewHolder.productPrice.setText(item.getProductPrice());
        }
        // Replace current fragment with the product details fragment
        if (getContext() != null && getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).replaceFragment(new ProductFragment(item, position), getContext().getString(R.string.product_page_title));
        }
    }

    // Handle adding/removing from favourites list
    @Override
    public void onFavouritesClick(int position) {
        // Handle click on the favourites icon for a product
        ProductModel item = userListViewModel.getProductModels().getValue().get(position);
        referenceFavouritesList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                referenceFavouritesList.child(item.getProductBarcode()).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
                        if (!tempFavouritesList.contains(item)) {
                            // Add product to favourites list
                            tempFavouritesList.add(item);
                            userListViewModel.setFavouritesList(tempFavouritesList);
                            String message = item.getProductName();
                            // Show toast message indicating product has successfully been favourites
                            String toastMessage = getString(R.string.placeholder_toast_favourites_format, message);
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            // Remove product from favourites list
                            tempFavouritesList.remove(item);
                            userListViewModel.setFavouritesList(tempFavouritesList);
                            referenceFavouritesList.child(item.getProductBarcode()).removeValue();
                            String message = item.getProductName();
                            // Show toast message indicating product has already been favourited
                            String toastMessage = getString(R.string.placeholder_toast_unfavourites_format, message);
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    // Handle adding to shopping list
    @Override
    public void onShoppingListClick(int position) {
        // Handle click on the shopping list icon for a product
        ProductModel item = userListViewModel.getProductModels().getValue().get(position);
        referenceShoppingList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                referenceShoppingList.child(item.getProductBarcode()).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
                        if (!tempShoppingList.containsKey(item)) {
                            // Add product to shopping list
                            tempShoppingList.put(item, 1);
                            userListViewModel.setShoppingList(tempShoppingList);
                            String message = item.getProductName();
                            // Show toast message indicating product has successfully been added
                            String toastMessage = getString(R.string.placeholder_toast_shopping_list_success_format, message);
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            // Inform user that the product is already in the shopping list
                            String message = item.getProductName();
                            // Show toast message indicating product is already in shopping list
                            String toastMessage = getString(R.string.placeholder_toast_shopping_list_fail_format, message);
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
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

    // Handle orientation change
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Replace the fragment with the appropriate layout based on orientation
        getChildFragmentManager().beginTransaction()
                .replace(R.id.layout_default, new MainFragment())
                .commit();
    }

    // Method to get the screen width
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    // Method to generate dummy images
    private List<Integer> generateImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        images.add(R.drawable.bread);
        return images;
    }
}
