package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.adapter.RecyclerdiscountAdapter;
import com.dukan.dukkan.pojo.Coupon;
import com.dukan.dukkan.pojo.CouponList;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantDiscountsCode extends AppCompatActivity {
    APIInterface apiInterface;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_discounts_code);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
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
                startActivity(new Intent(MerchantDiscountsCode.this, NotificationsActivity.class));
            }
        });
        getCouponList();
    }
    private void getCouponList() {
        progressBar.setVisibility(View.VISIBLE);
        Call<CouponList> callNew = apiInterface.doGetListCoupon();
        callNew.enqueue(new Callback<CouponList>() {
            @Override
            public void onResponse(Call<CouponList> callNew, Response<CouponList> response) {
                Log.d("TAG111111",response.code()+"");
                CouponList resource = response.body();
                if(resource.status) {
                    List<Coupon> datumList = resource.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerdiscountAdapter adapter = new RecyclerdiscountAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<CouponList> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}