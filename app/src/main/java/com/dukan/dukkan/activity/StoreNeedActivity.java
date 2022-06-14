package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerCartsAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreDeliveryAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreNeedAdapter;
import com.dukan.dukkan.pojo.Cart;
import com.dukan.dukkan.pojo.CartMain2;
import com.dukan.dukkan.pojo.CouponMain;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreNeedActivity extends AppCompatActivity   {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_need_delivery);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        ImageView img_back =  findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getStores1();
    }
    private void getStores1() {
        int countryId= Integer.parseInt(SharedPreferenceManager.getInstance(getBaseContext()).getCountryId());
        int cityId= Integer.parseInt(SharedPreferenceManager.getInstance(getBaseContext()).getCityId());
        progressBar.setVisibility(View.VISIBLE);
        Call<MultipleStore> callNew = apiInterface.doGetListStoreDelivery(cityId,countryId,1,0);
        callNew.enqueue(new Callback<MultipleStore>() {
            @Override
            public void onResponse(Call<MultipleStore> callNew, Response<MultipleStore> response) {
                Log.d("TAG111111",response.code()+"");
                MultipleStore resource = response.body();
                if(resource.status) {
                    List<MultipleStore.Datum> datumList = resource.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerStoreNeedAdapter adapter = new RecyclerStoreNeedAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                }
                else
                    Toast.makeText(StoreNeedActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<MultipleStore> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }


}