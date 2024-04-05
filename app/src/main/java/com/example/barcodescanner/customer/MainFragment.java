package com.example.barcodescanner.customer;

import android.content.Intent;
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
import androidx.annotation.Nullable;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ProductRecyclerViewInterface {

    ImageButton barcodeScannerButton;
    //    Button order_1;
//    Button order_2;
//    Button order_3;
//    Button order_4;
//    Button filter_1;
//    Button filter_2;
//    Button filter_reset;
    RecyclerView recyclerView;
    SearchView searchView;
    ProductRecyclerViewAdapter adapter;
    private SharedViewModel sharedViewModel;
    private UserListViewModel userListViewModel;
    DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference referenceCustomers = referenceUsers.child("Customers");
    DatabaseReference referenceCurrentUser = referenceCustomers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    DatabaseReference referenceShoppingList = referenceCurrentUser.child("Shopping List");
    DatabaseReference referenceFavouritesList = referenceCurrentUser.child("Favourites List");

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedViewModel = ((MainActivity) getActivity()).getSharedViewModel();
        userListViewModel = ((MainActivity) getActivity()).getUserListViewModel();
        userListViewModel.setProductModels(sharedViewModel.getProductModels().getValue());
        List<Integer> imageList = generateImages();
        referenceFavouritesList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot products : dataSnapshot.getChildren()) {
                        ProductModel databaseItem = new ProductModel(products.child("productName").getValue().toString(), products.child("productPrice").getValue().toString(), imageList, products.child("category").getValue().toString(), products.child("discount").getValue().toString(), products.child("productAmount").getValue().toString(), products.child("productBarcode").getValue().toString());
                        if (!tempFavouritesList.contains(databaseItem)) {
                            tempFavouritesList.add(databaseItem);
                            userListViewModel.setFavouritesList(tempFavouritesList);
                        }}}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceShoppingList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot products : dataSnapshot.getChildren()) {
                        ProductModel databaseItem = new ProductModel(products.child("productName").getValue().toString(), products.child("productPrice").getValue().toString(),imageList, products.child("category").getValue().toString(), products.child("discount").getValue().toString(), products.child("productAmount").getValue().toString(), products.child("productBarcode").getValue().toString());
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

                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
//        order_1 = view.findViewById(R.id.order_1);
//        order_2 = view.findViewById(R.id.order_2);
//        order_3 = view.findViewById(R.id.order_3);
//        order_4 = view.findViewById(R.id.order_4);
//        filter_1 = view.findViewById(R.id.filter_1);
//        filter_2 = view.findViewById(R.id.filter_2);
//        filter_reset = view.findViewById(R.id.filter_reset);
        searchView = view.findViewById(R.id.search_view);

        // Sets up the recycler view
        recyclerView = view.findViewById(R.id.main_page_recyclerview);
//        setupProductModels();
        adapter = new ProductRecyclerViewAdapter(requireContext(),
                this);
        recyclerView.setAdapter(adapter);
        if(getScreenWidth() > 1200) {
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 4);
            recyclerView.setLayoutManager(layoutManager);
        }else {
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
        }

        // Buttons listeners
        barcodeScannerButton.setOnClickListener(v -> {
            scanCode();
        });

