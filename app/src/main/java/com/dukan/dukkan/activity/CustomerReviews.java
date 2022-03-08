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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerCartsAdapter;
import com.dukan.dukkan.adapter.RecyclerProductAdapter;
import com.dukan.dukkan.adapter.RecyclerRatesAdapter;
import com.dukan.dukkan.fragment.FilterSheetFragment;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.Rate;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerReviews extends AppCompatActivity  {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    ProgressBar progressBar;
    int productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reviews);
        progressBar =findViewById(R.id.progressBar);
        recyclerView =findViewById(R.id.recyclerView);
        ImageView img_back =findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        productID= getIntent().getExtras().getInt("productID");
        getProducts();


    }
    private void getProducts() {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Call<Rate> callNew = apiInterface.ProductRates(productID,ID,"android");
        callNew.enqueue(new Callback<Rate>() {
            @Override
            public void onResponse(Call<Rate> callNew, Response<Rate> response) {
                Log.d("TAG111111",response.code()+"");
                Rate resource = response.body();
                if(resource.status) {
                    List<Rate.Datum> newProduct = resource.data;
                    RecyclerRatesAdapter adapter = new RecyclerRatesAdapter(getApplicationContext(), newProduct);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }}
            @Override
            public void onFailure(Call<Rate> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
}