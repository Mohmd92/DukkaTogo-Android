package com.dukan.dukkan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yihsian.slider.library.SliderItemView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    RecyclerView recyclerView;
    APIInterface apiInterface;
    ProgressBar progressBar;

    private ArrayList<LatLng> locationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_store);
        progressBar =findViewById(R.id.progressBar);
        recyclerView =findViewById(R.id.recyclerView);
        ImageView img_back =findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
       getStores();


        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();


    }
    private void getStores() {
        int countryId= Integer.parseInt(SharedPreferenceManager.getInstance(getBaseContext()).getCountryId());
        int cityId= Integer.parseInt(SharedPreferenceManager.getInstance(getBaseContext()).getCityId());
        progressBar.setVisibility(View.VISIBLE);
        Call<MultipleStore> callNew = apiInterface.doGetListStore(0,0);
        callNew.enqueue(new Callback<MultipleStore>() {
            @Override
            public void onResponse(Call<MultipleStore> callNew, Response<MultipleStore> response) {
                Log.d("TAG111111", response.code() + "");
                MultipleStore resource = response.body();
                if(resource.status){
                List<MultipleStore.Datum> datumList = resource.data;

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                RecyclerStoreAdapter adapter = new RecyclerStoreAdapter(getApplicationContext(), datumList);
                recyclerView.setAdapter(adapter);

                for (MultipleStore.Datum datum : datumList) {
                    if (datum.lat != null) {
                        Log.d("TAG111122", " null");
                        locationArrayList.add(new LatLng(Float.parseFloat(datum.lat), Float.parseFloat(datum.lng)));
                    }
                }
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(StoreMapActivity.this);
            }else
                    Toast.makeText(StoreMapActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<MultipleStore> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title("Marker"));

            // below lin is use to zoom our camera on map.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(3.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }
    }
}