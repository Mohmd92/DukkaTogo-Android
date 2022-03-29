package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.BrandAdapter;
import com.dukan.dukkan.adapter.DeliveryAdapter;
import com.dukan.dukkan.adapter.MostWantedAdapter;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerAddressAdapter;
import com.dukan.dukkan.adapter.RecyclerCartsAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.adapter.SpinnerAdapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.AllAddress;
import com.dukan.dukkan.pojo.Brand;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.CheckOutCart;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Coupon;
import com.dukan.dukkan.pojo.CouponMain;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.yihsian.slider.library.SliderItemView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity  implements RecyclerCartsAdapter.ItemClickListener {
    RecyclerView recyclerView;
    EditText edit_discount;
    public static TextView tv_total_price;
    public static TextView tv_num_products;
    ProgressBar progressBar;
    APIInterface apiInterface;
    List<CartMain.Cart> datumList;
    List<CartMain.PaymentGateway> PaymentGateways;
    String extra_str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        recyclerView = findViewById(R.id.recyclerView);
        edit_discount = findViewById(R.id.edit_discount);
        tv_total_price = findViewById(R.id.tv_total_price);
        tv_num_products = findViewById(R.id.tv_num_products);
        progressBar = findViewById(R.id.progressBar);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);

        ImageView img_back =  findViewById(R.id.img_back);
        Button but_checkout =  findViewById(R.id.but_checkout);
        Button confirm_button =  findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_coupon(edit_discount.getText().toString());
            }
        });
        but_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, CheckOut.class);
                i.putExtra("extra_str", extra_str);
                startActivity(i);
                finish();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getCarts();
    }

    private void check_coupon(String id) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(), 0);
        }
        progressBar.setVisibility(View.VISIBLE);
        Call<CouponMain> callNew = apiInterface.doCheckCoupon(id);
        callNew.enqueue(new Callback<CouponMain>() {
            @Override
            public void onResponse(Call<CouponMain> callNew, Response<CouponMain> response) {
                Log.d("TAG111111",response.code()+"");
                CouponMain resource = response.body();
                Boolean status = resource.status;
                if(status)
                    Toast.makeText(CartActivity.this, ""+resource.data.cart.cartTotal, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<CouponMain> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });
    }
    private void getCarts() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println("KKKKKKKKKKKKK12223 "+ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<CartMain> callNew = apiInterface.doGetListCart(ID);
        callNew.enqueue(new Callback<CartMain>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CartMain> callNew, Response<CartMain> response) {
                CartMain cart = response.body();
                if (cart.status) {
                    datumList = cart.data.carts;
                    PaymentGateways = cart.data.paymentGateway;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerCartsAdapter adapter = new RecyclerCartsAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                    tv_total_price.setText(Integer.toString(cart.data.cartTotal));
                    tv_num_products.setText(Integer.toString(datumList.size()));
                    adapter.setClickListener(CartActivity.this);
                    String payments="";
                    String paymentsss="";
                    for(int i=0;i<PaymentGateways.size();i++){
                        payments=PaymentGateways.get(i).id+"#"+PaymentGateways.get(i).name+"#"+PaymentGateways.get(i).description;
                        paymentsss=paymentsss+"%"+payments;
                    }
                    if(cart.data.deliveryPrice==null)
                        extra_str=cart.data.total+"&"+cart.data.cartTotal+"&0&"+paymentsss;
                    else
                     extra_str=cart.data.total+"&"+cart.data.cartTotal+"&"+cart.data.deliveryPrice+"&"+paymentsss;
                }


                    progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<CartMain> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }

        });
    }
    @Override
    public void onClick(View view, int position) {
//        onClickToAddCard(view,datumList,position);
    }
}