package com.dukan.dukkan.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.LoginActivity;
import com.dukan.dukkan.activity.MainActivity;
import com.dukan.dukkan.activity.MainDriveActivity;
import com.dukan.dukkan.activity.MainMerchantActivity;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySheetFragment extends BottomSheetDialogFragment {
    APIInterface apiInterface;
    ImageView img_customer, img_merchant, img_driver;
    private String currentChose = "";

    public CategorySheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_switch_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout rel_customer = view.findViewById(R.id.rel_customer);
        RelativeLayout rel_merchant = view.findViewById(R.id.rel_merchant);
        RelativeLayout rel_driver = view.findViewById(R.id.rel_driver);
        img_customer = view.findViewById(R.id.img_customer);
        img_merchant = view.findViewById(R.id.img_merchant);
        img_driver = view.findViewById(R.id.img_driver);
        rel_merchant.setVisibility(View.GONE);
        rel_driver.setVisibility(View.GONE);
        rel_customer.setVisibility(View.GONE);
        String UserRole = SharedPreferenceManager.getInstance(getContext()).getUserType();
        String UserCurrentType = SharedPreferenceManager.getInstance(getContext()).getUserCurrentType();
        System.out.println("UserRoleUserRoleUserRole " + UserRole);
        if (UserCurrentType.equals("Merchant")) {
            Picasso.get()
                    .load(R.drawable.ic_unchek)
                    .placeholder(R.drawable.ic_unchek)
                    .into(img_customer);

            Picasso.get()
                    .load(R.drawable.ic_check)
                    .placeholder(R.drawable.ic_check)
                    .into(img_merchant);

            Picasso.get()
                    .load(R.drawable.ic_unchek)
                    .placeholder(R.drawable.ic_unchek)
                    .into(img_driver);
        } else if (UserCurrentType.equals("Delivery")) {
            Picasso.get()
                    .load(R.drawable.ic_unchek)
                    .placeholder(R.drawable.ic_unchek)
                    .into(img_customer);

            Picasso.get()
                    .load(R.drawable.ic_unchek)
                    .placeholder(R.drawable.ic_unchek)
                    .into(img_merchant);

            Picasso.get()
                    .load(R.drawable.ic_check)
                    .placeholder(R.drawable.ic_check)
                    .into(img_driver);
        } else if (UserCurrentType.equals("Customer")) {
            Picasso.get()
                    .load(R.drawable.ic_check)
                    .placeholder(R.drawable.ic_check)
                    .into(img_customer);

            Picasso.get()
                    .load(R.drawable.ic_unchek)
                    .placeholder(R.drawable.ic_unchek)
                    .into(img_merchant);

            Picasso.get()
                    .load(R.drawable.ic_unchek)
                    .placeholder(R.drawable.ic_unchek)
                    .into(img_driver);
        }

        if (UserRole.contains("Merchant"))
            rel_merchant.setVisibility(View.VISIBLE);
        if (UserRole.contains("Delivery"))
            rel_driver.setVisibility(View.VISIBLE);
        if (UserRole.contains("Customer"))
            rel_customer.setVisibility(View.VISIBLE);
        Button confirm_button = view.findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentChose.equals("customer")) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    SharedPreferenceManager.getInstance(getContext()).setUserCurrentType("Customer");
                } else if (currentChose.equals("merchant")) {
                    startActivity(new Intent(getContext(), MainMerchantActivity.class));
                    SharedPreferenceManager.getInstance(getContext()).setUserCurrentType("Merchant");
                } else if (currentChose.equals("driver")) {
                    startActivity(new Intent(getContext(), MainDriveActivity.class));
                    SharedPreferenceManager.getInstance(getContext()).setUserCurrentType("Merchant");
                }
                dismiss();

            }
        });
        rel_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   dismiss();
                Picasso.get()
                        .load(R.drawable.ic_check)
                        .placeholder(R.drawable.ic_check)
                        .into(img_customer);

                Picasso.get()
                        .load(R.drawable.ic_unchek)
                        .placeholder(R.drawable.ic_unchek)
                        .into(img_merchant);

                Picasso.get()
                        .load(R.drawable.ic_unchek)
                        .placeholder(R.drawable.ic_unchek)
                        .into(img_driver);
                currentChose = "customer";
            }
        });
        rel_merchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getContext(), MainMerchantActivity.class));
//                dismiss();
                Picasso.get()
                        .load(R.drawable.ic_unchek)
                        .placeholder(R.drawable.ic_unchek)
                        .into(img_customer);

                Picasso.get()
                        .load(R.drawable.ic_check)
                        .placeholder(R.drawable.ic_check)
                        .into(img_merchant);

                Picasso.get()
                        .load(R.drawable.ic_unchek)
                        .placeholder(R.drawable.ic_unchek)
                        .into(img_driver);

                currentChose = "merchant";
            }
        });
        rel_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getContext(), MainDriveActivity.class));
//                dismiss();
                Picasso.get()
                        .load(R.drawable.ic_unchek)
                        .placeholder(R.drawable.ic_unchek)
                        .into(img_customer);

                Picasso.get()
                        .load(R.drawable.ic_unchek)
                        .placeholder(R.drawable.ic_unchek)
                        .into(img_merchant);

                Picasso.get()
                        .load(R.drawable.ic_check)
                        .placeholder(R.drawable.ic_check)
                        .into(img_driver);

                currentChose = "driver";
            }
        });


//        rel_customer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), MainActivity.class));
//                dismiss();
//                Picasso.get()
//                        .load(R.drawable.ic_check)
//                        .placeholder(R.drawable.ic_check)
//                        .into(img_customer);
//
//                Picasso.get()
//                        .load(R.drawable.ic_unchek)
//                        .placeholder(R.drawable.ic_unchek)
//                        .into(img_merchant);
//
//                Picasso.get()
//                        .load(R.drawable.ic_unchek)
//                        .placeholder(R.drawable.ic_unchek)
//                        .into(img_driver);
//            }
//        });
//        rel_merchant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), MainMerchantActivity.class));
//                dismiss();
//                Picasso.get()
//                        .load(R.drawable.ic_unchek)
//                        .placeholder(R.drawable.ic_unchek)
//                        .into(img_customer);
//
//                Picasso.get()
//                        .load(R.drawable.ic_check)
//                        .placeholder(R.drawable.ic_check)
//                        .into(img_merchant);
//
//                Picasso.get()
//                        .load(R.drawable.ic_unchek)
//                        .placeholder(R.drawable.ic_unchek)
//                        .into(img_driver);
//            }
//        });
//        rel_driver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), MainDriveActivity.class));
//                dismiss();
//                Picasso.get()
//                        .load(R.drawable.ic_unchek)
//                        .placeholder(R.drawable.ic_unchek)
//                        .into(img_customer);
//
//                Picasso.get()
//                        .load(R.drawable.ic_unchek)
//                        .placeholder(R.drawable.ic_unchek)
//                        .into(img_merchant);
//
//                Picasso.get()
//                        .load(R.drawable.ic_check)
//                        .placeholder(R.drawable.ic_check)
//                        .into(img_driver);
//            }
//        });
    }

}
