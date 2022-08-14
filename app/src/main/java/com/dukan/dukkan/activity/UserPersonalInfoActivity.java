package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.Role;
import com.dukan.dukkan.pojo.User;
import com.dukan.dukkan.pojo.UserProfile;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPersonalInfoActivity extends AppCompatActivity {
    ImageView img_profile;
    TextView tv_user_name,tv_edit;
    Spinner spinner_country,spinner_city,spinner_mobile;
    ProgressBar progressBar;
    APIInterface apiInterface;
    int currentItem = 0;
    int currentItem2 = 0;
    String countryId="";
    String cityId="";
    EditText edit_name,edit_street,edit_postal_code,edit_mail,edit_mobile;
    Boolean EditableScreen=false;
    String activty,UserProfile,image_url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);
        UserProfile= getIntent().getExtras().getString("UserProfile");
        activty= getIntent().getExtras().getString("activity");
        String[] userProfileInfo = UserProfile.split("&&");
        ImageView img_back = findViewById(R.id.img_back);
        img_profile =  findViewById(R.id.img_profile);
        img_profile.setClipToOutline(true);
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
         tv_edit =  findViewById(R.id.tv_edit);

        tv_user_name.setText(userProfileInfo[0]);
        edit_name.setText(userProfileInfo[0]);
        edit_street.setText(userProfileInfo[3]);
        edit_postal_code.setText(userProfileInfo[4]);
        edit_mail.setText(userProfileInfo[5]);
        edit_mobile.setText(userProfileInfo[6]);
        countryId=userProfileInfo[1];
        cityId=userProfileInfo[2];
        Picasso.get()
                .load(userProfileInfo[7])
                .into(img_profile);
        image_url=userProfileInfo[7];
        if(EditableScreen){
            tv_edit.setText(getString(R.string.save));
            edit_name.setEnabled(true);
            edit_street.setEnabled(true);
            edit_postal_code.setEnabled(true);
            edit_mail.setEnabled(true);
            edit_mobile.setEnabled(true);
            spinner_country.setEnabled(true);
            spinner_city.setEnabled(true);
            spinner_mobile.setEnabled(true);

        }else {
            tv_edit.setText(getString(R.string.edit));
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
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activty.equals("merchant"))
                    startActivity(new Intent(UserPersonalInfoActivity.this, MerchantProfileActivity.class));
                else if(activty.equals("customer"))
                    startActivity(new Intent(UserPersonalInfoActivity.this, UserProfileActivity.class));
                else
                    startActivity(new Intent(UserPersonalInfoActivity.this, DriverProfileActivity.class));
                finish();
            }
        });
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditableScreen){
                    EditableScreen=true;
                    tv_edit.setText(getString(R.string.save));
                    edit_name.setEnabled(true);
                    edit_street.setEnabled(true);
                    edit_postal_code.setEnabled(true);
                    edit_mail.setEnabled(true);
                    edit_mobile.setEnabled(true);
                    spinner_country.setEnabled(true);
                    spinner_city.setEnabled(true);
                    spinner_mobile.setEnabled(true);
                }else {
                    Save();

                }

                //        SharedPreferenceManager.getInstance(getBaseContext()).setCountry(countryId);
//        SharedPreferenceManager.getInstance(getBaseContext()).setCity(cityId);
            }
        });

        getCountries();
        getCities(Long.valueOf(countryId));
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

                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, name, img);
                    spinner_country.setAdapter(spinnerArrayAdapter);
                    if(selection>=0)
                        spinner_country.setSelection(selection);

                    SpinnerAdapter spinnerMobileArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, phoneCode, img);
                    spinner_mobile.setAdapter(spinnerMobileArrayAdapter);
                    if(selection>=0)
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
                        if(Integer.valueOf(cityId).equals(datum.id))
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
    private void Save() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(), 0);
        }
        progressBar.setVisibility(View.VISIBLE);
        tv_user_name.setText(edit_name.getText());
        UserProfile userProfile = new UserProfile(edit_name.getText().toString(), edit_mail.getText().toString(), countryId, cityId, edit_street.getText().toString(), edit_postal_code.getText().toString(), edit_mobile.getText().toString(), image_url);
        Call<Profile> call1 = apiInterface.updateProfile(userProfile);
        call1.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Profile login = response.body();
                if (login.status.equals(true)) {
                    Toast.makeText(UserPersonalInfoActivity.this, login.message, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    EditableScreen=false;
                    tv_edit.setText(getString(R.string.edit));
                    edit_name.setEnabled(false);
                    edit_street.setEnabled(false);
                    edit_postal_code.setEnabled(false);
                    edit_mail.setEnabled(false);
                    edit_mobile.setEnabled(false);
                    spinner_country.setEnabled(false);
                    spinner_city.setEnabled(false);
                    spinner_mobile.setEnabled(false);
                } else {
                    Toast.makeText(UserPersonalInfoActivity.this, login.message, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }
}