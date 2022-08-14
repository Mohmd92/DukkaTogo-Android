package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
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

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.Register;
import com.dukan.dukkan.pojo.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.dukan.dukkan.util.SharedPreferenceManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {
    EditText edit_mail, edit_password;
    TextView tv_skip, tv_sign_up, tv_forget;
    APIInterface apiInterface;
    ProgressBar progressBar;
    CheckBox checkboxs;
    private SignInButton signInButton;
    private static final int RC_SIGN_IN = 0;
    FirebaseAuth mFirebaseAuth;
    GoogleSignInClient mSignInClient;
    private CallbackManager callbackManager;
    private ProgressDialog mProgressDialog;
    boolean dialogShow = false;
    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 10 * 1000 - SystemClock.elapsedRealtime() % 1000);
            if (!dialogShow && !isNetworkAvailable()) {
                Dialog();
                dialogShow = true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.sign_in);
        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("LoginActivity", "onCreate: " + id);
        FirebaseApp.initializeApp(LoginActivity.this);
        SharedPreferenceManager.getInstance(getBaseContext()).setLoginType("");
        signInButton = findViewById(R.id.sign_in_button);
        progressBar = findViewById(R.id.progressBar);
        edit_mail = findViewById(R.id.edit_mail);
        edit_password = findViewById(R.id.edit_password);
        Button confirm_button = findViewById(R.id.confirm_button);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_forget = findViewById(R.id.tv_forget);
        tv_skip = findViewById(R.id.tv_skip);
        checkboxs = findViewById(R.id.checkboxs);
        mFirebaseAuth = FirebaseAuth.getInstance();
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        if (SharedPreferenceManager.getInstance(getBaseContext()).getPasswordRemember()) {
            System.out.println("9999999999999999999dd " + SharedPreferenceManager.getInstance(getBaseContext()).get_email());
            edit_mail.setText(SharedPreferenceManager.getInstance(getBaseContext()).get_email());
            edit_password.setText(SharedPreferenceManager.getInstance(getBaseContext()).getPassword());
            checkboxs.setChecked(true);
        }
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(edit_mail.getText().toString()) && !TextUtils.isEmpty(edit_password.getText().toString())) {
                    Login();

                } else
                    Toast.makeText(LoginActivity.this, getString(R.string.please_enter_email_password), Toast.LENGTH_SHORT).show();
            }
        });
        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceManager.getInstance(getBaseContext()).set_api_token("");
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
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.CheckInternet), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(LoginActivity.this, getString(R.string.CheckInternet), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
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
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mFirebaseAuth.signInWithCredential(credential)
//                .addOnSuccessListener(this, authResult -> {
//                    SharedPreferenceManager.getInstance(getBaseContext()).setLoginType("google");
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
//                })
//                .addOnFailureListener(this, e -> Toast.makeText(LoginActivity.this, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show());

        //////////
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            String uid = task.getResult().getUser().getUid();
                            String name = task.getResult().getUser().getDisplayName();
                            String email = task.getResult().getUser().getEmail();
                            String phone = task.getResult().getUser().getPhoneNumber();
                            String image = task.getResult().getUser().getPhotoUrl().toString();
                            System.out.println("googgggggle " + uid);
                            System.out.println("googgggggle " + name);
                            System.out.println("googgggggle " + email);
                            System.out.println("googgggggle " + phone);
                            System.out.println("googgggggle " + image);
                            SharedPreferenceManager.getInstance(getBaseContext()).set_api_token(uid);
                            SharedPreferenceManager.getInstance(getBaseContext()).setUser_Name(name);
                            SharedPreferenceManager.getInstance(getBaseContext()).set_email(email);
                            SharedPreferenceManager.getInstance(getBaseContext()).setUserImage(image);
                            SharedPreferenceManager.getInstance(getBaseContext()).setLoginType("google");
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
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

    private void Login() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        progressBar.setVisibility(View.VISIBLE);
        User user = new User(edit_mail.getText().toString(), edit_password.getText().toString());
        Call<Login> call1 = apiInterface.login(user);
        call1.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                if (login.status) {
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "onResponse: "+login.data.apiToken );
                    SharedPreferenceManager.getInstance(getBaseContext()).set_api_token(login.data.apiToken);
                    SharedPreferenceManager.getInstance(getBaseContext()).setUser_Name(login.data.name);
                    SharedPreferenceManager.getInstance(getBaseContext()).set_email(login.data.email);
                    if(login.data.city!=null && login.data.country!=null && login.data.cityId!=null&&login.data.countryId!=null) {
                        SharedPreferenceManager.getInstance(getBaseContext()).setCity(login.data.city.name);
                        SharedPreferenceManager.getInstance(getBaseContext()).setAddress(login.data.city.name);
                        SharedPreferenceManager.getInstance(getBaseContext()).setCountry(login.data.country.name);
                        SharedPreferenceManager.getInstance(getBaseContext()).setCityId(String.valueOf(login.data.cityId));
                        SharedPreferenceManager.getInstance(getBaseContext()).setCountryId(String.valueOf(login.data.countryId));
                    }

                    SharedPreferenceManager.getInstance(getBaseContext()).setUserImage(login.data.image);

                    System.out.println("tttttttttttttttttt " + login.data.apiToken);

                    List<Login.Role> roles = login.data.roles;
                    String UserRole = "";
                    for (Login.Role datum : roles) {
                        UserRole = UserRole + datum.name + "&";
                    }
                    SharedPreferenceManager.getInstance(getBaseContext()).setUserType(UserRole);
                    SharedPreferenceManager.getInstance(getBaseContext()).setPassword(edit_password.getText().toString());
                    if (checkboxs.isChecked())
                        SharedPreferenceManager.getInstance(getBaseContext()).setPasswordRemember(true);
                    else
                        SharedPreferenceManager.getInstance(getBaseContext()).setPasswordRemember(false);
                    if (UserRole.contains("Merchant"))
                        startActivity(new Intent(LoginActivity.this, MainMerchantActivity.class));
                    else if (UserRole.contains("Delivery"))
                        startActivity(new Intent(LoginActivity.this, MainDriveActivity.class));
                    else
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, login.message, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("LoginActivity", "" + t.getMessage());
                call.cancel();
            }
        });
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("https://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                return urlc.getResponseCode() == 200;
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;

    }

    private void Dialog() {
        final Dialog EndDialog = new Dialog(LoginActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        EndDialog.setContentView(R.layout.no_internet);
        EndDialog.setCancelable(false);
        Button button = EndDialog.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected(getApplicationContext())) {
                    dialogShow = false;
                    EndDialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.no_internet_connect), Toast.LENGTH_SHORT).show();
                }
            }
        });
        EndDialog.show();
    }

    @Override
    protected void onResume() {
        handler.post(periodicUpdate);

        super.onResume();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(periodicUpdate);

        super.onStop();
    }

    @Override
    protected void onStart() {
        handler.post(periodicUpdate);

        super.onStart();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(periodicUpdate);

        super.onDestroy();
    }
}