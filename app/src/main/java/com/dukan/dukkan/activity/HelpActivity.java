package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class HelpActivity extends AppCompatActivity {
    TextView tv_wash,tv_dish,tv_ref;
    View viewss1,viewss2,viewss3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_help);
        LinearLayout linear_call = findViewById(R.id.linear_call);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        ImageView icon_buy = toolbar.findViewById(R.id.icon_buy);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_buy.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        icon_notification.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        if (SharedPreferenceManager.getInstance(getBaseContext()).getUserType().equals("driver")){
            findViewById(R.id.tv_title).setVisibility(View.GONE);
            findViewById(R.id.tv_sub_title).setVisibility(View.GONE);
            findViewById(R.id.imag).setVisibility(View.GONE);
            findViewById(R.id.tv22).setVisibility(View.GONE);
            findViewById(R.id.tv_sub_title2).setVisibility(View.VISIBLE);
            linear_call.setVisibility(View.GONE);
            icon_notification.setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.tv_sub_title2).setVisibility(View.GONE);
        }
        setSupportActionBar(toolbar);

        tv_ref =findViewById(R.id.tv_ref);
        tv_dish =findViewById(R.id.tv_dish);
        tv_wash =findViewById(R.id.tv_wash);

        viewss1 =findViewById(R.id.viewss1);
        viewss2 =findViewById(R.id.viewss2);
        viewss3 =findViewById(R.id.viewss3);

        ImageView img_ref =findViewById(R.id.img_ref);
        ImageView img_dish =findViewById(R.id.img_dish);
        ImageView img_wash =findViewById(R.id.img_wash);

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(tv_ref.getVisibility()==View.GONE){
                   tv_ref.setVisibility(View.VISIBLE);
                   viewss1.setVisibility(View.VISIBLE);
                   img_ref.setRotation(90);
               }else{
                   viewss1.setVisibility(View.GONE);
                   tv_ref.setVisibility(View.GONE);
                   img_ref.setRotation(0);
               }

            }
        });
        img_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_dish.getVisibility()==View.GONE){
                    tv_dish.setVisibility(View.VISIBLE);
                    viewss2.setVisibility(View.VISIBLE);
                    img_dish.setRotation(90);
                }else{
                    viewss2.setVisibility(View.GONE);
                    tv_dish.setVisibility(View.GONE);
                    img_dish.setRotation(0);
                }
            }
        });
        img_wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_wash.getVisibility()==View.GONE){
                    tv_wash.setVisibility(View.VISIBLE);
                    viewss3.setVisibility(View.VISIBLE);
                    img_wash.setRotation(90);
                }else{
                    viewss3.setVisibility(View.GONE);
                    tv_wash.setVisibility(View.GONE);
                    img_wash.setRotation(0);
                }
            }
        });
    }
}