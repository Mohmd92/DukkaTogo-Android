package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class SettingActivity extends AppCompatActivity {
    Switch switchs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        CardView card_lang =findViewById(R.id.card_lang);
        CardView card_password =findViewById(R.id.card_password);
        switchs =findViewById(R.id.switchs);
        CardView card_help =findViewById(R.id.card_help);

        card_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, LanguageActivity.class));
            }
        });
        card_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, ChangePassword.class));
            }
        });
        card_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, HelpActivity.class));
            }
        });
        ImageView img_back =findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}