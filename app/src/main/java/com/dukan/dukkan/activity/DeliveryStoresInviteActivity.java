package com.dukan.dukkan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerStoreDeliveryAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreDeliveryInviteAdapter;
import com.dukan.dukkan.pojo.Request;
import com.dukan.dukkan.pojo.Request;
import com.dukan.dukkan.pojo.RequestMerchant;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryStoresInviteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_invite_delivery);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getStores();
    }

    private void getStores() {
        progressBar.setVisibility(View.VISIBLE);
        Call<RequestMerchant> callNew = apiInterface.getRequestsMerchant();
        callNew.enqueue(new Callback<RequestMerchant>() {
            @Override
            public void onResponse(Call<RequestMerchant> callNew, Response<RequestMerchant> response) {
                Log.d("TAG111111", response.code() + "");
                RequestMerchant resource = response.body();
                if (resource.status) {
                    List<RequestMerchant.Datum> datumList = resource.data.data;
                    List<Store> storeList = new ArrayList<>();
                    for (int i = 0; i < datumList.size(); i++) {
                        Store store = datumList.get(i).store;
                        if (store != null) {
                            storeList.add(store);
                        }

                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerStoreDeliveryInviteAdapter adapter = new RecyclerStoreDeliveryInviteAdapter(DeliveryStoresInviteActivity.this, getApplicationContext(), datumList);
                    adapter.setStores(storeList);
                    recyclerView.setAdapter(adapter);
                } else
                    Toast.makeText(DeliveryStoresInviteActivity.this, "" + resource.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<RequestMerchant> call, Throwable t) {
                Log.d("TAG111111", "  e " + t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}