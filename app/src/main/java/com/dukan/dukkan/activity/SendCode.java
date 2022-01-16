package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class SendCode extends AppCompatActivity {
    EditText edit1,edit2,edit3,edit4;
    private String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_code);
        edit1 =  findViewById(R.id.edit1);
        edit2 =  findViewById(R.id.edit2);
        edit3 =  findViewById(R.id.edit3);
        edit4 =  findViewById(R.id.edit4);
        ImageView ic_back =  findViewById(R.id.img_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv_resent =  findViewById(R.id.tv_resent);
        tv_resent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(edit1.getText().toString()))
                    edit2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(edit2.getText().toString()))
                    edit3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(edit3.getText().toString()))
                    edit4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(edit4.getText().toString()))
                    pin = edit1.getText().toString() + edit2.getText().toString() + edit3.getText().toString() + edit4.getText().toString();
                    if (SharedPreferenceManager.getInstance(getApplicationContext()).getSMScode().equals(pin))
                        Toast.makeText(SendCode.this, "OK", Toast.LENGTH_SHORT).show();
                 else
                        Toast.makeText(SendCode.this, "Error", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edit2.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                //check if the right key was pressed
                if (keyCode == KeyEvent.KEYCODE_DEL && TextUtils.isEmpty(edit2.getText().toString()))
                {
                    edit1.requestFocus();
                    return true;
                }
                return false;
            }
        });
        edit3.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                //check if the right key was pressed
                if (keyCode == KeyEvent.KEYCODE_DEL && TextUtils.isEmpty(edit3.getText().toString()))
                {
                    edit2.requestFocus();
                    return true;
                }
                return false;
            }
        });
        edit4.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                //check if the right key was pressed
                if (keyCode == KeyEvent.KEYCODE_DEL && TextUtils.isEmpty(edit4.getText().toString()))
                {
                    edit3.requestFocus();
                    return true;
                }
                return false;
            }
        });

    }
}