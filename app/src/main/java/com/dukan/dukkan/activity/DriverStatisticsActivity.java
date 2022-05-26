package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.dukan.dukkan.adapter.RecyclerMerchantOrder2Adapter;
import com.dukan.dukkan.fragment.DriveOrderFilterSheetFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.util.HorizontalListView;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverStatisticsActivity extends AppCompatActivity {
    TextView tv_num_orders,tv_total_cost;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_statistics_acticity);
//        SharedPreferenceManager.getInstance(getApplicationContext()).setFilterDates("");
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        progressBar = findViewById(R.id.progressBar);
        tv_num_orders = findViewById(R.id.tv_num_orders);
        tv_total_cost = findViewById(R.id.tv_total_cost);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        recyclerView = findViewById(R.id.recyclerView);
        ImageView icon_notification = findViewById(R.id.icon_notification);
        ImageView img_filter = findViewById(R.id.img_filter);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        ImageView icon_buy = toolbar.findViewById(R.id.icon_buy);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_buy.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
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
                startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
            }
        });
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriveOrderFilterSheetFragment driveOrderFilterSheetFragment = new DriveOrderFilterSheetFragment();
                driveOrderFilterSheetFragment.show(getSupportFragmentManager()
                        , driveOrderFilterSheetFragment.getTag());
            }
        });
            getOrders("","");
    }
    private void getOrders(String dateFrom,String dateTo) {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("TAG111111 ssssss "+ID);
        Call<Order> callNew = apiInterface.GetAllOrders(ID,"android","1",dateFrom,dateTo,"","","1");
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
    @Override
    protected void onResume() {
        super.onResume();
        if(!SharedPreferenceManager.getInstance(getApplicationContext()).getFilterDates().equals("")){
            String[] getFilterDates =  SharedPreferenceManager.getInstance(getBaseContext()).getFilterDates().split("&");
            getOrders(getFilterDates[0],getFilterDates[1]);
            SharedPreferenceManager.getInstance(getApplicationContext()).setFilterDates("");
        }
    }
}