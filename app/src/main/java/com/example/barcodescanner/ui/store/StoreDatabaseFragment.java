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

public class StoreDatabaseFragment extends Fragment implements StoreProductRecyclerViewInterface {
    Dialog plusDialog;
    Dialog addDialog;
    Dialog editDialog;
    Dialog deleteDialog;
    Button buttonAddDatabase;
    Button buttonAddProduct;
    Button buttonCloseAdd;
    Button buttonSaveAdd;
    Button buttonCloseEdit;
    Button buttonSaveEdit;
    Button buttonCancelDelete;
    Button buttonConfirmDelete;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    private StoreProductViewModel storeProductViewModel;
    DatabaseListAdapter databaseListAdapter;

    private FragmentStoreDatabaseBinding binding;
    private StoreDatabaseViewModel storeDatabaseViewModel;
    private boolean isDataValid;
    private String selectedItem;
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

            final EditText itemName = addDialog.findViewById(R.id.edit_item_name);
            final Spinner categorySpinner = addDialog.findViewById(R.id.spinner_item_category);
            final EditText barcodeNumber = addDialog.findViewById(R.id.edit_text_barcode);
            final EditText amount = addDialog.findViewById(R.id.edit_item_amount);
            final EditText price = addDialog.findViewById(R.id.edit_item_price);
            final EditText discountEditText = addDialog.findViewById(R.id.edit_text_discount);

            String[] categories = {"Vegetables", "Fruit", "Bread", "Meat", "Snacks", "Sweets", "Drinks", "Vega(n)"};
            selectedItem = categories[0];
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(arrayAdapter);

            categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Perform action when an item is selected
                    selectedItem = (String) parent.getItemAtPosition(position);
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
                isDataValid = storeDatabaseFormState.isDataValid();

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
                    int discountText;
                    try{
                        priceText = Double.parseDouble(price.getText().toString());
                    } catch (NumberFormatException e) {
                        priceText = -1;
                    }
                    try {
                        discountText = Integer.parseInt(discountEditText.getText().toString());
                    } catch (NumberFormatException e) {
                        discountText = -1;
                    }
                    storeDatabaseViewModel.storeDatabaseDataChanged(itemName.getText().toString(),
                            barcodeNumber.getText().toString(), amount.getText().toString(), priceText, discountText);
                }
            };
            itemName.addTextChangedListener(afterTextChangedListener);
            barcodeNumber.addTextChangedListener(afterTextChangedListener);
            amount.addTextChangedListener(afterTextChangedListener);
            price.addTextChangedListener(afterTextChangedListener);
            discountEditText.addTextChangedListener(afterTextChangedListener);

            binding.floatingButtonAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    plusDialog.show();
                }
            });

            buttonAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addDialog.show();
                }
            });

            buttonCloseAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    plusDialog.dismiss();
                    addDialog.dismiss();
                }
            });

            buttonSaveAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Integer> imageList = generateImages();
                    if(!itemName.getText().toString().isEmpty() && !selectedItem.isEmpty() && !barcodeNumber.getText().toString().isEmpty() && !amount.getText().toString().isEmpty() && !price.getText().toString().isEmpty()) {
                        ProductModel infoProduct = new ProductModel(itemName.getText().toString(), price.getText().toString(), imageList, selectedItem, "discount", amount.getText().toString(), barcodeNumber.getText().toString());
                        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
                        DatabaseReference referenceStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        referenceStore.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.hasChild(barcodeNumber.getText().toString())){
                                    referenceStore.child(barcodeNumber.getText().toString()).setValue(infoProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            plusDialog.dismiss();
                                            addDialog.dismiss();

                                            // Create the Snackbar
                                            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.success_add_item), Snackbar.LENGTH_LONG);
                                            // Set the Snackbar Layout
                                            snackbar.setBackgroundTint(ContextCompat.getColor(getContext(), R.color.success_color_green));
                                            snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                                            // Show the Snackbar
                                            snackbar.show();
                                            ArrayList<ProductModel> tempProductModels = storeProductViewModel.getProductModels().getValue();
                                            tempProductModels.add(infoProduct);
                                            storeProductViewModel.setProductModels(tempProductModels);
                                            databaseListAdapter.notifyDataSetChanged();
                                        }
                                    });
                                } else{
                                    barcodeNumber.setError("Barcode can not be the same as another product.");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

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

        private void showDialog(){
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_store_database_plus);
            dialog.show();
        }

    @Override
    public void onItemClick(int position) {
        ProductModel item = storeProductViewModel.getProductModels().getValue().get(position);
        DatabaseListAdapter.ViewHolder viewHolder = (DatabaseListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            viewHolder.itemImage.setImageResource(item.getProductImage(0));
            viewHolder.itemName.setText(item.getProductName());
            viewHolder.itemCategory.setText(item.getCategory());
            viewHolder.itemPrice.setText(item.getProductPrice());
        }
        if (getContext() != null && getContext() instanceof StoreActivity) {
            ((StoreActivity) getContext()).replaceFragment(new EditProductFragment(item, position), getContext().getString(R.string.edit_product_title));
        }
    }

    @Override
    public void onDeleteClick(int position) {

        deleteDialog.show();

        buttonCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        buttonConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteDialog.dismiss();

                ProductModel item = storeProductViewModel.getProductModels().getValue().get(position);
                DatabaseListAdapter.ViewHolder viewHolder = (DatabaseListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                if (viewHolder != null) {
                    viewHolder.itemImage.setImageResource(item.getProductImage(0));
                    viewHolder.itemName.setText(item.getProductName());
                    viewHolder.itemCategory.setText(item.getCategory());
                    viewHolder.itemPrice.setText(item.getProductPrice());
                }
                ArrayList<ProductModel> tempProductModels = storeProductViewModel.getProductModels().getValue();
                tempProductModels.remove(position);
                storeProductViewModel.setProductModels(tempProductModels);


                databaseListAdapter.notifyItemRemoved(position);
                databaseListAdapter.notifyItemRangeChanged(position, databaseListAdapter.getItemCount());
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference removalProduct = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductName());
                removalProduct.getRef().removeValue();
            }
        });

        DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
        DatabaseReference referenceStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void onEditClick(int position) {
        editDialog.show();

        final EditText nameEditText = editDialog.findViewById(R.id.edit_item_name);
        final Spinner editCategorySpinner = editDialog.findViewById(R.id.spinner_item_category);
        final EditText barcodeEditText = editDialog.findViewById(R.id.edit_text_barcode);
        final EditText amountEditText = editDialog.findViewById(R.id.edit_item_amount);
        final EditText priceEditText = editDialog.findViewById(R.id.edit_item_price);
        final EditText discountEditText = editDialog.findViewById(R.id.edit_text_discount);

        String[] categories = {"Vegetables", "Fruit", "Bread", "Meat", "Snacks", "Sweets", "Drinks", "Vega(n)"};
        selectedItem = categories[0];
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCategorySpinner.setAdapter(arrayAdapter);

        editCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Perform action when an item is selected
                selectedEditItem = (String) parent.getItemAtPosition(position);
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
            isDataValid = storeDatabaseFormState.isDataValid();

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
                int discountText;
                try{
                    priceText = Double.parseDouble(priceEditText.getText().toString());
                } catch (NumberFormatException e) {
                    priceText = -1;
                }
                try {
                    discountText = Integer.parseInt(discountEditText.getText().toString());
                } catch (NumberFormatException e) {
                    discountText = -1;
                }
                storeDatabaseViewModel.storeDatabaseDataChanged(nameEditText.getText().toString(),
                        barcodeEditText.getText().toString(), amountEditText.getText().toString(), priceText, discountText);
            }
        };
        nameEditText.addTextChangedListener(afterTextChangedListener);
        barcodeEditText.addTextChangedListener(afterTextChangedListener);
        amountEditText.addTextChangedListener(afterTextChangedListener);
        priceEditText.addTextChangedListener(afterTextChangedListener);
        discountEditText.addTextChangedListener(afterTextChangedListener);

        buttonCloseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });

        buttonSaveEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProductModel item = storeProductViewModel.getProductModels().getValue().get(position);

                DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference removalProduct = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductBarcode());
                DatabaseReference referenceStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                referenceStore.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(barcodeEditText.getText().toString()) && !item.getProductBarcode().equals(barcodeEditText.getText().toString())){
                            removalProduct.getRef().removeValue();

                            item.setProductName(nameEditText.getText().toString());
                            item.setCategory(selectedEditItem);
                            item.setProductBarcode(barcodeEditText.getText().toString());
                            item.setProductAmount(amountEditText.getText().toString());
                            item.setProductPrice(priceEditText.getText().toString());

                            storeProductViewModel.setProductModel(position, item);

                            referenceStore.child(barcodeEditText.getText().toString()).setValue(item);

                            databaseListAdapter.notifyDataSetChanged();

                            editDialog.dismiss();

                        } else{
                            barcodeEditText.setError("Barcode can not be the same as another product.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }
}