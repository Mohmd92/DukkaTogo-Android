package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerMerchantOrder2Adapter;
import com.dukan.dukkan.adapter.RecyclerOrderAdapter2;
import com.dukan.dukkan.pojo.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantOrdersDeliveredActivity extends AppCompatActivity {
    APIInterface apiInterface;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_orders_elivered);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_edit = toolbar.findViewById(R.id.icon_edit);
        icon_edit.setVisibility(View.GONE);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MerchantOrdersDeliveredActivity.this, MerchantOrderDetailsActivity.class));
            }
        });
        getOrders();
    }
    private void getOrders() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("TAG111111 ssssss "+ID);
        Call<Order> callNew = apiInterface.GetAllOrders(ID,"android","","","","","1","");
        callNew.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> callNew, Response<Order> response) {
                Log.d("TAG111111",response.code()+"");
                Order resource = response.body();
                Log.d("TAG111111","111111111111111111111111111111111 resource "+resource.status);
                if(resource.status){
                    Log.d("TAG111111","111111111111111111111111111111111ww");
                    List<Order.Datum> datumList = resource.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerMerchantOrder2Adapter adapter = new RecyclerMerchantOrder2Adapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
}