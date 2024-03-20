package com.example.barcodescanner.customer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barcodescanner.R;
import com.example.barcodescanner.ui.login.ForgotPasswordFragment;
import com.example.barcodescanner.ui.login.WelcomeActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements ProductRecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton barcodeScannerButton;



    // TODO: placeholder for items on main page
    ArrayList<ProductModel> productModels = new ArrayList<>();
    int[] productImage = {R.drawable.bread};
    //

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barcodeScannerButton = view.findViewById(R.id.barcode_scanner_button);

        // Sets up showing products inside the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.main_page_recyclerview);
        setupProductModels();
        ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(requireContext(),
                productModels,
                this);
        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        barcodeScannerButton.setOnClickListener(v -> {
            scanCode();

        });
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureActivity.class);

        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    });

    private void setupProductModels(){
        String[] productNames = getResources().getStringArray(R.array.placeholder_main_page_product);
        String[] productPrices = getResources().getStringArray(R.array.placeholder_main_page_price);

        for (int i = 0; i < productNames.length; i++) {
            productModels.add(new ProductModel(productNames[i],
                    productPrices[i],
                    productImage[0],
                    "10%"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                Toast.makeText(requireContext(), "Scanned code: " + contents, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Scan cancelled", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onItemClick(int position) {
        String[] toastMessages = requireContext().getResources().getStringArray(R.array.placeholder_main_page_product);

        if (position >= 0 && position < toastMessages.length) {
            String message = toastMessages[position];
            String toastMessage = getString(R.string.placeholder_toast_product_format, message);
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).replaceFragment(new ProductFragment());
        } else {
            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show();
        }
    }

    // Same code as onItemClick for now, but will be changed later
    @Override
    public void onFavouritesClick(int position) {
        String[] toastMessages = requireContext().getResources().getStringArray(R.array.placeholder_main_page_product);

        if (position >= 0 && position < toastMessages.length) {
            String message = toastMessages[position];
            String toastMessage = getString(R.string.placeholder_toast_favourites_format, message);
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show();
        }
    }

    // Same code as onItemClick for now, but will be changed later
    @Override
    public void onShoppingListClick(int position) {
        String[] toastMessages = requireContext().getResources().getStringArray(R.array.placeholder_main_page_product);

        if (position >= 0 && position < toastMessages.length) {
            String message = toastMessages[position];
            String toastMessage = getString(R.string.placeholder_toast_shopping_list_format, message);
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Invalid position", Toast.LENGTH_SHORT).show();
        }
    }
}