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
import androidx.cardview.widget.CardView;

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

public class DriverPersonalInfoActivity extends AppCompatActivity {
    ImageView img_profile,img_profile_true;
    TextView tv_user_name;
    Spinner spinner_country,spinner_city,spinner_mobile;
    ProgressBar progressBar;
    APIInterface apiInterface;
    int currentItem = 0;
    int currentItem2 = 0;
    String countryId="";
    String cityId="";
    EditText edit_name,edit_street,edit_postal_code,edit_mail,edit_mobile;
    Boolean EditableScreen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_personal_information);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        img_profile =  findViewById(R.id.img_profile);
        img_profile.setClipToOutline(true);
        img_profile_true =  findViewById(R.id.img_profile_true);
        tv_user_name =  findViewById(R.id.tv_user_name);
        spinner_country =  findViewById(R.id.spinner_country);
        spinner_city =  findViewById(R.id.spinner_city);
        spinner_mobile =  findViewById(R.id.spinner_mobile);
        progressBar =  findViewById(R.id.progressBar);

        edit_name =  findViewById(R.id.edit_name);
        edit_street =  findViewById(R.id.edit_street);
        edit_postal_code =  findViewById(R.id.edit_postal_code);
        edit_mail =  findViewById(R.id.edit_mail);
        edit_mobile =  findViewById(R.id.edit_mobile);
        if(EditableScreen){
            edit_name.setEnabled(true);
            edit_street.setEnabled(true);
            edit_postal_code.setEnabled(true);
            edit_mail.setEnabled(true);
            edit_mobile.setEnabled(true);
            spinner_country.setEnabled(true);
            spinner_city.setEnabled(true);
            spinner_mobile.setEnabled(true);
        }else {
            edit_name.setEnabled(false);
            edit_street.setEnabled(false);
            edit_postal_code.setEnabled(false);
            edit_mail.setEnabled(false);
            edit_mobile.setEnabled(false);
            spinner_country.setEnabled(false);
            spinner_city.setEnabled(false);
            spinner_mobile.setEnabled(false);
        }

        apiInterface = APIClient.getClient(this).create(APIInterface.class);
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
        edit_name.setText("Ahmed ");
        icon_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditableScreen){
                    edit_name.setFocusable(true);
                    edit_name.setEnabled(true);
                    edit_street.setEnabled(true);
                    edit_postal_code.setEnabled(true);
                    edit_mail.setEnabled(true);
                    edit_mobile.setEnabled(true);
                    spinner_country.setEnabled(true);
                    spinner_city.setEnabled(true);
                    spinner_mobile.setEnabled(true);
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
        getCountries();
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
                    int selection=0;
                    for (Country.Datum datum : datumList) {
                        if(SharedPreferenceManager.getInstance(getBaseContext()).getCountry().equals(""+datum.id))
                            selection=i;
                        idCountry[i]=datum.id;
                        name[i]=datum.name;
                        img[i]=datum.iso2;
                        phoneCode[i]=datum.phoneCode;
                        i++;
                    }
                    progressBar.setVisibility(View.GONE);

                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, name, img);
                    spinner_country.setAdapter(spinnerArrayAdapter);
                    spinner_country.setSelection(selection);

                    SpinnerAdapter spinnerMobileArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, phoneCode, img);
                    spinner_mobile.setAdapter(spinnerMobileArrayAdapter);
                    spinner_mobile.setSelection(selection);

                    spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(currentItem == position){
                                return; //do nothing
                            }
                            else {
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
                    int selection=0;
                    for (City.Datum datum : datumList) {
                        if(SharedPreferenceManager.getInstance(getBaseContext()).getCity().equals(""+datum.id))
                            selection=i;
                        idCity[i]=datum.id;
                        name[i]=datum.name;
                        img[i]="city";

                        i++;
                    }
                    progressBar.setVisibility(View.GONE);

                    countryId=""+id;


                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, name, img);
                    spinner_city.setAdapter(spinnerArrayAdapter);
                    spinner_city.setSelection(selection);
                    spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(currentItem2 == position){
                                return; //do nothing
                            }
                            else {
                                cityId=""+idCity[position];
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
}