package com.dukan.dukkan.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.SpinnerAdapter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.CategoryProduct;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Country;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreFilterSheetFragment extends BottomSheetDialogFragment {
    APIInterface apiInterface;
    Spinner spinner_country,spinner_city,spinner_category;
    ProgressBar progressBar;
    int currentItem = 0;
    int currentItem2 = 0;
    int currentItem3 = 0;
    String countryId="";
    String cityId="";
    String categoryId="";
    public StoreFilterSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_filter_store, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        TextView textView00 = (TextView) view.findViewById(R.id.textView00);
        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        spinner_country =  view.findViewById(R.id.spinner_country);
        spinner_category =  view.findViewById(R.id.spinner_category);
        spinner_city =  view.findViewById(R.id.spinner_city);
        progressBar =  view.findViewById(R.id.progressBar);
        getCountries();
        getCategories();
    }
    private void getCategories() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Category> callNew = apiInterface.doGetListCategory();
        callNew.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> callNew, Response<Category> response) {
                Log.d("TAG111111",response.code()+"");
                Category resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    List<CategoryProduct> datumList = resource.data;
                    String[] categ=new String[datumList.size()+1];
                    Integer[] CategoryId=new Integer[datumList.size()+1];
                    categ[0]=getString(R.string.select);
                    CategoryId[0]=0;
                    int i=1;
                    for (CategoryProduct datum : datumList) {
                        categ[i]= datum.name;
                        CategoryId[i]= datum.id;
                        System.out.println("Tvvvvvvvvvvvvvvvvvvv "+datum.id);
                        i++;
                    }
//                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.brand_dropdown,categ);

                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(),R.layout.brand_dropdown,categ){
                        @Override
                        public boolean isEnabled(int position){
                            if(position == 0)
                            {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if(position == 0){
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            }
                            else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.brand_dropdown);
                    spinner_category.setAdapter(spinnerArrayAdapter);
                    spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("Tvvvvvvvvvvvvvvvvvvv p "+position);

                            if(currentItem3 == position){
                                return; //do nothing
                            }
                            else {
                                categoryId=""+CategoryId[position];
                                System.out.println("Tvvvvvvvvvvvvvvvvvvv p "+categ[position]);
                            }
                            currentItem3 = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {
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
                    for (City.Datum datum : datumList) {
                        idCity[i]=datum.id;
                        name[i]=datum.name;
                        img[i]="city";
                        i++;
                    }
                    progressBar.setVisibility(View.GONE);

                    countryId=""+id;


                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getContext(), R.layout.country_item, name, img);
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
                    Integer[] idCountry=new Integer[datumList.size()];
                    String[] name=new String[datumList.size()];
                    String[] img=new String[datumList.size()];
                    int i=0;
                    for (Country.Datum datum : datumList) {
                        idCountry[i]=datum.id;
                        name[i]=datum.name;
                        img[i]=datum.iso2;
                        i++;
                    }
                    progressBar.setVisibility(View.GONE);

                    SpinnerAdapter spinnerArrayAdapter = new SpinnerAdapter(getContext(), R.layout.country_item, name, img);
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
