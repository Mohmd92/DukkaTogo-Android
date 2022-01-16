package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class ForgetPassword extends AppCompatActivity {
    EditText edit_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        edit_mail =  findViewById(R.id.edit_mail);
        ImageView ic_back =  findViewById(R.id.img_back);
        Button button =  findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceManager.getInstance(getApplicationContext()).setSMScode("1234");
                startActivity(new Intent(ForgetPassword.this, SendCode.class));
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

    }

}