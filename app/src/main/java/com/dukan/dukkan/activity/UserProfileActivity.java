package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
    UserProfile UserProfile;

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
                startActivity(i);
                finish();
            }
        });

        getProfile();
    }
    private void getProfile() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Profile> callNew = apiInterface.UserProfile();
        callNew.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> callNew, Response<Profile> response) {
                Profile resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    UserProfile=resource.user;
                    tv_user_name.setText(resource.user.name);
                    tv_location.setText(resource.user.address);
                    Picasso.get()
                            .load(resource.user.image)
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