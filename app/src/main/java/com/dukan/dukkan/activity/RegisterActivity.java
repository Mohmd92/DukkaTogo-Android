package com.dukan.dukkan.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.SpinnerAdapter;
import com.dukan.dukkan.fragment.TermsSheetFragment;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Country;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.Register;
import com.dukan.dukkan.pojo.RegisterParameter;
import com.dukan.dukkan.pojo.User;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {
    EditText edit_name,edit_street,edit_mail,edit_password2,edit_password,edit_mobile,edit_postal,edit_city;
    TextView tv_skip,tv_sign_up,tv_forget;
    APIInterface apiInterface;
    ProgressBar progressBar;
    CheckBox checkboxs;
    Spinner spinner_city,spinner_country,spinner_mobile;
    int currentItem = 0;
    String phoneCodes="";
    String country,city="";
    int currentItem2 = 0;
    long countryId=0;
    long cityId=0;
    private SignInButton signInButton;
    GoogleSignInClient mSignInClient;
    private CallbackManager callbackManager;
    FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;
    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        SharedPreferenceManager.getInstance(getBaseContext()).setLoginType("");
        ImageView img_back =findViewById(R.id.img_back);
        LinearLayout liner_facebook =findViewById(R.id.liner_facebook);
        LinearLayout liner_google =findViewById(R.id.liner_google);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();

        progressBar =findViewById(R.id.progressBar);
        edit_name =findViewById(R.id.edit_name);
        edit_street =findViewById(R.id.edit_street);
        edit_postal =findViewById(R.id.edit_postal);
        edit_mail =findViewById(R.id.edit_mail);
        edit_mobile =findViewById(R.id.edit_mobile);
        edit_password =findViewById(R.id.edit_password);
        edit_password2 =findViewById(R.id.edit_password2);
        spinner_country =findViewById(R.id.spinner_country);
        spinner_mobile =findViewById(R.id.spinner_mobile);
        spinner_city =  findViewById(R.id.spinner_city);
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
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        getCountries();
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edit_name.getText().toString()))
                     Toast.makeText(RegisterActivity.this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(edit_street.getText().toString()))
                    Toast.makeText(RegisterActivity.this, getString(R.string.enter_street_building_no), Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(edit_postal.getText().toString()))
                    Toast.makeText(RegisterActivity.this, getString(R.string.enter_postal_code), Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(edit_mail.getText().toString()))
                    Toast.makeText(RegisterActivity.this, getString(R.string.enter_e_mail), Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(edit_mobile.getText().toString()))
                    Toast.makeText(RegisterActivity.this, getString(R.string.enter_mobile_number), Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(edit_password.getText().toString()))
                    Toast.makeText(RegisterActivity.this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(edit_password2.getText().toString()))
                    Toast.makeText(RegisterActivity.this, getString(R.string.enter_confirm_password), Toast.LENGTH_SHORT).show();
                else if(!checkboxs.isChecked())
                    Toast.makeText(RegisterActivity.this, getString(R.string.check_terms), Toast.LENGTH_SHORT).show();
                else
                    registers();
            }
        });
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.CheckInternet), Toast.LENGTH_SHORT).show();
                } else {
                    signInGmail();
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this, gso);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.fbLogin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    private void signInWithFacebook(AccessToken token) {
        if (!isNetworkAvailable()) {
            Toast.makeText(RegisterActivity.this, getString(R.string.CheckInternet), Toast.LENGTH_SHORT).show();
        } else {
            showProgressDialog();
            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            mFirebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                            } else {
                                String uid = task.getResult().getUser().getUid();
                                String name = task.getResult().getUser().getDisplayName();
                                String email = task.getResult().getUser().getEmail();
                                String image = task.getResult().getUser().getPhotoUrl().toString();
                                System.out.println("googgggggle " + uid);
                                System.out.println("googgggggle " + name);
                                System.out.println("googgggggle " + email);
                                System.out.println("googgggggle " + image);
                                SharedPreferenceManager.getInstance(getBaseContext()).set_api_token(uid);
                                SharedPreferenceManager.getInstance(getBaseContext()).setUser_Name(name);
                                SharedPreferenceManager.getInstance(getBaseContext()).set_email(email);
                                SharedPreferenceManager.getInstance(getBaseContext()).setUserImage(image);
                                SharedPreferenceManager.getInstance(getBaseContext()).setLoginType("facebook");
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();


                            }

                        }
                    });
        }
    }

    public void showProgressDialog() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.please_wait));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void getCountries() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Country> callNew = apiInterface.doGetListCountry();
        callNew.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> callNew, Response<Country> response) {
                Log.d("TAG111111",response.code()+"");
                Country resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    List<Country.Datum> datumList = resource.data;
                    Integer[] idCountry=new Integer[datumList.size()];
                    String[] name=new String[datumList.size()];
                    String[] img=new String[datumList.size()];
                    String[] phoneCode=new String[datumList.size()];

                    int i=0;
                    for (Country.Datum datum : datumList) {
                        idCountry[i]=datum.id;
                        name[i]=datum.name;
                        img[i]=datum.iso2;
                        phoneCode[i]=datum.phoneCode;
                        i++;
                    }
                    progressBar.setVisibility(View.GONE);

                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, name, img);
                    spinner_country.setAdapter(spinnerArrayAdapter);

                    SpinnerAdapter spinnerMobileArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, phoneCode, img);
                    spinner_mobile.setAdapter(spinnerMobileArrayAdapter);

                    spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(currentItem == position){
                                return; //do nothing
                            }
                            else {
                                country=name[position];
                                getCities(Long.valueOf(idCountry[position]));
                            }
                            currentItem = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }
            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });
    }
    private void getCities(Long id) {
        progressBar.setVisibility(View.VISIBLE);
        Call<City> callNew = apiInterface.doGetCity(id);
        callNew.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> callNew, Response<City> response) {
                Log.d("TAG111111",response.code()+"");
                City resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    List<City.Datum> datumList = resource.data;
                    Integer[] idCity=new Integer[datumList.size()];
                    String[] name=new String[datumList.size()];
                    String[] img=new String[datumList.size()];
                    int i=0;
                    for (City.Datum datum : datumList) {
                        idCity[i]=datum.id;
                        name[i]=datum.name;
                        img[i]="city";
                        i++;
                    }
                    progressBar.setVisibility(View.GONE);

                    countryId=id;


                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, name, img);
                    spinner_city.setAdapter(spinnerArrayAdapter);

                    spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(currentItem2 == position){
                                return; //do nothing
                            }
                            else {
                                cityId=idCity[position];
                                city=name[position];
                            }
                            currentItem2 = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });
    }
    private void registers() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(), 0);
        }

        progressBar.setVisibility(View.VISIBLE);
        RegisterParameter regisster = new RegisterParameter(edit_name.getText().toString(),edit_mail.getText().toString(),edit_mobile.getText().toString(),edit_password.getText().toString(),edit_password2.getText().toString(),countryId,cityId,edit_name.getText().toString(),edit_street.getText().toString(),edit_postal.getText().toString());
        Call<Register> call1 = apiInterface.register(regisster);
        call1.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                Register register = response.body();
                if(!register.status) {
                    Toast.makeText(RegisterActivity.this, register.message, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(RegisterActivity.this, register.message, Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);
                    SharedPreferenceManager.getInstance(getBaseContext()).set_api_token(register.data.apiToken);
                    SharedPreferenceManager.getInstance(getBaseContext()).setUser_Name(register.data.name);
                    SharedPreferenceManager.getInstance(getBaseContext()).set_email(register.data.email);
                    SharedPreferenceManager.getInstance(getBaseContext()).setCity(city);
                    SharedPreferenceManager.getInstance(getBaseContext()).setCityId(String.valueOf(register.data.cityId));
                    SharedPreferenceManager.getInstance(getBaseContext()).setCountry(country);
                    SharedPreferenceManager.getInstance(getBaseContext()).setCountryId(String.valueOf(register.data.countryId));
                    SharedPreferenceManager.getInstance(getBaseContext()).setUserImage(register.data.image);
                    List<Register.Role> roles = register.data.roles;
                    StringBuilder UserRole= new StringBuilder();
                    for (Register.Role datum : roles) {
                        UserRole.append(datum.name);
                        UserRole.append("*");
                    }
                    SharedPreferenceManager.getInstance(getBaseContext()).setUserType(String.valueOf(UserRole));
                    if(checkboxs.isChecked())
                        SharedPreferenceManager.getInstance(getBaseContext()).setPassword(edit_password.getText().toString());
                    else
                        SharedPreferenceManager.getInstance(getBaseContext()).setPassword("");
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
        //  return  true;
    }

    private void signInGmail() {
        Intent signInIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}