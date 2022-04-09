package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;


public class QrCodeScaner extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        IntentIntegrator qrScan = new IntentIntegrator(this);
        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.initiateScan();
        qrScan.initiateScan();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("GGGGGGGGGGGGGgggggg obj ************");

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainDriveActivity.class);
                startActivity(intent);
                finish();
            } else {
                System.out.println("GGGGGGGGGGGGGgggggg obj "+result.getContents());
                Intent intent = new Intent(getApplicationContext(), DriverReadCardActivity.class);//
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("QrCode", result.getContents());
                getApplicationContext().startActivity(intent);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}