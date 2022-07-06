package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.ProductAdapter;
import com.dukan.dukkan.pojo.OrderDetail;
import com.dukan.dukkan.pojo.OrderToDelevey;
import com.dukan.dukkan.pojo.OrderToDelevey;
import com.dukan.dukkan.pojo.ShowOrder;
import com.dukan.dukkan.util.HorizontalListView;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverOrderDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView tv_date,tv_day,tv_num_products,tv_start_place,tv_arrival_place,tv_price;
    HorizontalListView HorizontalListViewProduct;
    APIInterface apiInterface;
    ProgressBar progressBar;
//    CardView card_point,card_driver;
    float lat,lng;
    int OrderId;
    private GoogleMap mMap;
    String qrcode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_order_details_acticity);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        OrderId= getIntent().getExtras().getInt("OrderId");
        tv_date = findViewById(R.id.tv_date);
        tv_day = findViewById(R.id.tv_day);
        tv_num_products = findViewById(R.id.tv_num_products);
        tv_start_place = findViewById(R.id.tv_start_place);
        tv_arrival_place = findViewById(R.id.tv_arrival_place);
        tv_price = findViewById(R.id.tv_price);
//        card_driver = findViewById(R.id.card_driver);
//        card_point = findViewById(R.id.card_point);
        progressBar = findViewById(R.id.progressBar);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        HorizontalListViewProduct = findViewById(R.id.HorizontalListViewProduct);
        Button but_accept = findViewById(R.id.but_accept);
        Button but_reject = findViewById(R.id.but_reject);
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
        but_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderToDelv();
            }
        });
        but_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.Dialog EndDialog=new Dialog(DriverOrderDetailsActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                EndDialog.setContentView(R.layout.dialog_reject_order);
                EndDialog.setCancelable(false);
                progressBar =  EndDialog.findViewById(R.id.progressBar);
                ImageView img_close =  EndDialog.findViewById(R.id.img_close);
                TextView dialog_ok =  EndDialog.findViewById(R.id.yes_button);
                TextView dialog_cancel =  EndDialog.findViewById(R.id.no_button);
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EndDialog.dismiss();
                    }
                });
                dialog_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderReject(EndDialog);
                    }
                });
                dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       EndDialog.dismiss();
                    }
                });
                EndDialog.show();

            }
        });
        getOrderDetails();
    }
    private void orderReject(android.app.Dialog EndDialog) {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<ShowOrder> callNew = apiInterface.OrderReject(OrderId);
        callNew.enqueue(new Callback<ShowOrder>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ShowOrder> callNew, Response<ShowOrder> response) {
                ShowOrder resource = response.body();
                if(resource.status){
                    finish();
//                    Toast.makeText(DriverOrderDetailsActivity.this, resource.message, Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(DriverOrderDetailsActivity.this, resource.message, Toast.LENGTH_SHORT).show();
                EndDialog.dismiss();
                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<ShowOrder> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
    private void OrderToDelv() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<OrderToDelevey> callNew = apiInterface.OrderToDelevry(OrderId,ID,"android","test message","put",1,1);
        callNew.enqueue(new Callback<OrderToDelevey>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OrderToDelevey> callNew, Response<OrderToDelevey> response) {
                OrderToDelevey resource = response.body();
                if(resource.status){
                    Toast.makeText(DriverOrderDetailsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(DriverOrderDetailsActivity.this, resource.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<OrderToDelevey> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }

    private void getOrderDetails() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<ShowOrder> callNew = apiInterface.OrderDetails(OrderId,"","1",ID,"android");
        callNew.enqueue(new Callback<ShowOrder>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ShowOrder> callNew, Response<ShowOrder> response) {
                ShowOrder resource = response.body();
                if(resource.status){
                    tv_date.setText(resource.data.createdAt.split("T")[0]);
                    tv_num_products.setText(""+resource.data.orderDetails.size());
//               tv_start_place.setText(""+resource.data.store.address);
                    tv_start_place.setText(SharedPreferenceManager.getInstance(getBaseContext()).getAddress());
                    tv_arrival_place.setText(""+resource.data.address.location);
                    tv_price.setText(""+resource.data.total);
                    lat=0;
                    if(resource.data.user.lat!=null)
                        lat=Float.parseFloat(resource.data.user.lat);
                    lng=0;
                    if(resource.data.user.lng!=null)
                        lng=Float.parseFloat(resource.data.user.lng);
                    List<OrderDetail> newProduct = resource.data.orderDetails;
                    qrcode=resource.data.qrCode;

                    ProductAdapter AllProductAdapter = new ProductAdapter(getApplicationContext(),newProduct);
                    HorizontalListViewProduct.setAdapter(AllProductAdapter);
                    AllProductAdapter.notifyDataSetChanged();

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(DriverOrderDetailsActivity.this);
                    if(resource.data.delivery!=null){

                    }else{

                    }

                }else
                    Toast.makeText(DriverOrderDetailsActivity.this, resource.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<ShowOrder> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // below line is use to add marker to each location of our array list.
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker"));

        // below lin is use to zoom our camera on map.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(3.0f));

        // below line is use to move our camera to the specific location.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
    }


}