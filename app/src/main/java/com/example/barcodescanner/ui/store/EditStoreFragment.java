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

public class EditStoreFragment extends Fragment {

    Dialog editStoreDialog;
    Button editStoreCancel;
    Button editStoreConfirm;
    StoreModel store;
    List<Integer> imageList;
    String storeName;
    String location;

    private EditStoreViewModel editStoreViewModel;

    private boolean isDataValid;
    private FragmentEditStoreBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditStoreBinding.inflate(inflater, container, false);

        editStoreViewModel = new EditStoreViewModel();
        isDataValid = false;

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceStores = referenceProfile.child("Store Owners");
        DatabaseReference referenceCurrentStore = referenceStores.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceCurrentStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                storeName = dataSnapshot.child("Storename").getValue().toString();
                location = dataSnapshot.child("location").getValue().toString();

                store = new StoreModel(storeName,location, generateImages());
                imageList = store.getStoreImageList();
                RecyclerView recyclerView = binding.recyclerView;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                ImageListAdapter imageListAdapter = new ImageListAdapter(imageList);
                recyclerView.setAdapter(imageListAdapter);

                binding.textviewStorename.setText(store.getStoreName());
                binding.textviewLocation.setText(store.getStoreLocation());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button editInformationButton = view.findViewById(R.id.button_edit_information);
        final Button addPicturesButton = view.findViewById(R.id.button_add_pictures);

        //Initializing edit store dialog
        editStoreDialog = new Dialog(getContext());
        editStoreDialog.setContentView(R.layout.dialog_edit_store_information_box);
        editStoreDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
        editStoreDialog.getWindow().setBackgroundDrawable(background);
        editStoreDialog.setCancelable(true);
        editStoreCancel =  editStoreDialog.findViewById(R.id.edit_store_cancel);
        editStoreConfirm =  editStoreDialog.findViewById(R.id.edit_store_confirm);

        final EditText storeNameEditText = editStoreDialog.findViewById(R.id.edit_store_name);
        final EditText storeLocationEditText = editStoreDialog.findViewById(R.id.edit_store_location);
        final EditText storeKvKEditText = editStoreDialog.findViewById(R.id.edit_store_kvk);

        editStoreViewModel.getEditStoreFormState().observe(getViewLifecycleOwner(), editStoreFormState -> {
            if (editStoreFormState == null) {
                return;
            }
            isDataValid = editStoreFormState.isDataValid();

            if (editStoreFormState.getKvkError() != null) {
                storeKvKEditText.setError(getString(editStoreFormState.getKvkError()));
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
                String storeName;
                String storeLocation;

                // TODO: check if valid information

                // edit information in database
                int kvkText;
                try{
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
                editStoreDialog.show();
            }
        });

        editStoreCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStoreDialog.dismiss();
            }
        });

        editStoreConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // implement
                if(isDataValid && !storeKvKEditText.getText().toString().isEmpty() && !storeLocationEditText.getText().toString().isEmpty() && !storeNameEditText.getText().toString().isEmpty()){
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
                DatabaseReference referenceStoreOwners= referenceProfile.child("Store Owners");
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
                        editStoreDialog.dismiss();
                        if (getActivity() != null && getActivity() instanceof StoreActivity) {
                            ((StoreActivity) getActivity()).replaceFragment(new EditStoreFragment(), getResources().getString(R.string.edit_store_title));
                        }

                        // Create the Snackbar
                        Snackbar snackbar = Snackbar.make(getView(), getString(R.string.success_edit_store), Snackbar.LENGTH_LONG);
                        // Set the Snackbar Layout
                        snackbar.setBackgroundTint(ContextCompat.getColor(getContext(), R.color.success_color_green));
                        snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        // Show the Snackbar
                        snackbar.show();
                    }
                });

            }else {
                    if (storeKvKEditText.getText().toString().isEmpty()){
                        storeKvKEditText.setError("Text field cannot be empty, please re-enter the store information");
                    }
                    if (storeLocationEditText.getText().toString().isEmpty()){
                        storeLocationEditText.setError("Text field cannot be empty, please re-enter the store information");
                    }
                    if (storeNameEditText.getText().toString().isEmpty()){
                        storeNameEditText.setError("Text field cannot be empty, please re-enter the store information");
                    }
                }
            }
        });

        addPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
