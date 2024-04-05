package com.example.barcodescanner.ui.store;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;
import com.example.barcodescanner.databinding.FragmentStoreDatabaseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment responsible for managing the store database, including adding, editing, and deleting products.
 */
public class StoreDatabaseFragment extends Fragment implements StoreProductRecyclerViewInterface {
    // Dialogs for various actions
    Dialog plusDialog, addDialog, editDialog, deleteDialog;

    // Buttons for dialog actions
    Button buttonAddDatabase, buttonAddProduct, buttonCloseAdd, buttonSaveAdd, buttonCloseEdit, buttonSaveEdit, buttonCancelDelete, buttonConfirmDelete;

    // Firebase authentication instance
    FirebaseAuth mAuth;

    // RecyclerView for displaying products
    RecyclerView recyclerView;

    // ViewModel for managing store products
    private StoreProductViewModel storeProductViewModel;

    // Adapter for the RecyclerView
    DatabaseListAdapter databaseListAdapter;

    // View binding instance
    private FragmentStoreDatabaseBinding binding;

    // ViewModel for managing store database
    private StoreDatabaseViewModel storeDatabaseViewModel;

    // Flag to track whether data is valid
    private boolean isDataValid;

    // Selected item in spinner
    private String selectedItem;

    // Selected item in edit spinner
    private String selectedEditItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeDatabaseViewModel = new StoreDatabaseViewModel();
        isDataValid = false;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        storeProductViewModel = ((StoreActivity) getActivity()).getStoreProductViewModel();
        binding = FragmentStoreDatabaseBinding.inflate(inflater, container, false);

        recyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        databaseListAdapter = new DatabaseListAdapter(requireContext(), this);
        recyclerView.setAdapter(databaseListAdapter);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initializing plus dialog
        plusDialog = new Dialog(getContext());
        plusDialog.setContentView(R.layout.dialog_store_database_plus);
        plusDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable plusDialogBackground = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
        plusDialog.getWindow().setBackgroundDrawable(plusDialogBackground);
        plusDialog.setCancelable(true);
        buttonAddDatabase = plusDialog.findViewById(R.id.button_upload_database);
        buttonAddProduct = plusDialog.findViewById(R.id.button_add_item);

        // Initializing add dialog
        addDialog = new Dialog(getContext());
        addDialog.setContentView(R.layout.dialog_store_database_add);
        addDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable addDialogBackground = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
        addDialog.getWindow().setBackgroundDrawable(addDialogBackground);
        addDialog.setCancelable(true);
        buttonCloseAdd =  addDialog.findViewById(R.id.edit_product_cancel);
        buttonSaveAdd =  addDialog.findViewById(R.id.edit_product_confirm);

        //Initializing edit dialog
        editDialog = new Dialog(getContext());
        editDialog.setContentView(R.layout.dialog_store_database_add);
        editDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
        editDialog.getWindow().setBackgroundDrawable(background);
        editDialog.setCancelable(true);
        buttonCloseEdit =  editDialog.findViewById(R.id.edit_product_cancel);
        buttonSaveEdit =  editDialog.findViewById(R.id.edit_product_confirm);

        // Initializing delete prompt dialog
        deleteDialog = new Dialog(getContext());
        deleteDialog.setContentView(R.layout.dialog_delete_product_box);
        deleteDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable deleteDialogBackground = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
        deleteDialog.getWindow().setBackgroundDrawable(deleteDialogBackground);
        deleteDialog.setCancelable(true);
        buttonConfirmDelete = deleteDialog.findViewById(R.id.delete_product_dialog_button_confirm);
        buttonCancelDelete = deleteDialog.findViewById(R.id.delete_product_dialog_button_cancel);

        // EditText and Spinner fields in the add dialog
        final EditText itemName = addDialog.findViewById(R.id.edit_item_name);
        final Spinner categorySpinner = addDialog.findViewById(R.id.spinner_item_category);
        final EditText barcodeNumber = addDialog.findViewById(R.id.edit_text_barcode);
        final EditText amount = addDialog.findViewById(R.id.edit_item_amount);
        final EditText price = addDialog.findViewById(R.id.edit_item_price);
        final EditText discountEditText = addDialog.findViewById(R.id.edit_text_discount);

