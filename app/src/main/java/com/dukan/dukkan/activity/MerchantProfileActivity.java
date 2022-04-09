package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.pojo.UserProfile;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantProfileActivity extends AppCompatActivity {
    ImageView img_profile,img_profile_true;
    TextView tv_user_name,tv_location,tv_driving_license,tv_affiliation_date,tv_store_noe,tv_store_name;
    CardView card_personal_info,card_store_data;
    ProgressBar progressBar;
    APIInterface apiInterface;
    String UserProfileString;
    String StoreInfoData;
    Profile.Data UserProfile;
    Store StoreInff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_profile);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        progressBar = findViewById(R.id.progressBar);
        img_profile = findViewById(R.id.img_profile);
        img_profile.setClipToOutline(true);
        tv_store_noe =  findViewById(R.id.tv_store_noe);
        tv_store_name =  findViewById(R.id.tv_store_name);
        img_profile_true = findViewById(R.id.img_profile_true);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_location = findViewById(R.id.tv_location);
        RelativeLayout rel_licences = findViewById(R.id.rel_licences);
        card_personal_info = findViewById(R.id.card_personal_info);
        card_store_data = findViewById(R.id.card_driving_data);
        tv_driving_license = findViewById(R.id.tv_driving_license);
        tv_affiliation_date = findViewById(R.id.tv_affiliation_date);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        icon_notification.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        card_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileString=UserProfile.name+"&&"+UserProfile.countryId+"&&"+UserProfile.cityId+"&&"+UserProfile.address+"&&"+UserProfile.postalCode+"&&"+UserProfile.email+"&&"+UserProfile.mobile+"&&"+UserProfile.image+"&&"+UserProfile.username;
                Intent i = new Intent(MerchantProfileActivity.this, UserPersonalInfoActivity.class);
                i.putExtra("UserProfile", UserProfileString);
                i.putExtra("activity", "merchant");
                startActivity(i);
                finish();
            }
        });
        card_store_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreInfoData=UserProfile.name+"&&"+UserProfile.image+"&&"+StoreInff.name+"&&"+StoreInff.id+"&&"+StoreInff.address+"&&"+StoreInff.mobile+"&&"+StoreInff.countryId;
                Intent i = new Intent(MerchantProfileActivity.this, StoreInfoActivity.class);
                i.putExtra("StoreInfoData", StoreInfoData);
                startActivity(i);
                finish();
            }
        });
        rel_licences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MerchantProfileActivity.this, DriverProfileLicenceActivity.class));
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
                    UserProfile=resource.data;
                    StoreInff=resource.data.store;
                    tv_user_name.setText(resource.data.name);
                    tv_location.setText(resource.data.address);
                    tv_store_name.setText(resource.data.store.name);
                    tv_store_noe.setText(""+resource.data.store.id);
                    Picasso.get()
                            .load(resource.data.image)
                            .into(img_profile);
                }
                if(resource.data.status.equals("1"))
                    img_profile_true.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}