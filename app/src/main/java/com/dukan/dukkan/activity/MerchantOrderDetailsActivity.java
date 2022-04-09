package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.ProductAdapter;
import com.dukan.dukkan.fragment.ChooseDriverSheetFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.model.SliderItem;
import com.dukan.dukkan.pojo.Image;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.pojo.OrderDetail;
import com.dukan.dukkan.pojo.ShowOrder;
import com.dukan.dukkan.util.HorizontalListView;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantOrderDetailsActivity extends AppCompatActivity  implements OnMapReadyCallback {
    TextView tv_date,tv_day,tv_num_products,tv_start_place,tv_arrival_place,tv_price,tv_points,tv_join_date,tv__driver_name;
    HorizontalListView HorizontalListViewProduct;
    ImageView image_driver;
    CardView card_point,card_driver;
    ProgressBar progressBar;
    int OrderId;
    APIInterface apiInterface;
    private GoogleMap mMap;
    float lat,lng;
    String qrcode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_order_details_acticity);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("");
        OrderId= getIntent().getExtras().getInt("OrderId");
        tv_date = findViewById(R.id.tv_date);
        tv_day = findViewById(R.id.tv_day);
        tv_num_products = findViewById(R.id.tv_num_products);
        tv_start_place = findViewById(R.id.tv_start_place);
        tv_arrival_place = findViewById(R.id.tv_arrival_place);
        progressBar = findViewById(R.id.progressBar);
        tv_price = findViewById(R.id.tv_price);
        tv_points = findViewById(R.id.tv_points);
        card_driver = findViewById(R.id.card_driver);
        card_point = findViewById(R.id.card_point);
        tv_join_date = findViewById(R.id.tv_join_date);
        tv__driver_name = findViewById(R.id.tv__driver_name);
        image_driver = findViewById(R.id.image_driver);
        image_driver.setClipToOutline(true);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        HorizontalListViewProduct = findViewById(R.id.HorizontalListViewProduct);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_barcode = toolbar.findViewById(R.id.icon_barcode);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        icon_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                ChooseDriverSheetFragment chooseDriverSheetFragment = new ChooseDriverSheetFragment();
                bundle.putString("qrcode", qrcode);
                chooseDriverSheetFragment.setArguments(bundle);
                chooseDriverSheetFragment.show(getSupportFragmentManager()
                        , chooseDriverSheetFragment.getTag());
            }
        });
        getOrderDetails();
    }
    private void getOrderDetails() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<ShowOrder> callNew = apiInterface.OrderDetails(OrderId,"1","",ID,"android");
        callNew.enqueue(new Callback<ShowOrder>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ShowOrder> callNew, Response<ShowOrder> response) {
                ShowOrder resource = response.body();
                if(resource.status){
                    tv_date.setText(resource.data.createdAt.split("T")[0]);
                    tv_num_products.setText(""+resource.data.orderDetails.size());
//                    tv_start_place.setText(""+resource.data.store.address);
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
                    mapFragment.getMapAsync(MerchantOrderDetailsActivity.this);
                    if(resource.data.delivery!=null){

                    }else{
                        card_driver.setVisibility(View.GONE);
                        card_point.setVisibility(View.GONE);
                    }

                }else
                    Toast.makeText(MerchantOrderDetailsActivity.this, resource.message, Toast.LENGTH_SHORT).show();

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