package com.example.barcodescanner.ui.store;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.ProductModel;
import com.example.barcodescanner.databinding.FragmentStoreDatabaseBinding;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class StoreDatabaseFragment extends Fragment {

    List<ProductModel> itemList;
    Dialog plusDialog;
    Dialog addDialog;
    Button buttonAddDatabase;
    Button buttonAddProduct;
    ImageButton buttonCloseAdd;
    Button buttonSaveAdd;

    private FragmentStoreDatabaseBinding binding;
    private StoreDatabaseViewModel storeDatabaseViewModel;
    private boolean isDataValid;

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

        binding = FragmentStoreDatabaseBinding.inflate(inflater, container, false);

        itemList = generateItems();

        RecyclerView recyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DatabaseListAdapter databaseListAdapter = new DatabaseListAdapter(itemList);
        recyclerView.setAdapter(databaseListAdapter);

        // Initializing plus dialog
        plusDialog = new Dialog(getContext());
        plusDialog.setContentView(R.layout.store_database_plus_popup);
        plusDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plusDialog.setCancelable(true);
        buttonAddDatabase = plusDialog.findViewById(R.id.button_upload_database);
        buttonAddProduct = plusDialog.findViewById(R.id.button_add_item);

        // Initializing add dialog
        addDialog = new Dialog(getContext());
        addDialog.setContentView(R.layout.store_database_add_popup);
        addDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addDialog.setCancelable(true);
        buttonCloseAdd =  addDialog.findViewById(R.id.button_close_add);
        buttonSaveAdd =  addDialog.findViewById(R.id.button_save_popup);

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
                plusDialog.dismiss();
                addDialog.dismiss();

                // Create the Snackbar
                Snackbar snackbar = Snackbar.make(getView(), getString(R.string.success_add_item), Snackbar.LENGTH_LONG);
                // Set the Snackbar Layout
                snackbar.setBackgroundTint(ContextCompat.getColor(getContext(), R.color.success_color_green));
                snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                // Show the Snackbar
                snackbar.show();

            }
        });

        return binding.getRoot();

        }

        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // Initializing plus dialog
            plusDialog = new Dialog(getContext());
            plusDialog.setContentView(R.layout.store_database_plus_popup);
            plusDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            plusDialog.setCancelable(true);
            buttonAddDatabase = plusDialog.findViewById(R.id.button_upload_database);
            buttonAddProduct = plusDialog.findViewById(R.id.button_add_item);

            // Initializing add dialog
            addDialog = new Dialog(getContext());
            addDialog.setContentView(R.layout.store_database_add_popup);
            addDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addDialog.setCancelable(true);
            buttonCloseAdd =  addDialog.findViewById(R.id.button_close_add);
            buttonSaveAdd =  addDialog.findViewById(R.id.button_save_popup);

            final EditText nameEditText = addDialog.findViewById(R.id.edit_text_item_name_popup);
            final EditText categoryEditText = addDialog.findViewById(R.id.edit_text_item_category_popup);
            final EditText barcodeEditText = addDialog.findViewById(R.id.edit_text_barcode_popup);
            final EditText amountEditText = addDialog.findViewById(R.id.edit_text_amount_popup);
            final EditText priceEditText = addDialog.findViewById(R.id.edit_text_price_popup);

            storeDatabaseViewModel.getStoreDatabaseFormState().observe(getViewLifecycleOwner(), storeDatabaseFormState -> {
                if (storeDatabaseFormState == null) {
                    return;
                }
                isDataValid = storeDatabaseFormState.isDataValid();

                if (storeDatabaseFormState.getNameError() != null) {
                    nameEditText.setError(getString(storeDatabaseFormState.getNameError()));
                }
                if (storeDatabaseFormState.getCategoryError() != null) {
                    categoryEditText.setError(getString(storeDatabaseFormState.getCategoryError()));
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
                    double amountText;
                    double priceText;
                    try{
                        amountText = Double.parseDouble(amountEditText.getText().toString());
                    } catch (NumberFormatException e) {
                        amountText = -1;
                    }
                    try{
                        priceText = Double.parseDouble(priceEditText.getText().toString());
                    } catch (NumberFormatException e) {
                        priceText = -1;
                    }
                    storeDatabaseViewModel.storeDatabaseDataChanged(nameEditText.getText().toString(), categoryEditText.getText().toString(),
                            barcodeEditText.getText().toString(), amountText, priceText);
                }
            };
            nameEditText.addTextChangedListener(afterTextChangedListener);
            categoryEditText.addTextChangedListener(afterTextChangedListener);
            barcodeEditText.addTextChangedListener(afterTextChangedListener);
            amountEditText.addTextChangedListener(afterTextChangedListener);
            priceEditText.addTextChangedListener(afterTextChangedListener);

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
                    if (!isDataValid) {
                        return;
                    }

                    plusDialog.dismiss();
                    addDialog.dismiss();

                    // Create the Snackbar
                    Snackbar snackbar = Snackbar.make(getView(), getString(R.string.success_add_item), Snackbar.LENGTH_LONG);
                    // Set the Snackbar Layout
                    snackbar.setBackgroundTint(ContextCompat.getColor(getContext(), R.color.success_color_green));
                    snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    // Show the Snackbar
                    snackbar.show();

                    if (getContext() != null && getContext().getApplicationContext() != null) {
                        Toast.makeText(getContext().getApplicationContext(), "Product added successfully", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

        private List<ProductModel> generateItems() {
            List<Integer> imageList = generateImages();

            List<ProductModel> item = new ArrayList<>();
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            item.add(new ProductModel("name", "price", imageList, "category", "discount"));
            return item;
        }

        private List<Integer> generateImages() {
            List<Integer> images = new ArrayList<>();
            images.add(R.drawable.bread);
            images.add(R.drawable.bread);
            images.add(R.drawable.bread);
            return images;
        }

        private void showDialog(){
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.store_database_plus_popup);
            dialog.show();
        }
}