//        order_1.setOnClickListener(v -> {
//            adapter.sortBy("name_ascending");
//        });
//
//        order_2.setOnClickListener(v -> {
//            adapter.sortBy("name_descending");
//        });
//
//        order_3.setOnClickListener(v -> {
//            adapter.sortBy("price_ascending");
//        });
//
//        order_4.setOnClickListener(v -> {
//            adapter.sortBy("price_descending");
//        });
//
//        filter_1.setOnClickListener(v -> {
//            adapter.filterBy("food");
//        });
//
//        filter_2.setOnClickListener(v -> {
//            adapter.filterBy("drink");
//        });
//
//        filter_reset.setOnClickListener(v -> {
//            adapter.filterBy("reset");
//        });

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Check if the fragment is being destroyed due to a configuration change
        boolean isBeingRecreated = getActivity().isChangingConfigurations();

        // Check if the fragment is being removed from the back stack
        boolean isBeingRemoved = !isBeingRecreated && !getActivity().isFinishing();

        // If neither of the above cases, call resetFilter
        if (isBeingRemoved) {
            if (sharedViewModel.isFiltered) {
                sharedViewModel.isFiltered = false;
            } else {
                sharedViewModel.resetFilter();
            }
        }
    }

    // Barcode scanner shenanigans
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureAct.class);

        barLauncher.launch(options);
    }

    // Barcode scanner shenanigans part 2
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
            referenceStores.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()){
                        for (DataSnapshot stores : dataSnapshot.getChildren()) {
                            if(stores.hasChild(result.getContents())){
                                if (getContext() != null && getContext() instanceof MainActivity) {
                                    ProductModel item = new ProductModel(stores.child(result.getContents()).child("productName").getValue().toString(), stores.child(result.getContents()).child("productPrice").getValue().toString(), generateImages(), stores.child(result.getContents()).child("category").getValue().toString(), stores.child(result.getContents()).child("discount").getValue().toString(), stores.child(result.getContents()).child("productAmount").getValue().toString(), stores.child(result.getContents()).child("productBarcode").getValue().toString());
                                    ((MainActivity) getContext()).replaceFragment(new ProductFragment(item, 0), getContext().getString(R.string.product_page_title));
                                    break;
                                }
                            }else{
                                Toast.makeText(requireContext(), "Product is not on database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    /*        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();*/
        }


    });

    // Barcode scanner shenanigans part 3
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                Toast.makeText(requireContext(), "Scanned code: " + contents, Toast.LENGTH_SHORT).show();
                DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
                referenceStores.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            for (DataSnapshot stores : dataSnapshot.getChildren()) {
                                if(stores.hasChild(contents)){
                                    if (getContext() != null && getContext() instanceof MainActivity) {
                                        ProductModel item = new ProductModel(stores.child(contents).child("productName").toString(), stores.child(contents).child("productPrice").toString(), generateImages(), stores.child(contents).child("category").toString(), stores.child(contents).child("discount").toString(), stores.child(contents).child("productAmount").toString(), stores.child(contents).child("productBarcode").toString());
                                        ((MainActivity) getContext()).replaceFragment(new ProductFragment(item, 0), getContext().getString(R.string.product_page_title));
                                        break;
                                    }
                                }
                                }
                            Toast.makeText(requireContext(), "Product is not on database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {
                Toast.makeText(requireContext(), "Scan cancelled", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Placeholder code for clicking on recyclerview elements
    @Override
    public void onItemClick(int position) {
        ProductModel item = userListViewModel.getProductModels().getValue().get(position);
        ProductRecyclerViewAdapter.MyViewHolder viewHolder = (ProductRecyclerViewAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            viewHolder.productImage.setImageResource(item.getProductImage(0));
            viewHolder.productName.setText(item.getProductName());
            viewHolder.productPrice.setText(item.getProductPrice());
        }
        if (getContext() != null && getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).replaceFragment(new ProductFragment(item, position), getContext().getString(R.string.product_page_title));
        }
    }

    // Same code as onItemClick
    @Override
    public void onFavouritesClick(int position) {


        ProductModel item = userListViewModel.getProductModels().getValue().get(position);
        referenceFavouritesList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                referenceFavouritesList.child(item.getProductBarcode()).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ArrayList<ProductModel> tempFavouritesList = userListViewModel.getFavouritesList().getValue();
                        String[] toastMessages = requireContext().getResources().getStringArray(R.array.placeholder_main_page_product);
                        if (!(position >= 0 && position < toastMessages.length)) {
                            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!tempFavouritesList.contains(item)) {
                            tempFavouritesList.add(item);
                            userListViewModel.setFavouritesList(tempFavouritesList);

                            String message = item.getProductName();
                            String toastMessage = getString(R.string.placeholder_toast_favourites_format, message);
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            tempFavouritesList.remove(item);
                            userListViewModel.setFavouritesList(tempFavouritesList);
                            referenceFavouritesList.child(item.getProductBarcode()).removeValue();

                            String message = item.getProductName();
                            String toastMessage = getString(R.string.placeholder_toast_unfavourites_format, message);
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // Same code as onItemClick
    @Override
    public void onShoppingListClick(int position) {
        ProductModel item = userListViewModel.getProductModels().getValue().get(position);

        referenceShoppingList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                referenceShoppingList.child(item.getProductBarcode()).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Map<ProductModel, Integer> tempShoppingList = userListViewModel.getShoppingList().getValue();
                        String[] toastMessages = requireContext().getResources().getStringArray(R.array.placeholder_main_page_product);
                        if (!(position >= 0 && position < toastMessages.length)) {
                            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        boolean duplicate = false;
                        for (Map.Entry<ProductModel, Integer> entry : tempShoppingList.entrySet()) {
                            ProductModel pm = entry.getKey();
                            if (pm.getProductName().equals(item.getProductName())) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (!duplicate) {
                            tempShoppingList.put(item, 1);
                            userListViewModel.setShoppingList(tempShoppingList);
                            String message = item.getProductName();
                            String toastMessage = getString(R.string.placeholder_toast_shopping_list_success_format, message);
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            String message = item.getProductName();
                            String toastMessage = getString(R.string.placeholder_toast_shopping_list_fail_format, message);
                            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    //Changing rotation
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Replace the current fragment with the landscape version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new MainFragment())
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Replace the current fragment with the portrait version
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.layout_default, new MainFragment())
                    .commit();
        }
    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

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