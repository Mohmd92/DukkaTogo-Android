package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.fragment.ChooseDriverSheetFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.util.HorizontalListView;

public class MerchantOrderDetailsActivity extends AppCompatActivity {
    TextView tv_date,tv_day,tv_num_products,tv_start_place,tv_arrival_place,tv_price,tv_points,tv_join_date,tv__driver_name;
    HorizontalListView HorizontalListViewProduct;
    ImageView image_driver;
    CardView card_driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_order_details_acticity);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("");
        tv_date = findViewById(R.id.tv_date);
        tv_day = findViewById(R.id.tv_day);
        tv_num_products = findViewById(R.id.tv_num_products);
        tv_start_place = findViewById(R.id.tv_start_place);
        tv_arrival_place = findViewById(R.id.tv_arrival_place);
        tv_price = findViewById(R.id.tv_price);
        tv_points = findViewById(R.id.tv_points);
        card_driver = findViewById(R.id.card_driver);
        tv_join_date = findViewById(R.id.tv_join_date);
        tv__driver_name = findViewById(R.id.tv__driver_name);
        image_driver = findViewById(R.id.image_driver);
        image_driver.setClipToOutline(true);
        HorizontalListViewProduct = findViewById(R.id.HorizontalListViewProduct);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_barcode = toolbar.findViewById(R.id.icon_barcode);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        icon_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDriverSheetFragment chooseDriverSheetFragment = new ChooseDriverSheetFragment();
                chooseDriverSheetFragment.show(getSupportFragmentManager()
                        , chooseDriverSheetFragment.getTag());
            }
        });

    }
}