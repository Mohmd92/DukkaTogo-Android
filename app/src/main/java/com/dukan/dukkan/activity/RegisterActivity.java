package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.fragment.TermsSheetFragment;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.User;
import com.dukan.dukkan.util.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edit_name,edit_street,edit_mail,edit_password2,edit_password,edit_mobile,edit_postal,edit_city;
    TextView tv_skip,tv_sign_up,tv_forget;
    APIInterface apiInterface;
    ProgressBar progressBar;
    CheckBox checkboxs;
    Spinner spinner_country,spinner_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ImageView img_back =findViewById(R.id.img_back);
        LinearLayout liner_facebook =findViewById(R.id.liner_facebook);
        LinearLayout liner_google =findViewById(R.id.liner_google);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressBar =findViewById(R.id.progressBar);





        edit_name =findViewById(R.id.edit_name);
        edit_street =findViewById(R.id.edit_street);
        edit_postal =findViewById(R.id.edit_postal);
        edit_mail =findViewById(R.id.edit_mail);
        edit_mobile =findViewById(R.id.edit_mobile);
        edit_password =findViewById(R.id.edit_password);
        edit_password2 =findViewById(R.id.edit_password2);
        edit_city =findViewById(R.id.edit_city);
        spinner_country =findViewById(R.id.spinner_country);
        spinner_mobile =findViewById(R.id.spinner_mobile);

        Button confirm_button =findViewById(R.id.confirm_button);
        checkboxs =findViewById(R.id.checkboxs);

        checkboxs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    TermsSheetFragment termsSheetFragment = new TermsSheetFragment();
                    termsSheetFragment.show(getSupportFragmentManager()
                            , termsSheetFragment.getTag());
                }
                else{
                    // Do your coding
                }
            }
        });
        apiInterface = APIClient.getClient().create(APIInterface.class);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "hhhhhhhh", Toast.LENGTH_SHORT).show();
//                if (!TextUtils.isEmpty(edit_mail.getText().toString()) && !TextUtils.isEmpty(edit_password.getText().toString())){
//                    Login();
//                }else
//                    Toast.makeText(RegisterActivity.this, getString(R.string.please_enter_email_password), Toast.LENGTH_SHORT).show();
            }
        });

    }
//    private void Login() {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        if (getCurrentFocus() != null) {
//            inputMethodManager.hideSoftInputFromWindow(
//                   getCurrentFocus().getWindowToken(), 0);
//        }
//        progressBar.setVisibility(View.VISIBLE);
//        User user = new User(edit_mail.getText().toString(),edit_password.getText().toString());
//        Call<Login> call1 = apiInterface.login(user);
//        call1.enqueue(new Callback<Login>() {
//            @Override
//            public void onResponse(Call<Login> call, Response<Login> response) {
//                Login login = response.body();
//                if(login.status.equals(false)) {
//                    Toast.makeText(RegisterActivity.this, login.message, Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//                }else {
//                    progressBar.setVisibility(View.GONE);
//                    SharedPreferenceManager.getInstance(getBaseContext()).set_api_token(login.user.apiToken);
//                    SharedPreferenceManager.getInstance(getBaseContext()).setUser_Name(login.user.name);
//                    SharedPreferenceManager.getInstance(getBaseContext()).set_email(login.user.email);
//                    if(checkboxs.isChecked())
//                        SharedPreferenceManager.getInstance(getBaseContext()).setPassword(edit_password.getText().toString());
//                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Login> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                call.cancel();
//            }
//        });
//    }

}