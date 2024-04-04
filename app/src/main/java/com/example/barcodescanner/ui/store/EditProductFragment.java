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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditProductFragment extends Fragment {

    Dialog editDialog;
    Button buttonCloseEdit;
    Button buttonSaveEdit;

    Dialog deleteDialog;
    Button buttonCancelDelete;
    Button buttonConfirmDelete;
    ProductModel item;
    int position;
    List<Integer> imageList;
    private StoreDatabaseViewModel storeDatabaseViewModel;

    private StoreProductViewModel storeProductViewModel;

    private FragmentEditProductBinding binding;
    private boolean isDataValid;

    private String selectedItem;
    public EditProductFragment(ProductModel item, int position) {
        this.item = item;
        this.position = position;
    }

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

        binding = FragmentEditProductBinding.inflate(inflater, container, false);

        storeProductViewModel = ((StoreActivity) getActivity()).getStoreProductViewModel();

        binding.imageMain.setImageResource(item.getProductImage(0));

        imageList = item.getProductImageList();
        RecyclerView recyclerView = binding.editProductImages;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
        recyclerView.setAdapter(imageListAdapter);

        binding.storeProductName.setText(item.getProductName());
        binding.storeProductPrice.setText(item.getProductPrice());
        binding.storeProductCategory.setText(item.getCategory());

//        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

//        binding.productDeleteIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (view.getContext() != null && view.getContext() instanceof StoreActivity) {
//                    ((StoreActivity) view.getContext()).replaceFragment(new StoreDatabaseFragment(), view.getContext().getString(R.string.edit_product_title));
//                }
//            }
//        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageButton backButton = view.findViewById(R.id.button_back);
        final Button addPicturesButton = view.findViewById(R.id.button_product_add_pictures);
        final Button editProductButton = view.findViewById(R.id.button_product_edit);
        final ImageButton deleteProductButton = view.findViewById(R.id.product_delete_icon);

        //Initializing add dialog
        editDialog = new Dialog(getContext());
        editDialog.setContentView(R.layout.dialog_store_database_add);
        editDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
        editDialog.getWindow().setBackgroundDrawable(background);
        editDialog.setCancelable(true);
        buttonCloseEdit =  editDialog.findViewById(R.id.edit_product_cancel);
        buttonSaveEdit =  editDialog.findViewById(R.id.edit_product_confirm);

        deleteDialog = new Dialog(getContext());
        deleteDialog.setContentView(R.layout.dialog_delete_product_box);
        deleteDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteDialog.getWindow().setBackgroundDrawable(background);
        deleteDialog.setCancelable(true);
        buttonConfirmDelete = deleteDialog.findViewById(R.id.delete_product_dialog_button_confirm);
        buttonCancelDelete = deleteDialog.findViewById(R.id.delete_product_dialog_button_cancel);

        final EditText nameEditText = editDialog.findViewById(R.id.edit_item_name);
        final Spinner categorySpinner = editDialog.findViewById(R.id.spinner_item_category);
        final EditText barcodeEditText = editDialog.findViewById(R.id.edit_text_barcode);
        final EditText amountEditText = editDialog.findViewById(R.id.edit_item_amount);
        final EditText priceEditText = editDialog.findViewById(R.id.edit_item_price);
        final EditText discountEditText = editDialog.findViewById(R.id.edit_text_discount);

        String[] categories = {"Food", "Drinks", "Other stuff"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Perform action when an item is selected
                selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() != null && view.getContext() instanceof StoreActivity) {
                    ((StoreActivity) view.getContext()).replaceFragment(new StoreDatabaseFragment(), view.getContext().getString(R.string.edit_product_title));
                }
            }
        });

        addPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        editProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.show();
            }
        });

        buttonCloseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });

        buttonSaveEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // whatever the fuck was inputted in the dialog code
                DatabaseReference referenceStores = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference removalProduct = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductName());
                removalProduct.getRef().removeValue();

                item.setProductName(nameEditText.getText().toString());
                item.setCategory(selectedItem);
                item.setProductBarcode(barcodeEditText.getText().toString());
                item.setProductAmount(amountEditText.getText().toString());
                item.setProductPrice(priceEditText.getText().toString());

                storeProductViewModel.setProductModel(position, item);

                DatabaseReference referenceStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                referenceStore.child(nameEditText.getText().toString()).setValue(item);

                binding.storeProductName.setText(item.getProductName());
                binding.storeProductPrice.setText(item.getProductPrice());
                binding.storeProductCategory.setText(item.getCategory());

                editDialog.dismiss();

            }
        });

        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.show();

            }
        });

        buttonCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        buttonConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference removalProduct = referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getProductName());
                removalProduct.getRef().removeValue();
                deleteDialog.dismiss();
                if (view.getContext() != null && view.getContext() instanceof StoreActivity) {
                    ((StoreActivity) view.getContext()).replaceFragment(new StoreDatabaseFragment(), view.getContext().getString(R.string.edit_product_title));
                }

                ArrayList<ProductModel> tempProductModels = storeProductViewModel.getProductModels().getValue();
                tempProductModels.remove(position);
                storeProductViewModel.setProductModels(tempProductModels);

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
