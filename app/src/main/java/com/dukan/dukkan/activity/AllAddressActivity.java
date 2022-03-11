package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerAddressAdapter;
import com.dukan.dukkan.adapter.RecyclerCartsAdapter;
import com.dukan.dukkan.pojo.AllAddress;
import com.dukan.dukkan.pojo.AllAddress;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.MultipleStore;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAddressActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    APIInterface apiInterface;
    Boolean started=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);

        ImageView img_back =  findViewById(R.id.img_back);
        RelativeLayout rel_add_address =  findViewById(R.id.rel_add_address);

        rel_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllAddressActivity.this, AddAddressActivity.class));

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
       getAddress();
    }

    private void getAddress() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("IDIDIDIDIDID "+ID);
        Call<AllAddress> callNew = apiInterface.GetAllAddress(ID,"android");
        callNew.enqueue(new Callback<AllAddress>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<AllAddress> callNew, Response<AllAddress> response) {
                AllAddress cart = response.body();
                if (cart.status) {
                    AllAddress resource = response.body();
                    List<AllAddress.AddressData> datumList = resource.data;

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerAddressAdapter adapter = new RecyclerAddressAdapter(getApplicationContext(),  datumList);
                    recyclerView.setAdapter(adapter);
//                    adapter.setClickListener(AllAddressActivity.this);
                }
                    progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<AllAddress> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }

        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!started)
            getAddress();

        started=false;
    }
}