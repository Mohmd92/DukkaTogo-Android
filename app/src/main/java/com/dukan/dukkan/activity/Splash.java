package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class Splash extends AppCompatActivity {
    String UserRole="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());
        SharedPreferenceManager.getInstance(getApplicationContext()).setFilterDates("");
        if(SharedPreferenceManager.getInstance(getBaseContext()).getUserType()!=null){
            if (!SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name().equals(""))
                UserRole= SharedPreferenceManager.getInstance(getBaseContext()).getUserType();
        }
//        SharedPreferenceManager.getInstance(getBaseContext()).setCountryId("32");
//        SharedPreferenceManager.getInstance(getBaseContext()).setCityId("8550");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name()!=null) {
                    if (!SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name().equals("")){
                        if (UserRole.contains("Merchant"))
                            startActivity(new Intent(Splash.this, MainMerchantActivity.class));
                        else if (UserRole.contains("Delivery"))
                            startActivity(new Intent(Splash.this, MainDriveActivity.class));
                        else
                            startActivity(new Intent(Splash.this, MainActivity.class));
                    }
                   else
                        checkCountry();
                   }else
                    checkCountry();

//                finish();
            }
        },1000);
    }
    private void checkCountry(){
//        Toast.makeText(this, "Test version", Toast.LENGTH_SHORT).show();
//        SharedPreferenceManager.getInstance(getBaseContext()).setUserType("driver");
        SharedPreferenceManager.getInstance(getBaseContext()).set_api_token("");
        if(SharedPreferenceManager.getInstance(getBaseContext()).getCountryId()!=null) {
            if (SharedPreferenceManager.getInstance(getBaseContext()).getCountryId().equals(""))
                startActivity(new Intent(Splash.this, LoginActivity.class));
            else
                startActivity(new Intent(Splash.this, CountryActivity.class));
        }else
            startActivity(new Intent(Splash.this, CountryActivity.class));
    }
}