package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.R;
import com.market.dokan.util.SharedPreferenceManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(SharedPreferenceManager.getInstance(getBaseContext()).getuid()!=null) {
//                    if (!SharedPreferenceManager.getInstance(getBaseContext()).getuid().equals("")) {
                            startActivity(new Intent(Splash.this, MainActivity.class));
//                    }else {
////                        startActivity(new Intent(Splash.this, LoginActivity.class));
//                        finish();
//                    }
//                }else
//                {
////                    startActivity(new Intent(Splash.this, LoginActivity.class));
//                    finish();
//                }

            }
        },500);

    }


}