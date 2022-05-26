package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.ProductAdapter;
import com.dukan.dukkan.pojo.OrderDetail;
import com.dukan.dukkan.pojo.OrderToDelevey;
import com.dukan.dukkan.pojo.OrderToDelevey;
import com.dukan.dukkan.util.HorizontalListView;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.gms.maps.SupportMapFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverOrderDetailsActivity extends AppCompatActivity {
    TextView tv_date,tv_day,tv_num_products,tv_start_place,tv_arrival_place,tv_price;
    HorizontalListView HorizontalListViewProduct;
    APIInterface apiInterface;
    ProgressBar progressBar;
    int OrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_order_details_acticity);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        OrderId= getIntent().getExtras().getInt("OrderId");
        tv_date = findViewById(R.id.tv_date);
        tv_day = findViewById(R.id.tv_day);
        tv_num_products = findViewById(R.id.tv_num_products);
        tv_start_place = findViewById(R.id.tv_start_place);
        tv_arrival_place = findViewById(R.id.tv_arrival_place);
        tv_price = findViewById(R.id.tv_price);
        progressBar = findViewById(R.id.progressBar);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        HorizontalListViewProduct = findViewById(R.id.HorizontalListViewProduct);
        Button but_accept = findViewById(R.id.but_accept);
        Button but_reject = findViewById(R.id.but_reject);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        ImageView icon_buy = toolbar.findViewById(R.id.icon_buy);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        icon_buy.setVisibility(View.GONE);
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
                OrderToDelv();
            }
        });
        but_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void OrderToDelv() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<OrderToDelevey> callNew = apiInterface.OrderToDelevry(OrderId,ID,"android","test message","put",1,1);
        callNew.enqueue(new Callback<OrderToDelevey>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OrderToDelevey> callNew, Response<OrderToDelevey> response) {
                OrderToDelevey resource = response.body();
                if(resource.status){
                    Toast.makeText(DriverOrderDetailsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(DriverOrderDetailsActivity.this, resource.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<OrderToDelevey> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
}