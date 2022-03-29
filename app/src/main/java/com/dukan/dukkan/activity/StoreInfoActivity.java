package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.SpinnerAdapter;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Country;
import com.dukan.dukkan.pojo.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreInfoActivity extends AppCompatActivity {
    ImageView img_profile,image_edit;
    TextView tv_user_name;
    ProgressBar progressBar;
    APIInterface apiInterface;
    EditText edit_name,edit_store_no,edit_store_location,edit_mobile;
    Boolean EditableScreen=false;
    String StoreProfile,image_url="";
    Spinner spinner_mobile;
    String countryId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_information);
        StoreProfile= getIntent().getExtras().getString("StoreInfoData");
        String[] StoreProfileInfo = StoreProfile.split("&&");
        ImageView img_back = findViewById(R.id.img_back);
        img_profile =  findViewById(R.id.img_profile);
        img_profile.setClipToOutline(true);
        tv_user_name =  findViewById(R.id.tv_user_name);

        spinner_mobile =  findViewById(R.id.spinner_mobile);
        progressBar =  findViewById(R.id.progressBar);

        edit_name =  findViewById(R.id.edit_name);
        edit_store_no =  findViewById(R.id.edit_store_no);
        edit_store_location =  findViewById(R.id.edit_store_location);
        edit_mobile =  findViewById(R.id.edit_mobile);
        image_edit =  findViewById(R.id.image_edit);
        tv_user_name.setText(StoreProfileInfo[0]);
        edit_name.setText(StoreProfileInfo[2]);
        edit_store_no.setText(StoreProfileInfo[3]);
        edit_store_location.setText(StoreProfileInfo[4]);
        edit_mobile.setText(StoreProfileInfo[5]);
        countryId=StoreProfileInfo[6];

        Picasso.get()
                .load(StoreProfileInfo[1])
                .into(img_profile);
        image_url=StoreProfileInfo[1];
        if(EditableScreen){
            image_edit.setImageResource(R.drawable.ic_tick_square);
            edit_name.setEnabled(true);
            edit_store_no.setEnabled(true);
            edit_store_location.setEnabled(true);
            edit_mobile.setEnabled(true);
            spinner_mobile.setEnabled(true);
        }else {
            image_edit.setImageResource(R.drawable.ic_edit_square);
            edit_name.setEnabled(false);
            edit_store_no.setEnabled(false);
            edit_store_location.setEnabled(false);
            edit_mobile.setEnabled(false);
            spinner_mobile.setEnabled(false);
        }

        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(StoreInfoActivity.this, MerchantProfileActivity.class));
                finish();
            }
        });
        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditableScreen){
                    EditableScreen=true;
                    image_edit.setImageResource(R.drawable.ic_edit_square);
                    edit_name.setEnabled(true);
                    edit_store_no.setEnabled(true);
                    edit_store_location.setEnabled(true);
                    edit_mobile.setEnabled(true);
                    spinner_mobile.setEnabled(true);
                }else {
                    Save();

                }

                //        SharedPreferenceManager.getInstance(getBaseContext()).setCountry(countryId);
//        SharedPreferenceManager.getInstance(getBaseContext()).setCity(cityId);
            }
        });

        getMobile();
    }
    private void getMobile() {
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
                    int selection=-1;
                    for (Country.Datum datum : datumList) {
                        if(Integer.valueOf(countryId).equals(datum.id))
                            selection = i;

                        idCountry[i]=datum.id;
                        name[i]=datum.name;
                        img[i]=datum.iso2;
                        phoneCode[i]=datum.phoneCode;
                        i++;
                    }
                    progressBar.setVisibility(View.GONE);
                    System.out.println("555555555555 "+selection);


                    SpinnerAdapter spinnerMobileArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, phoneCode, img);
                    spinner_mobile.setAdapter(spinnerMobileArrayAdapter);
                    if(selection>=0)
                        spinner_mobile.setSelection(selection);



                }
            }
            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void Save() {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        if (getCurrentFocus() != null) {
//            inputMethodManager.hideSoftInputFromWindow(
//                    getCurrentFocus().getWindowToken(), 0);
//        }
//        progressBar.setVisibility(View.VISIBLE);
//        StoreProfile StoreProfile = new StoreProfile(edit_name.getText().toString(), edit_mail.getText().toString(), countryId, cityId, edit_store_no.getText().toString(), edit_store_location.getText().toString(), edit_mobile.getText().toString(), image_url);
//        Call<Profile> call1 = apiInterface.updateProfile(StoreProfile);
//        call1.enqueue(new Callback<Profile>() {
//            @Override
//            public void onResponse(Call<Profile> call, Response<Profile> response) {
//                Profile login = response.body();
//                if (login.status.equals(true)) {
//                    Toast.makeText(StoreInfoActivity.this, login.message, Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//                    EditableScreen=false;
//                    tv_edit.setText(getString(R.string.edit));
//                    edit_name.setEnabled(false);
//                    edit_store_no.setEnabled(false);
//                    edit_store_location.setEnabled(false);
//                    edit_mail.setEnabled(false);
//                    edit_mobile.setEnabled(false);
//                    spinner_country.setEnabled(false);
//                    spinner_city.setEnabled(false);
//                    spinner_mobile.setEnabled(false);
//                } else {
//                    Toast.makeText(StoreInfoActivity.this, login.message, Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Profile> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                call.cancel();
//            }
//        });
    }
}