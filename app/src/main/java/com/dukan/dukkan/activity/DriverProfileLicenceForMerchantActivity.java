package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.RequestStatus;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverProfileLicenceForMerchantActivity extends AppCompatActivity {
    ImageView img_mot,img_licence,img_profile,img_transport,img_transport2;
    TextView tv_user_name,tv_driving_license,tv_affiliation_date,tv_edit,tv_edit2;
    ProgressBar progressBar;
    APIInterface apiInterface;
    EditText edit_license,edit_affiliation_date;
    Boolean EditableScreen=false;
    String UserProfile;
    int delivery_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profile_licences);
        Button invite_button = findViewById(R.id.invite_button);
        if(getIntent().getExtras().getString("join").equals("true"))
            invite_button.setVisibility(View.VISIBLE);
        img_profile =  findViewById(R.id.img_profile);
        img_profile.setClipToOutline(true);
        img_licence =  findViewById(R.id.img_licence);
        img_mot =  findViewById(R.id.img_mot);
        img_transport =  findViewById(R.id.img_transport);
        img_transport2 =  findViewById(R.id.img_transport2);
        tv_edit =  findViewById(R.id.tv_edit);
        tv_edit2 =  findViewById(R.id.tv_edit2);
        tv_user_name =  findViewById(R.id.tv_user_name);
        tv_driving_license =  findViewById(R.id.tv_driving_license);
        tv_affiliation_date =  findViewById(R.id.tv_affiliation_date);
        progressBar =  findViewById(R.id.progressBar);
        ImageView icon_back =  findViewById(R.id.icon_back);
        ImageView icon_edit =  findViewById(R.id.icon_edit);

        edit_license =  findViewById(R.id.edit_license);
        edit_affiliation_date =  findViewById(R.id.edit_affiliation_date);
        if(EditableScreen){
            icon_edit.setImageResource(R.drawable.ic_tick_square);
            tv_edit.setVisibility(View.VISIBLE);
            tv_edit2.setVisibility(View.VISIBLE);
            img_transport.setVisibility(View.VISIBLE);
            img_transport2.setVisibility(View.VISIBLE);
            edit_license.setEnabled(true);
            edit_affiliation_date.setEnabled(true);
        }else {
            icon_edit.setImageResource(R.drawable.ic_edit_square);
            edit_license.setEnabled(false);
            edit_affiliation_date.setEnabled(false);
            tv_edit.setVisibility(View.GONE);
            tv_edit2.setVisibility(View.GONE);
            img_transport.setVisibility(View.GONE);
            img_transport2.setVisibility(View.GONE);
        }
        invite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestDelivery();
            }
        });
        icon_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditableScreen){
                    EditableScreen=true;
                    icon_edit.setImageResource(R.drawable.ic_tick_square);
                    tv_edit.setVisibility(View.VISIBLE);
                    tv_edit2.setVisibility(View.VISIBLE);
                    img_transport.setVisibility(View.VISIBLE);
                    img_transport2.setVisibility(View.VISIBLE);
                    edit_license.setEnabled(true);
                    edit_affiliation_date.setEnabled(true);
                }else {
                    EditableScreen=false;
                    icon_edit.setImageResource(R.drawable.ic_edit_square);
                    edit_license.setEnabled(false);
                    edit_affiliation_date.setEnabled(false);
                    tv_edit.setVisibility(View.GONE);
                    tv_edit2.setVisibility(View.GONE);
                    img_transport.setVisibility(View.GONE);
                    img_transport2.setVisibility(View.GONE);
                    save();
                }
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        UserProfile= getIntent().getExtras().getString("UserProfile");
        String[] userProfileInfo = UserProfile.split("&&");

        tv_user_name.setText(userProfileInfo[0]);
        tv_driving_license.setText(userProfileInfo[2]);
//        tv_affiliation_date.setText(userProfileInfo[3]);
        edit_license.setText(userProfileInfo[2]);
        edit_affiliation_date.setText(userProfileInfo[3]);
        delivery_id=Integer.parseInt(userProfileInfo[6]);
        System.out.println("item111111111111111 "+userProfileInfo[6]);

        Picasso.get()
                .load(userProfileInfo[1])
                .into(img_profile);
        Picasso.get()
                .load(userProfileInfo[4])
                .into(img_licence);
        Picasso.get()
                .load(userProfileInfo[5])
                .into(img_mot);
    }
    void save(){
//        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();

    }
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
                    Toast.makeText(DriverProfileLicenceForMerchantActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(DriverProfileLicenceForMerchantActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();
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