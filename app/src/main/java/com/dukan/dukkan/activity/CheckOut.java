package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class CheckOut extends AppCompatActivity {
    TextView tv_address_name,tv_change,tv_address,tv_name,tv_mobile,tv_total,tv_total_price,tv_delivery_fee;
    ImageView check1,check2;
    Boolean checkOne=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out);
        tv_address_name = findViewById(R.id.tv_address_name);
        tv_change = findViewById(R.id.tv_change);
        tv_address = findViewById(R.id.tv_address);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        /////////////////////////////////////////////////////
        tv_total = findViewById(R.id.tv_total);
        tv_total_price = findViewById(R.id.tv_total_price);
        tv_delivery_fee = findViewById(R.id.tv_delivery_fee);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        RelativeLayout rel_cash =  findViewById(R.id.rel_cash);
        RelativeLayout rel_card =  findViewById(R.id.rel_card);
        Button checkout_button =  findViewById(R.id.checkout_button);
        Button redeem_points_button =  findViewById(R.id.redeem_points_button);
        ImageView img_back =  findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOut.this, AllAddressActivity.class));

            }
        });
        rel_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   check1.setImageResource(R.drawable.ic_check);
                   check2.setImageResource(R.drawable.ic_unchek);
                   checkOne=true;
            }
        });
        rel_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   check2.setImageResource(R.drawable.ic_check);
                   check1.setImageResource(R.drawable.ic_unchek);
                   checkOne=false;

            }
        });
    }
}