package com.dukan.dukkan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.SpinnerAdapter;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Country;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverProfileLicenceActivity extends AppCompatActivity {
    ImageView img_profile,img_transport,img_transport2;
    TextView tv_user_name,tv_driving_license,tv_affiliation_date,tv_edit,tv_edit2;
    ProgressBar progressBar;
    APIInterface apiInterface;
    EditText edit_license,edit_affiliation_date;
    Boolean EditableScreen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profile_licences);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        img_profile =  findViewById(R.id.img_profile);
        img_profile.setClipToOutline(true);
        img_transport =  findViewById(R.id.img_transport);
        img_transport2 =  findViewById(R.id.img_transport2);
        tv_edit =  findViewById(R.id.tv_edit);
        tv_edit2 =  findViewById(R.id.tv_edit2);
        tv_user_name =  findViewById(R.id.tv_user_name);
        tv_driving_license =  findViewById(R.id.tv_driving_license);
        tv_affiliation_date =  findViewById(R.id.tv_affiliation_date);
        progressBar =  findViewById(R.id.progressBar);

        edit_license =  findViewById(R.id.edit_license);
        edit_affiliation_date =  findViewById(R.id.edit_affiliation_date);
        if(EditableScreen){
            tv_edit.setVisibility(View.VISIBLE);
            tv_edit2.setVisibility(View.VISIBLE);
            img_transport.setVisibility(View.VISIBLE);
            img_transport2.setVisibility(View.VISIBLE);
            edit_license.setEnabled(true);
            edit_affiliation_date.setEnabled(true);
        }else {
            edit_license.setEnabled(false);
            edit_affiliation_date.setEnabled(false);
            tv_edit.setVisibility(View.GONE);
            tv_edit2.setVisibility(View.GONE);
            img_transport.setVisibility(View.GONE);
            img_transport2.setVisibility(View.GONE);
        }
        apiInterface = APIClient.getClient().create(APIInterface.class);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_edit = toolbar.findViewById(R.id.icon_edit);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit_license.setText("Ahmed ");
        icon_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditableScreen){
                    edit_license.setFocusable(true);
                    edit_license.setEnabled(true);
                    edit_affiliation_date.setEnabled(true);
                    tv_edit.setVisibility(View.VISIBLE);
                    tv_edit2.setVisibility(View.VISIBLE);
                    img_transport.setVisibility(View.VISIBLE);
                    img_transport2.setVisibility(View.VISIBLE);
                }else {
                    //save
                }
                //        SharedPreferenceManager.getInstance(getBaseContext()).setCountry(countryId);
//        SharedPreferenceManager.getInstance(getBaseContext()).setCity(cityId);
            }
        });
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

}