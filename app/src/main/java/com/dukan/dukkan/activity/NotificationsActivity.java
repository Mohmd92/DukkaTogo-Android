package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.dukan.dukkan.adapter.RecyclerNotificationAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.Notifications;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    LinearLayout linear_no_account,linear_exist_account,linear_no_notifications;
    ProgressBar progressBar;
    APIInterface apiInterface;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        ImageView icon_buy = findViewById(R.id.icon_buy);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        linear_exist_account = findViewById(R.id.linear_exist_account);
        linear_no_account = findViewById(R.id.linear_no_account);
        linear_no_notifications = findViewById(R.id.linear_no_notifications);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        icon_notification.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        icon_buy.setVisibility(View.GONE);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        Button but_login = findViewById(R.id.confirm_button);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, LoginActivity.class));
                finish();
            }
        });
        if(!SharedPreferenceManager.getInstance(getBaseContext()).getUserCurrentType().equals("Customer"))
            icon_buy.setVisibility(View.GONE);


        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));

            }
        });
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(SharedPreferenceManager.getInstance(getBaseContext()).get_api_token().equals("")) {
            linear_exist_account.setVisibility(View.GONE);
            linear_no_account.setVisibility(View.VISIBLE);
        }else {
            linear_exist_account.setVisibility(View.VISIBLE);
            linear_no_account.setVisibility(View.GONE);
            getNotification();
        }



    }
    private void getNotification() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Notifications> callNew = apiInterface.getNotifications();
        callNew.enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> callNew, Response<Notifications> response) {
                Log.d("TAG111111",response.code()+"");
                Notifications resource = response.body();
                if(resource.status) {
                    List<Notifications.Datum> datumList = resource.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerNotificationAdapter adapter = new RecyclerNotificationAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                    if (datumList.size()==0){
                        linear_no_notifications.setVisibility(View.VISIBLE);
                        linear_no_account.setVisibility(View.GONE);
                        linear_exist_account.setVisibility(View.GONE);
                    }
                }else{
                    linear_no_notifications.setVisibility(View.VISIBLE);
                    linear_no_account.setVisibility(View.GONE);
                    linear_exist_account.setVisibility(View.GONE);
                }

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);
                linear_no_notifications.setVisibility(View.VISIBLE);
                linear_no_account.setVisibility(View.GONE);
                linear_exist_account.setVisibility(View.GONE);

            }

        });
    }
}