        // Setting up the spinner with categories
        String[] categories = {"Vegetables", "Fruit", "Bread", "Meat", "Snacks", "Sweets", "Drinks", "Vega(n)"};
        selectedItem = categories[0];
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);

        // Spinner item selection listener
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update the selected item when an item is selected
                selectedItem = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });

        // Observing form state changes in the ViewModel
        storeDatabaseViewModel.getStoreDatabaseFormState().observe(getViewLifecycleOwner(), storeDatabaseFormState -> {
            if (storeDatabaseFormState == null) {
                return;
            }
            isDataValid = storeDatabaseFormState.isDataValid();

            // Displaying error messages if any field is invalid
            if (storeDatabaseFormState.getNameError() != null) {
                itemName.setError(getString(storeDatabaseFormState.getNameError()));
            }
            if (storeDatabaseFormState.getBarcodeError() != null) {
                barcodeNumber.setError(getString(storeDatabaseFormState.getBarcodeError()));
            }
            if (storeDatabaseFormState.getAmountError() != null) {
                amount.setError(getString(storeDatabaseFormState.getAmountError()));
            }
            if (storeDatabaseFormState.getPriceError() != null) {
                price.setError(getString(storeDatabaseFormState.getPriceError()));
            }
        });

        // TextWatcher to validate data as user types
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                double priceText;
                try{
                    priceText = Double.parseDouble(price.getText().toString());
                } catch (NumberFormatException e) {
                    priceText = -1;
                }
                // Validate data in the ViewModel
                storeDatabaseViewModel.storeDatabaseDataChanged(itemName.getText().toString(),
                        barcodeNumber.getText().toString(), amount.getText().toString(), priceText);
            }
        };
        // Attach the TextWatcher to EditText fields
        itemName.addTextChangedListener(afterTextChangedListener);
        barcodeNumber.addTextChangedListener(afterTextChangedListener);
        amount.addTextChangedListener(afterTextChangedListener);
        price.addTextChangedListener(afterTextChangedListener);
        discountEditText.addTextChangedListener(afterTextChangedListener);

        // Click listener for adding a product
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog.show();
            }
        });

        // Click listener for closing add dialog
        buttonCloseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusDialog.dismiss();
                addDialog.dismiss();
            }
        });

        // Click listener for saving a new product
        buttonSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate fields before saving
                if(!itemName.getText().toString().isEmpty() && !selectedItem.isEmpty() && !barcodeNumber.getText().toString().isEmpty() && !amount.getText().toString().isEmpty() && !price.getText().toString().isEmpty()) {
                    // Generate a list of dummy images
                    List<Integer> imageList = generateImages();
                    // Create a new product model
                    ProductModel infoProduct = new ProductModel(itemName.getText().toString(), price.getText().toString(), imageList, selectedItem, "discount", amount.getText().toString(), barcodeNumber.getText().toString());
                    // Get a reference to the Firebase database
                    DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
                    DatabaseReference referenceStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    referenceStore.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.hasChild(barcodeNumber.getText().toString())){
                                // Add the product to the database
                                referenceStore.child(barcodeNumber.getText().toString()).setValue(infoProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        plusDialog.dismiss();
                                        addDialog.dismiss();

                                        // Display a success message
                                        Snackbar snackbar = Snackbar.make(getView(), getString(R.string.success_add_item), Snackbar.LENGTH_LONG);
                                        snackbar.setBackgroundTint(ContextCompat.getColor(getContext(), R.color.success_color_green));
                                        snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                                        snackbar.show();

                                        // Update the list of products
                                        ArrayList<ProductModel> tempProductModels = storeProductViewModel.getProductModels().getValue();
                                        tempProductModels.add(infoProduct);
                                        storeProductViewModel.setProductModels(tempProductModels);
                                        databaseListAdapter.notifyDataSetChanged();
                                    }
                                });
                            } else{
                                barcodeNumber.setError("Barcode cannot be the same as another product.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle cancellation
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Method to generate a list of dummy images for the product.
     *
     * @return List of image resources
     */
    private List<Integer> generateImages() {
        List<Integer> images = new ArrayList<>();
        // Add dummy image resources to the list
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

    /**
     * Method to show a dialog.
     */
    private void showDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_store_database_plus);
        dialog.show();
    }

    @Override
    public void onItemClick(int position) {
        // Handle item click event
        ProductModel item = storeProductViewModel.getProductModels().getValue().get(position);
        DatabaseListAdapter.ViewHolder viewHolder = (DatabaseListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            // Update UI elements with product information
            viewHolder.itemImage.setImageResource(item.getProductImage(0));
            viewHolder.itemName.setText(item.getProductName());
            viewHolder.itemCategory.setText(item.getCategory());
            viewHolder.itemPrice.setText(item.getProductPrice());
        }
        if (getContext() != null && getContext() instanceof StoreActivity) {
            // Replace fragment with edit product fragment
            ((StoreActivity) getContext()).replaceFragment(new EditProductFragment(item, position), getContext().getString(R.string.edit_product_title));
        }
    }

    @Override
    public void onDeleteClick(int position) {
        // Handle delete button click event
        deleteDialog.show();

        // Click listener for canceling delete operation
        buttonCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        // Click listener for confirming delete operation
        buttonConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the delete dialog
                deleteDialog.dismiss();

                // Get the product to be deleted
                ProductModel item = storeProductViewModel.getProductModels().getValue().get(position);
                // Update UI elements with product information
                DatabaseListAdapter.ViewHolder viewHolder = (DatabaseListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                if (viewHolder != null) {
                    viewHolder.itemImage.setImageResource(item.getProductImage(0));
                    viewHolder.itemName.setText(item.getProductName());
                    viewHolder.itemCategory.setText(item.getCategory());
                    viewHolder.itemPrice.setText(item.getProductPrice());
                }
                // Remove the product from the list
                ArrayList<ProductModel> tempProductModels = storeProductViewModel.getProductModels().getValue();
                tempProductModels.remove(position);
                storeProductViewModel.setProductModels(tempProductModels);

                // Notify adapter about item removal
                databaseListAdapter.notifyItemRemoved(position);
                databaseListAdapter.notifyItemRangeChanged(position, databaseListAdapter.getItemCount());

                // Get a reference to the Firebase database
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference removalProduct = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductBarcode());
                // Remove the product from the database
                removalProduct.getRef().removeValue();
            }
        });

        // Get a reference to the Firebase database
        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
        DatabaseReference referenceStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void onEditClick(int position) {
        // Handle edit button click event
        editDialog.show();

        // EditText and Spinner fields in the edit dialog
        final EditText nameEditText = editDialog.findViewById(R.id.edit_item_name);
        final Spinner editCategorySpinner = editDialog.findViewById(R.id.spinner_item_category);
        final EditText barcodeEditText = editDialog.findViewById(R.id.edit_text_barcode);
        final EditText amountEditText = editDialog.findViewById(R.id.edit_item_amount);
        final EditText priceEditText = editDialog.findViewById(R.id.edit_item_price);
        final EditText discountEditText = editDialog.findViewById(R.id.edit_text_discount);

        // Setting up the spinner with categories
        String[] categories = {"Vegetables", "Fruit", "Bread", "Meat", "Snacks", "Sweets", "Drinks", "Vega(n)"};
        selectedItem = categories[0];
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCategorySpinner.setAdapter(arrayAdapter);

        // Spinner item selection listener
        editCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update the selected item when an item is selected
                selectedEditItem = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });

        // Observing form state changes in the ViewModel
        storeDatabaseViewModel.getStoreDatabaseFormState().observe(getViewLifecycleOwner(), storeDatabaseFormState -> {
            if (storeDatabaseFormState == null) {
                return;
            }
            isDataValid = storeDatabaseFormState.isDataValid();

            // Displaying error messages if any field is invalid
            if (storeDatabaseFormState.getNameError() != null) {
                nameEditText.setError(getString(storeDatabaseFormState.getNameError()));
            }
            if (storeDatabaseFormState.getBarcodeError() != null) {
                barcodeEditText.setError(getString(storeDatabaseFormState.getBarcodeError()));
            }
            if (storeDatabaseFormState.getAmountError() != null) {
                amountEditText.setError(getString(storeDatabaseFormState.getAmountError()));
            }
            if (storeDatabaseFormState.getPriceError() != null) {
                priceEditText.setError(getString(storeDatabaseFormState.getPriceError()));
            }
        });

        // TextWatcher to validate data as user types
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                double priceText;
                try{
                    priceText = Double.parseDouble(priceEditText.getText().toString());
                } catch (NumberFormatException e) {
                    priceText = -1;
                }
                // Validate data in the ViewModel
                storeDatabaseViewModel.storeDatabaseDataChanged(nameEditText.getText().toString(),
                        barcodeEditText.getText().toString(), amountEditText.getText().toString(), priceText);
            }
        };
        // Attach the TextWatcher to EditText fields
        nameEditText.addTextChangedListener(afterTextChangedListener);
        barcodeEditText.addTextChangedListener(afterTextChangedListener);
        amountEditText.addTextChangedListener(afterTextChangedListener);
        priceEditText.addTextChangedListener(afterTextChangedListener);
        discountEditText.addTextChangedListener(afterTextChangedListener);

        // Click listener for closing edit dialog
        buttonCloseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });

        // Click listener for saving edited product
        buttonSaveEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Get the product to be edited
                ProductModel item = storeProductViewModel.getProductModels().getValue().get(position);

                // Get references to Firebase database
                DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference removalProduct = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductBarcode());
                DatabaseReference referenceStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                // Check if the new barcode is unique or same as the current one
                referenceStore.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(barcodeEditText.getText().toString()) || item.getProductBarcode().equals(barcodeEditText.getText().toString())){
                            // If barcode is unique or unchanged, update the product
                            removalProduct.getRef().removeValue();

                            // Update product information
                            item.setProductName(nameEditText.getText().toString());
                            item.setCategory(selectedEditItem);
                            item.setProductBarcode(barcodeEditText.getText().toString());
                            item.setProductAmount(amountEditText.getText().toString());
                            item.setProductPrice(priceEditText.getText().toString());
                            try {
                                item.setDiscount(discountEditText.getText().toString());
                                storeProductViewModel.setProductModel(position, item);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            // Update product in the database
                            referenceStore.child(barcodeEditText.getText().toString()).setValue(item);

                            // Notify adapter about data change
                            databaseListAdapter.notifyDataSetChanged();

                            // Dismiss the edit dialog
                            editDialog.dismiss();

                        } else{
                            barcodeEditText.setError("Barcode cannot be the same as another product.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle cancellation
                    }
                });
            }
        });

    }
}
