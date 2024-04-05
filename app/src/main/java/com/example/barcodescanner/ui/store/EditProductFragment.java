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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;
import com.example.barcodescanner.databinding.FragmentEditProductBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditProductFragment extends Fragment {

    Dialog editDialog; // Dialog for editing product
    Button buttonCloseEdit; // Button to close edit dialog
    Button buttonSaveEdit; // Button to save edit dialog

    Dialog deleteDialog; // Dialog for deleting product
    Button buttonCancelDelete; // Button to cancel delete dialog
    Button buttonConfirmDelete; // Button to confirm delete dialog
    ProductModel item; // Product to be edited
    int position; // Position of the product
    List<Integer> imageList; // List of images for the product
    private StoreDatabaseViewModel storeDatabaseViewModel; // ViewModel for store database

    private StoreProductViewModel storeProductViewModel; // ViewModel for store product

    private FragmentEditProductBinding binding; // Binding for fragment
    private boolean isDataValid; // Flag to check if data is valid

    private String selectedItem; // Selected category of the product

    /**
     * Constructor for EditProductFragment
     *
     * @param item     The product to be edited
     * @param position Position of the product
     */
    public EditProductFragment(ProductModel item, int position) {
        this.item = item; // Initializing product
        this.position = position; // Initializing position
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeDatabaseViewModel = new StoreDatabaseViewModel(); // Initializing ViewModel
        isDataValid = false; // Setting data validity flag to false
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment using data binding
        binding = FragmentEditProductBinding.inflate(inflater, container, false);

        storeProductViewModel = ((StoreActivity) getActivity()).getStoreProductViewModel(); // Getting ViewModel from activity

        binding.imageMain.setImageResource(item.getProductImage(0)); // Setting main product image

        imageList = item.getProductImageList(); // Getting product image list
        RecyclerView recyclerView = binding.editProductImages; // Getting RecyclerView for product images
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); // Creating LinearLayoutManager
        recyclerView.setLayoutManager(linearLayoutManager); // Setting layout manager for RecyclerView
        ImageListAdapter imageListAdapter = new ImageListAdapter(imageList); // Creating adapter for images
        recyclerView.setAdapter(imageListAdapter); // Setting adapter for RecyclerView

        binding.storeProductName.setText(item.getProductName()); // Setting product name
        binding.storeProductPrice.setText(item.getProductPrice()); // Setting product price
        binding.storeProductCategory.setText(item.getCategory()); // Setting product category

        return binding.getRoot(); // Returning root view
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageButton backButton = view.findViewById(R.id.button_back); // Back button
        final Button addPicturesButton = view.findViewById(R.id.button_product_add_pictures); // Add pictures button
        final Button editProductButton = view.findViewById(R.id.button_product_edit); // Edit product button
        final ImageButton deleteProductButton = view.findViewById(R.id.product_delete_icon); // Delete product button

        // Initializing edit dialog
        editDialog = new Dialog(getContext());
        editDialog.setContentView(R.layout.dialog_store_database_add); // Setting layout for edit dialog
        editDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); // Setting dialog window layout parameters
        Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background); // Setting dialog background
        editDialog.getWindow().setBackgroundDrawable(background); // Setting dialog background drawable
        editDialog.setCancelable(true); // Setting dialog cancelable
        buttonCloseEdit = editDialog.findViewById(R.id.edit_product_cancel); // Close edit dialog button
        buttonSaveEdit = editDialog.findViewById(R.id.edit_product_confirm); // Save edit dialog button

        // Initializing delete dialog
        deleteDialog = new Dialog(getContext());
        deleteDialog.setContentView(R.layout.dialog_delete_product_box); // Setting layout for delete dialog
        deleteDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); // Setting dialog window layout parameters
        deleteDialog.getWindow().setBackgroundDrawable(background); // Setting dialog background drawable
        deleteDialog.setCancelable(true); // Setting dialog cancelable
        buttonConfirmDelete = deleteDialog.findViewById(R.id.delete_product_dialog_button_confirm); // Confirm delete button
        buttonCancelDelete = deleteDialog.findViewById(R.id.delete_product_dialog_button_cancel); // Cancel delete button

        final EditText nameEditText = editDialog.findViewById(R.id.edit_item_name); // EditText for product name
        final Spinner categorySpinner = editDialog.findViewById(R.id.spinner_item_category); // Spinner for product category
        final EditText barcodeEditText = editDialog.findViewById(R.id.edit_text_barcode); // EditText for product barcode
        final EditText amountEditText = editDialog.findViewById(R.id.edit_item_amount); // EditText for product amount
        final EditText priceEditText = editDialog.findViewById(R.id.edit_item_price); // EditText for product price
        final EditText discountEditText = editDialog.findViewById(R.id.edit_text_discount); // EditText for product discount

        String[] categories = {"Vegetables", "Fruit", "Bread", "Meat", "Snacks", "Sweets", "Drinks", "Vega(n)"}; // Categories for spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories); // Creating array adapter for spinner
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Setting drop down view resource for spinner
        categorySpinner.setAdapter(arrayAdapter); // Setting adapter for spinner

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Perform action when an item is selected
                selectedItem = (String) parent.getItemAtPosition(position); // Getting selected item from spinner
                Toast.makeText(getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show(); // Displaying selected item in toast
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        storeDatabaseViewModel.getStoreDatabaseFormState().observe(getViewLifecycleOwner(), storeDatabaseFormState -> {
            if (storeDatabaseFormState == null) {
                return;
            }
            isDataValid = storeDatabaseFormState.isDataValid(); // Getting data validity status

            if (storeDatabaseFormState.getNameError() != null) {
                nameEditText.setError(getString(storeDatabaseFormState.getNameError())); // Setting error for name EditText
            }
            if (storeDatabaseFormState.getBarcodeError() != null) {
                barcodeEditText.setError(getString(storeDatabaseFormState.getBarcodeError())); // Setting error for barcode EditText
            }
            if (storeDatabaseFormState.getAmountError() != null) {
                amountEditText.setError(getString(storeDatabaseFormState.getAmountError())); // Setting error for amount EditText
            }
            if (storeDatabaseFormState.getPriceError() != null) {
                priceEditText.setError(getString(storeDatabaseFormState.getPriceError())); // Setting error for price EditText
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                double priceText;
                try {
                    priceText = Double.parseDouble(priceEditText.getText().toString()); // Parsing price to double
                } catch (NumberFormatException e) {
                    priceText = -1; // Setting price to -1 if parsing fails
                }
                storeDatabaseViewModel.storeDatabaseDataChanged(nameEditText.getText().toString(), // Storing changed data in ViewModel
                        barcodeEditText.getText().toString(), amountEditText.getText().toString(), priceText);
            }
        };
        nameEditText.addTextChangedListener(afterTextChangedListener); // Adding text changed listener for name EditText
        barcodeEditText.addTextChangedListener(afterTextChangedListener); // Adding text changed listener for barcode EditText
        amountEditText.addTextChangedListener(afterTextChangedListener); // Adding text changed listener for amount EditText
        priceEditText.addTextChangedListener(afterTextChangedListener); // Adding text changed listener for price EditText
        discountEditText.addTextChangedListener(afterTextChangedListener); // Adding text changed listener for discount EditText

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to StoreDatabaseFragment on back button click
                if (view.getContext() != null && view.getContext() instanceof StoreActivity) {
                    ((StoreActivity) view.getContext()).replaceFragment(new StoreDatabaseFragment(), view.getContext().getString(R.string.edit_product_title));
                }
            }
        });

        addPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add picture functionality
            }
        });

        editProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show edit dialog on edit product button click
                editDialog.show();
            }
        });

        buttonCloseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close edit dialog on close button click
                editDialog.dismiss();
            }
        });

        buttonSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save edited product details on save button click
                DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores"); // Getting reference to Firebase database
                DatabaseReference removalProduct = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductBarcode()); // Getting reference to the product to be edited

                DatabaseReference referenceStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid()); // Getting reference to the store

                referenceStore.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(barcodeEditText.getText().toString()) || item.getProductBarcode().equals(barcodeEditText.getText().toString())) {

                            removalProduct.getRef().removeValue(); // Removing old product from database

                            // Updating product details
                            item.setProductName(nameEditText.getText().toString());
                            item.setCategory(selectedItem);
                            item.setProductBarcode(barcodeEditText.getText().toString());
                            item.setProductAmount(amountEditText.getText().toString());
                            item.setProductPrice(priceEditText.getText().toString());
                            try {
                                item.setDiscount(discountEditText.getText().toString());
                                storeProductViewModel.setProductModel(position, item);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            storeProductViewModel.setProductModel(position, item); // Setting updated product details to ViewModel

                            referenceStore.child(barcodeEditText.getText().toString()).setValue(item); // Saving updated product details to database

                            // Updating UI with new product details
                            binding.storeProductName.setText(item.getProductName());
                            binding.storeProductPrice.setText(item.getProductPrice());
                            binding.storeProductCategory.setText(item.getCategory());

                            editDialog.dismiss(); // Dismissing edit dialog
                        } else {
                            barcodeEditText.setError("Barcode can not be the same as another product."); // Displaying error if barcode is not unique
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled event
                    }
                });
            }
        });

        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show delete dialog on delete product button click
                deleteDialog.show();
            }
        });

        buttonCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close delete dialog on cancel button click
                deleteDialog.dismiss();
            }
        });

        buttonConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete product on confirm delete button click
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores"); // Getting reference to Firebase database
                DatabaseReference removalProduct = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductBarcode()); // Getting reference to the product to be deleted
                removalProduct.getRef().removeValue(); // Removing product from database
                deleteDialog.dismiss(); // Dismissing delete dialog

                // Navigate back to StoreDatabaseFragment after product deletion
                if (view.getContext() != null && view.getContext() instanceof StoreActivity) {
                    ((StoreActivity) view.getContext()).replaceFragment(new StoreDatabaseFragment(), view.getContext().getString(R.string.edit_product_title));
                }

                ArrayList<ProductModel> tempProductModels = storeProductViewModel.getProductModels().getValue(); // Getting list of products from ViewModel
                tempProductModels.remove(position); // Removing deleted product from the list
                storeProductViewModel.setProductModels(tempProductModels); // Setting updated list of products to ViewModel
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clearing binding reference
    }
}
