package com.example.barcodescanner.ui.store;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.customer.StoreModel;
import com.example.barcodescanner.databinding.FragmentEditStoreBinding;
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
 * Fragment responsible for editing store information.
 */
public class EditStoreFragment extends Fragment {

    // Dialog for editing store information
    private Dialog editStoreDialog;
    private Button editStoreCancel;
    private Button editStoreConfirm;
    // StoreModel instance to hold store data
    private StoreModel store;
    private List<Integer> imageList;
    // Strings to store store name and location
    private String storeName;
    private String location;

    // ViewModel for managing store information
    private EditStoreViewModel editStoreViewModel;

    // Flag to track whether data is valid
    private boolean isDataValid;
    // View binding instance
    private FragmentEditStoreBinding binding;

    // Method called when creating the view
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout using view binding
        binding = FragmentEditStoreBinding.inflate(inflater, container, false);

        // Initialize ViewModel and flag
        editStoreViewModel = new EditStoreViewModel();
        isDataValid = false;

        // Get reference to store data from Firebase
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceStores = referenceProfile.child("Store Owners");
        DatabaseReference referenceCurrentStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceCurrentStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Extract store name and location from the database
                storeName = dataSnapshot.child("Storename").getValue().toString();
                location = dataSnapshot.child("location").getValue().toString();

                // Create StoreModel instance and populate image list
                store = new StoreModel(storeName, location, generateImages());
                imageList = store.getStoreImageList();

                // Set up RecyclerView to display store images
                RecyclerView recyclerView = binding.recyclerView;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
                recyclerView.setAdapter(imageListAdapter);

                // Update UI with store name and location
                binding.textviewStorename.setText(store.getStoreName());
                binding.textviewLocation.setText(store.getStoreLocation());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error if needed
            }
        });

        return binding.getRoot();

    }

    // Method called after the view is created
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to UI elements
        final Button editInformationButton = view.findViewById(R.id.button_edit_information);
        final Button addPicturesButton = view.findViewById(R.id.button_add_pictures);

        //Initializing edit store dialog
        editStoreDialog = new Dialog(getContext());
        editStoreDialog.setContentView(R.layout.dialog_edit_store_information_box);
        editStoreDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
        editStoreDialog.getWindow().setBackgroundDrawable(background);
        editStoreDialog.setCancelable(true);
        editStoreCancel = editStoreDialog.findViewById(R.id.edit_store_cancel);
        editStoreConfirm = editStoreDialog.findViewById(R.id.edit_store_confirm);

        // Set up text change listeners for input fields
        final EditText storeNameEditText = editStoreDialog.findViewById(R.id.edit_store_name);
        final EditText storeLocationEditText = editStoreDialog.findViewById(R.id.edit_store_location);
        final EditText storeKvKEditText = editStoreDialog.findViewById(R.id.edit_store_kvk);

        // Observe the edit store form state
        editStoreViewModel.getEditStoreFormState().observe(getViewLifecycleOwner(), editStoreFormState -> {
            if (editStoreFormState == null) {
                return;
            }
            isDataValid = editStoreFormState.isDataValid();

            if (editStoreFormState.getKvkError() != null) {
                storeKvKEditText.setError(getString(editStoreFormState.getKvkError()));
            }
        });

        // Text watcher for text input changes
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
                // Method to be executed after text has been changed in EditText fields
                String storeName;
                String storeLocation;

                // TODO: check if valid information

                // Perform validation and update ViewModel
                int kvkText;
                try {
                    kvkText = Integer.parseInt(storeKvKEditText.getText().toString());
                } catch (NumberFormatException e) {
                    kvkText = 0;
                }
                editStoreViewModel.editStoreDataChanged(kvkText);
            }
        };
        storeNameEditText.addTextChangedListener(afterTextChangedListener);
        storeLocationEditText.addTextChangedListener(afterTextChangedListener);
        storeKvKEditText.addTextChangedListener(afterTextChangedListener);

        editInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the edit store dialog when the button is clicked
                editStoreDialog.show();
            }
        });

        editStoreCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the edit store dialog when cancel button is clicked
                editStoreDialog.dismiss();
            }
        });

        editStoreConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement store information update
                // Validate data and update Firebase if valid
                if (isDataValid && !storeKvKEditText.getText().toString().isEmpty() && !storeLocationEditText.getText().toString().isEmpty() && !storeNameEditText.getText().toString().isEmpty()) {
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
                    DatabaseReference referenceStoreOwners = referenceProfile.child("Store Owners");
                    referenceStoreOwners.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("kvkNumber").setValue(storeKvKEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                    referenceStoreOwners.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("location").setValue(storeLocationEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                    referenceStoreOwners.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Storename").setValue(storeNameEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Dismiss edit store dialog
                            editStoreDialog.dismiss();
                            if (getActivity() != null && getActivity() instanceof StoreActivity) {
                                // Refresh fragment to reflect changes
                                ((StoreActivity) getActivity()).replaceFragment(new EditStoreFragment(), getResources().getString(R.string.edit_store_title));
                            }

                            // Create and show a success Snackbar
                            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.success_edit_store), Snackbar.LENGTH_LONG);
                            snackbar.setBackgroundTint(ContextCompat.getColor(getContext(), R.color.success_color_green));
                            snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                            snackbar.show();
                        }
                    });

                } else {
                    // Show error messages if any field is empty
                    if (storeKvKEditText.getText().toString().isEmpty()) {
                        storeKvKEditText.setError("Text field cannot be empty, please re-enter the store information");
                    }
                    if (storeLocationEditText.getText().toString().isEmpty()) {
                        storeLocationEditText.setError("Text field cannot be empty, please re-enter the store information");
                    }
                    if (storeNameEditText.getText().toString().isEmpty()) {
                        storeNameEditText.setError("Text field cannot be empty, please re-enter the store information");
                    }
                }
            }
        });

        addPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Action to add pictures to the store
            }
        });
    }

    // Method called when the view is destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Method to generate sample images for the store
    private List<Integer> generateImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.supermarket_outside_foreground);
        images.add(R.mipmap.supermarket_outside1_foreground);
        images.add(R.mipmap.supermarket_inside1_foreground);
        images.add(R.mipmap.supermarket_inside2_foreground);
        images.add(R.mipmap.supermarket_inside3_foreground);
        images.add(R.mipmap.supermarket_baklava_foreground);
        images.add(R.mipmap.supermarket_outside_foreground);
        images.add(R.mipmap.supermarket_outside1_foreground);
        images.add(R.mipmap.supermarket_inside1_foreground);
        images.add(R.mipmap.supermarket_inside2_foreground);
        images.add(R.mipmap.supermarket_inside3_foreground);
        images.add(R.mipmap.supermarket_baklava_foreground);
        return images;
    }
}
