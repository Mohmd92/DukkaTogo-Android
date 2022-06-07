package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerOrderAdapter;
import com.dukan.dukkan.adapter.RecyclerOrderAdapter2;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);
        progressBar =findViewById(R.id.progressBar);
        recyclerView =findViewById(R.id.recyclerView);
        ImageView img_back =findViewById(R.id.img_back);
        ImageView image_filter =findViewById(R.id.image_filter);
        TextView tv_full =findViewById(R.id.tv_full);
        TextView tv_unfull =findViewById(R.id.tv_unfull);
        CardView card_fulfilled =findViewById(R.id.card_fulfilled);
        CardView card_unfulfilled =findViewById(R.id.card_unfulfilled);
        card_fulfilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_fulfilled.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button12));
                card_unfulfilled.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.edit_shap2));
                tv_unfull.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                tv_full.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                getOrders();
            }
        });
        card_unfulfilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_fulfilled.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.edit_shap2));
                card_unfulfilled.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button12));
                tv_unfull.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                tv_full.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                getOrders2();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
       getOrders();


    }
    private void getOrders() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<Order> callNew = apiInterface.GetAllOrders(ID,"android","","","","","","2");
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
                    RecyclerOrderAdapter adapter = new RecyclerOrderAdapter(getApplicationContext(), datumList,OrdersActivity.this);
                recyclerView.setAdapter(adapter);
            }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("TAG111111 xxxx","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
    private void getOrders2() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("TAG111111 ssssss "+ID);
        Call<Order> callNew = apiInterface.GetAllOrders(ID,"android","","","","","","0");
        callNew.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> callNew, Response<Order> response) {
                Order resource = response.body();
                if(resource.status){
                    List<Order.Datum> datumList = resource.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerOrderAdapter2 adapter = new RecyclerOrderAdapter2(getApplicationContext(), datumList,OrdersActivity.this);
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