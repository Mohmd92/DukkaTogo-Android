package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;

public class MerchantDiscountsCode extends AppCompatActivity {
    APIInterface apiInterface;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_discounts_code);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        recyclerView = findViewById(R.id.recyclerView);
        RelativeLayout rel_add = findViewById(R.id.rel_add);
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
        rel_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MerchantDiscountsCode.this, MerchantCreateDiscount.class));

            }
        });
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MerchantDiscountsCode.this, NotificationsActivity.class));
            }
        });
    }

}