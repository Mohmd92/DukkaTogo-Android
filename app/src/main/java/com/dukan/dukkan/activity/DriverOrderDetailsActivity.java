package com.dukan.dukkan.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.HorizontalListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DriverOrderDetailsActivity extends AppCompatActivity {
    TextView tv_date,tv_day,tv_num_products,tv_start_place,tv_arrival_place,tv_price;
    HorizontalListView HorizontalListViewProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_order_details_acticity);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        tv_date = findViewById(R.id.tv_date);
        tv_day = findViewById(R.id.tv_day);
        tv_num_products = findViewById(R.id.tv_num_products);
        tv_start_place = findViewById(R.id.tv_start_place);
        tv_arrival_place = findViewById(R.id.tv_arrival_place);
        tv_price = findViewById(R.id.tv_price);
        HorizontalListViewProduct = findViewById(R.id.HorizontalListViewProduct);
        Button but_accept = findViewById(R.id.but_accept);
        Button but_reject = findViewById(R.id.but_reject);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        but_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        but_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}