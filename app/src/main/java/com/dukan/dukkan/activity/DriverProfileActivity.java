package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.fragment.DriveOrderFilterSheetFragment;

public class DriverProfileActivity extends AppCompatActivity {
    ImageView img_profile,img_profile_true;
    TextView tv_user_name,tv_location,tv_driving_license,tv_affiliation_date;
    CardView card_personal_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profile);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");

        img_profile = findViewById(R.id.img_profile);
        img_profile.setClipToOutline(true);
        img_profile_true = findViewById(R.id.img_profile_true);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_location = findViewById(R.id.tv_location);
        RelativeLayout rel_licences = findViewById(R.id.rel_licences);
        card_personal_info = findViewById(R.id.card_personal_info);
        tv_driving_license = findViewById(R.id.tv_driving_license);
        tv_affiliation_date = findViewById(R.id.tv_affiliation_date);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
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
                startActivity(new Intent(DriverProfileActivity.this, DriverPersonalInfoActivity.class));
            }
        });
        rel_licences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverProfileActivity.this, DriverProfileLicenceActivity.class));
            }
        });
    }
}