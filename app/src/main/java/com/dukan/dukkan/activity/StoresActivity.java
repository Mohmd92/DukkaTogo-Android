package com.dukan.dukkan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoresActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        progressBar =findViewById(R.id.progressBar);
        recyclerView =findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar2);
        ImageView iconMenu =toolbar.findViewById(R.id.icon_menu);
        ImageView iconBack =toolbar.findViewById(R.id.icon_back);
        iconMenu.setVisibility(View.GONE);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
       getStores();


    }
    private void getStores() {
        int countryId= Integer.parseInt(SharedPreferenceManager.getInstance(getBaseContext()).getCountryId());
        int cityId= Integer.parseInt(SharedPreferenceManager.getInstance(getBaseContext()).getCityId());
        progressBar.setVisibility(View.VISIBLE);
        Call<MultipleStore> callNew = apiInterface.doGetListStore(0,0);
        callNew.enqueue(new Callback<MultipleStore>() {
            @Override
            public void onResponse(Call<MultipleStore> callNew, Response<MultipleStore> response) {
                Log.d("TAG111111",response.code()+"");
                MultipleStore resource = response.body();
                String status = resource.status;
                List<MultipleStore.Datum> datumList = resource.data;
//                for (MultipleStore.Datum datum : datumList) {
//                    displayResponse += datum.id + " " + datum.name + " " + datum.description +"\n";
//                }
//                Log.d("TAG111111","  d "+displayResponse);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                RecyclerStoreAdapter adapter = new RecyclerStoreAdapter(getApplicationContext(), datumList);
                recyclerView.setAdapter(adapter);
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