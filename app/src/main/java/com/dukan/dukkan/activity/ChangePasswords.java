package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.ChangePassParameter;
import com.dukan.dukkan.pojo.ChangePassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswords extends AppCompatActivity {
    EditText edit_password,edit_new_password,edit_new_password2;
    APIInterface apiInterface;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        ImageView img_back =findViewById(R.id.img_back);
        edit_password =findViewById(R.id.edit_password);
        edit_new_password =findViewById(R.id.edit_new_password);
        edit_new_password2 =findViewById(R.id.edit_new_password2);
        progressBar =findViewById(R.id.progressBar);
        Button button =findViewById(R.id.button);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
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
                    ChangePassword();
                }else{
                    Toast.makeText(ChangePasswords.this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
                }

                }
        });
    }

    private void ChangePassword() {
        progressBar.setVisibility(View.VISIBLE);
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(), 0);
        }
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ChangePassParameter changePassParameter = new ChangePassParameter(edit_password.getText().toString(),edit_new_password.getText().toString(),edit_new_password2.getText().toString());
        Call<ChangePassword> call1 = apiInterface.DoChangePassword(changePassParameter);
        call1.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                ChangePassword ChangePassword = response.body();
                if(ChangePassword.status.equals(true)) {
                    Toast.makeText(ChangePasswords.this, ChangePassword.message, Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(ChangePasswords.this, ChangePassword.message, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                call.cancel();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}