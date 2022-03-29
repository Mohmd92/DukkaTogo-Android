package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.UserProfile;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
    ImageView img_profile;
    TextView tv_user_name,tv_location;
    CardView card_personal_info,card_orders,card_statistics;
    APIInterface apiInterface;
    ProgressBar progressBar;
    String UserProfileString;
    Profile.Data UserProfile;
    LinearLayout linear_no_account,linear_exist_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        img_profile = findViewById(R.id.img_profile);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_location = findViewById(R.id.tv_location);
        card_personal_info = findViewById(R.id.card_personal_info);
        card_orders = findViewById(R.id.card_orders);
        card_statistics = findViewById(R.id.card_statistics);
        linear_exist_account = findViewById(R.id.linear_exist_account);
        linear_no_account = findViewById(R.id.linear_no_account);
        progressBar = findViewById(R.id.progressBar);
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        card_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileString=UserProfile.name+"&&"+UserProfile.countryId+"&&"+UserProfile.cityId+"&&"+UserProfile.address+"&&"+UserProfile.postalCode+"&&"+UserProfile.email+"&&"+UserProfile.mobile+"&&"+UserProfile.image+"&&"+UserProfile.username;
                Intent i = new Intent(UserProfileActivity.this, UserPersonalInfoActivity.class);
                i.putExtra("UserProfile", UserProfileString);
                i.putExtra("activity", "customer");
                startActivity(i);
                finish();
            }
        });
        card_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfileActivity.this, OrdersActivity.class);
                startActivity(i);
            }
        });
        if(SharedPreferenceManager.getInstance(getBaseContext()).get_api_token().equals("")) {
            linear_exist_account.setVisibility(View.GONE);
            linear_no_account.setVisibility(View.VISIBLE);
        }else {
            linear_exist_account.setVisibility(View.VISIBLE);
            linear_no_account.setVisibility(View.GONE);
            getProfile();
        }
    }
    private void getProfile() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Profile> callNew = apiInterface.UserProfile();
        callNew.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> callNew, Response<Profile> response) {
                Profile resource = response.body();
                System.out.println("ds33333333333 "+resource.data.name);
                Boolean status = resource.status;
                if(status) {
                    UserProfile=resource.data;
                    tv_user_name.setText(resource.data.name);
                    tv_location.setText(resource.data.address);
                    Picasso.get()
                            .load(resource.data.image)
                            .into(img_profile);
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}