package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class NotificationsActivity extends AppCompatActivity {
    LinearLayout linear_no_account,linear_exist_account,linear_no_notifications;

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
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        linear_exist_account = findViewById(R.id.linear_exist_account);
        linear_no_account = findViewById(R.id.linear_no_account);
        linear_no_notifications = findViewById(R.id.linear_no_notifications);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        icon_notification.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        ImageView icon_buy = findViewById(R.id.icon_buy);
        icon_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, CartActivity.class));
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
        }
        linear_no_notifications.setVisibility(View.VISIBLE);
        linear_no_account.setVisibility(View.GONE);
        linear_exist_account.setVisibility(View.GONE);


    }
}