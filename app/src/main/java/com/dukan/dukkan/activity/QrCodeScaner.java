package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.SpinnerAdapter;
import com.dukan.dukkan.pojo.Country;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.pojo.QrCode;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QrCodeScaner extends AppCompatActivity {
    ProgressBar progressBar;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        IntentIntegrator qrScan = new IntentIntegrator(this);
        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.initiateScan();
        qrScan.initiateScan();
        apiInterface = APIClient.getClient(this).create(APIInterface.class);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("GGGGGGGGGGGGGgggggg obj ************");

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainDriveActivity.class);
                startActivity(intent);
                finish();
            } else {
                System.out.println("GGGGGGGGGGGGGgggggg obj "+result.getContents());
                sendQR(result.getContents());
//                Intent intent = new Intent(getApplicationContext(), DriverReadCardActivity.class);//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("QrCode", result.getContents());
//                getApplicationContext().startActivity(intent);
//                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void sendQR(String Qrcodss) {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Call<QrCode> callNew = apiInterface.OrderQR(Qrcodss,ID,"android");
        callNew.enqueue(new Callback<QrCode>() {
            @Override
            public void onResponse(Call<QrCode> callNew, Response<QrCode> response) {
                Log.d("TAG111111",response.code()+"");
                QrCode resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    if(resource.data.status.equals("1"))
                    Toast.makeText(QrCodeScaner.this,getString(R.string.order_has_been_received), Toast.LENGTH_SHORT).show();
                    else if(resource.data.status.equals("2"))
                    Toast.makeText(QrCodeScaner.this,getString(R.string.order_has_been_delivered), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(QrCodeScaner.this, "Error", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<QrCode> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}