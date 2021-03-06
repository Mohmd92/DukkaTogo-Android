package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {
    Spinner spinner_country,spinner_city;
    APIInterface apiInterface;
    ProgressBar progressBar;
    int currentItem = 0;
    int currentItem2 = 0;
    String countryId="";
    String cityId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_country);
        progressBar =  findViewById(R.id.progressBar);
        spinner_country =  findViewById(R.id.spinner_country);
        spinner_city =  findViewById(R.id.spinner_city);
        Button confirm_button =  findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!countryId.equals("") && !cityId.equals("")){
                    SharedPreferenceManager.getInstance(getBaseContext()).setCountryId(countryId);
                SharedPreferenceManager.getInstance(getBaseContext()).setCityId(cityId);
                startActivity(new Intent(CountryActivity.this, LoginActivity.class));
                finish();
            }
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        getCountries();

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
                    Integer[] idCity=new Integer[datumList.size()+1];
                    String[] name=new String[datumList.size()+1];
                    String[] img=new String[datumList.size()+1];
                    int i=1;
                    idCity[0]=0;
                    name[0]=getString(R.string.select_city);
                    img[0]="city";
                    for (City.Datum datum : datumList) {
                        idCity[i]=datum.id;
                        name[i]=datum.name;
                        img[i]="city";
                        i++;
                    }
                    progressBar.setVisibility(View.GONE);

                    countryId=""+id;


                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, name, img);
                    spinner_city.setAdapter(spinnerArrayAdapter);

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
                    Integer[] idCountry=new Integer[datumList.size()+1];
                    String[] name=new String[datumList.size()+1];
                    String[] img=new String[datumList.size()+1];
                    int i=1;
                    idCountry[0]=0;
                    name[0]=getString(R.string.select_country);
                    img[0]="";
                    for (Country.Datum datum : datumList) {
                        idCountry[i]=datum.id;
                        name[i]=datum.name;
                        img[i]=datum.iso2;
                        i++;
                    }
                    progressBar.setVisibility(View.GONE);

                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.country_item, name, img);
                    spinner_country.setAdapter(spinnerArrayAdapter);



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
}