package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class ChangePassword extends AppCompatActivity {
    EditText edit_password,edit_new_password,edit_new_password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        ImageView img_back =findViewById(R.id.img_back);
        edit_password =findViewById(R.id.edit_password);
        edit_new_password =findViewById(R.id.edit_new_password);
        edit_new_password2 =findViewById(R.id.edit_new_password2);
        Button button =findViewById(R.id.button);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(edit_password.getText().toString()) && (!TextUtils.isEmpty(edit_new_password.getText().toString()) && !TextUtils.isEmpty(edit_new_password2.getText().toString()))){

                }else{

                }

                }
        });
    }
}