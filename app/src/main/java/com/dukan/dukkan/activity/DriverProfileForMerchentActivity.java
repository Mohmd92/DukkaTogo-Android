package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerDriversAdapter;
import com.dukan.dukkan.pojo.Driver;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.RequestStatus;
import com.dukan.dukkan.pojo.UserOrder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverProfileForMerchentActivity extends AppCompatActivity {
    ImageView img_profile,img_profile_true;
    TextView tv_user_name,tv_location,tv_driving_license,tv_affiliation_date;
    CardView card_personal_info,card_driving_data;
    ProgressBar progressBar;
    APIInterface apiInterface;
    Profile.Data UserProfile;
    int delivery_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profile);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        img_profile = findViewById(R.id.img_profile);
        img_profile.setClipToOutline(true);
        img_profile_true = findViewById(R.id.img_profile_true);
        tv_user_name = findViewById(R.id.tv_user_name);
        progressBar = findViewById(R.id.progressBar);
        tv_location = findViewById(R.id.tv_location);
        RelativeLayout rel_licences = findViewById(R.id.rel_licences);
        Button invite_button = findViewById(R.id.invite_button);
        if(getIntent().getExtras().getString("join").equals("true"))
            invite_button.setVisibility(View.VISIBLE);
        card_personal_info = findViewById(R.id.card_personal_info);
        card_driving_data = findViewById(R.id.card_driving_data);
        tv_driving_license = findViewById(R.id.tv_driving_license);
        tv_affiliation_date = findViewById(R.id.tv_affiliation_date);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        ImageView icon_buy = toolbar.findViewById(R.id.icon_buy);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        icon_buy.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        invite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestDelivery();
            }
        });
        card_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(DriverProfileActivity.this, DriverPersonalInfoActivity.class));
                String UserProfileString=UserProfile.name+"&&"+UserProfile.countryId+"&&"+UserProfile.cityId+"&&"+UserProfile.address+"&&"+UserProfile.postalCode+"&&"+UserProfile.email+"&&"+UserProfile.mobile+"&&"+UserProfile.image+"&&"+UserProfile.username;
                Intent i = new Intent(DriverProfileForMerchentActivity.this, UserPersonalInfoActivity.class);
                i.putExtra("UserProfile", UserProfileString);
                i.putExtra("activity", "delivery");
                startActivity(i);
                finish();
            }
        });
        card_driving_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserProfileString=UserProfile.name+"&&"+UserProfile.image+"&&"+UserProfile.licenseNumber+"&&"+"null"+"&&"+UserProfile.licensePicture+"&&"+UserProfile.vehiclePicture;
                Intent i = new Intent(DriverProfileForMerchentActivity.this, DriverProfileLicenceActivity.class);
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
                    UserProfile=resource.data;
//                    StoreInff=resource.data.store;
                    tv_user_name.setText(resource.data.name);
                    tv_location.setText(resource.data.address);
                    if(resource.data.licenseNumber!=null)
                        tv_driving_license.setText(resource.data.licenseNumber);
                    tv_affiliation_date.setText("");
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
//    private void getProfile() {
//        String allData= getIntent().getExtras().getString("allData");
//        String[] allDatass = allData.split("&&");
//        delivery_id=Integer.parseInt(allDatass[0]);
//        tv_user_name.setText(""+allDatass[1]);
//        tv_location.setText(""+allDatass[2]);
//        if(allDatass[3]!=null)
//            tv_driving_license.setText(allDatass[3]);
//        Picasso.get()
//                .load(allDatass[4])
//                .into(img_profile);
//        if(allDatass[5].equals("1"))
//            img_profile_true.setVisibility(View.VISIBLE);
//
//    }
    private void RequestDelivery() {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Call<RequestStatus> callNew = apiInterface.RequestDelivery(delivery_id);
        callNew.enqueue(new Callback<RequestStatus>() {
            @Override
            public void onResponse(Call<RequestStatus> callNew, Response<RequestStatus> response) {
                Log.d("TAG111111",response.code()+"");
                RequestStatus resource = response.body();
                if(resource.status) {
                    finish();
                    Toast.makeText(DriverProfileForMerchentActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(DriverProfileForMerchentActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<RequestStatus> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
}