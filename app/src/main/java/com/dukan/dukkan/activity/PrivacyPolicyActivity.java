package com.dukan.dukkan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerPrivacyPolicyAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreNeedAdapter;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.Privacy;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyActivity extends AppCompatActivity   {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);
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
        getPrivacyPolicy();
    }
    private void getPrivacyPolicy() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Privacy> callNew = apiInterface.getPrivacyList();
        callNew.enqueue(new Callback<Privacy>() {
            @Override
            public void onResponse(Call<Privacy> callNew, Response<Privacy> response) {
                Log.d("TAG111111",response.code()+"");
                Privacy resource = response.body();
                if(resource.status) {
                    List<Privacy.Datum> datumList = resource.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerPrivacyPolicyAdapter adapter = new RecyclerPrivacyPolicyAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                }
                else
                    Toast.makeText(PrivacyPolicyActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Privacy> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}