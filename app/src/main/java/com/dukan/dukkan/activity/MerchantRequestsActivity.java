package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerDriversAdapter;
import com.dukan.dukkan.adapter.RecyclerRequestMerchantAdapter;
import com.dukan.dukkan.pojo.Driver;
import com.dukan.dukkan.pojo.Request;
import com.dukan.dukkan.pojo.RequestMerchant;
import com.dukan.dukkan.pojo.UserOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantRequestsActivity extends AppCompatActivity {
    APIInterface apiInterface;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_drivers);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_edit = toolbar.findViewById(R.id.icon_edit);
        icon_edit.setVisibility(View.GONE);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(getIntent().getExtras().getString("title"));
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
                startActivity(new Intent(MerchantRequestsActivity.this, NotificationsActivity.class));
            }
        });
        getDrivers();
    }
    private void getDrivers() {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Call<RequestMerchant> callNew = apiInterface.getRequestsMerchant();
        callNew.enqueue(new Callback<RequestMerchant>() {
            @Override
            public void onResponse(Call<RequestMerchant> callNew, Response<RequestMerchant> response) {
                Log.d("TAG111111",response.code()+"");
                RequestMerchant resource = response.body();
                if(resource.status) {
                    List<RequestMerchant.Datum> datumList = resource.data.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerRequestMerchantAdapter adapter = new RecyclerRequestMerchantAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                }else
                    Toast.makeText(MerchantRequestsActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<RequestMerchant> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
}