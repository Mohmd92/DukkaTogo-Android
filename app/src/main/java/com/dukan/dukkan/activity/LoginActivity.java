package com.dukan.dukkan.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.dukan.dukkan.util.SharedPreferenceManager;
public class LoginActivity extends AppCompatActivity {
    EditText edit_mail,edit_password;
    TextView tv_skip,tv_sign_up,tv_forget;
    APIInterface apiInterface;
    ProgressBar progressBar;
    CheckBox checkboxs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        progressBar =findViewById(R.id.progressBar);
        edit_mail =findViewById(R.id.edit_mail);
        edit_password =findViewById(R.id.edit_password);
        Button confirm_button =findViewById(R.id.confirm_button);
        tv_sign_up =findViewById(R.id.tv_sign_up);
        tv_forget =findViewById(R.id.tv_forget);
        tv_skip =findViewById(R.id.tv_skip);
        checkboxs =findViewById(R.id.checkboxs);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(edit_mail.getText().toString()) && !TextUtils.isEmpty(edit_password.getText().toString())){
                    Login();
                }else
                    Toast.makeText(LoginActivity.this, getString(R.string.please_enter_email_password), Toast.LENGTH_SHORT).show();
            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });
    }
    private void Login() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                   getCurrentFocus().getWindowToken(), 0);
        }
        progressBar.setVisibility(View.VISIBLE);
        User user = new User(edit_mail.getText().toString(),edit_password.getText().toString());
        Call<Login> call1 = apiInterface.login(user);
        call1.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                if(login.status.equals(false)) {
                    Toast.makeText(LoginActivity.this, login.message, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.GONE);
                    SharedPreferenceManager.getInstance(getBaseContext()).set_api_token(login.user.apiToken);
                    SharedPreferenceManager.getInstance(getBaseContext()).setUser_Name(login.user.name);
                    SharedPreferenceManager.getInstance(getBaseContext()).set_email(login.user.email);
                    if(checkboxs.isChecked())
                        SharedPreferenceManager.getInstance(getBaseContext()).setPassword(edit_password.getText().toString());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }

}