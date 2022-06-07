package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.dukan.dukkan.adapter.RecyclerLanguageAdapter;
import com.dukan.dukkan.adapter.RecyclerPaymentAdapter;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.fragment.ThankOrderSheetFragment;
import com.dukan.dukkan.model.DataModeLanguage;
import com.dukan.dukkan.pojo.Address;
import com.dukan.dukkan.pojo.AddressData;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CheckOutCart;
import com.dukan.dukkan.pojo.CheckOuts;
import com.dukan.dukkan.pojo.PaymentGateway;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.yihsian.slider.library.SliderItemView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOut extends AppCompatActivity {
    TextView tv_address_name, tv_change, tv_address, tv_name, tv_mobile, tv_total, tv_total_price, tv_delivery_fee;
    ImageView check1, check2;
    Boolean checkOne = true;
    ProgressBar progressBar;
    APIInterface apiInterface;
    RecyclerView recyclerView;
    int address_id;
    boolean isFirst = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out);
        SharedPreferenceManager.getInstance(getApplicationContext()).setPaymentId(-1);
        tv_address_name = findViewById(R.id.tv_address_name);
        progressBar = findViewById(R.id.progressBar);
        tv_change = findViewById(R.id.tv_change);
        tv_address = findViewById(R.id.tv_address);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        /////////////////////////////////////////////////////
        tv_total = findViewById(R.id.tv_total);
        tv_total_price = findViewById(R.id.tv_total_price);
        tv_delivery_fee = findViewById(R.id.tv_delivery_fee);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        recyclerView = findViewById(R.id.recyclerView);
        RelativeLayout rel_cash = findViewById(R.id.rel_cash);
        RelativeLayout rel_card = findViewById(R.id.rel_card);
        Button checkout_button = findViewById(R.id.checkout_button);
        Button redeem_points_button = findViewById(R.id.redeem_points_button);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceManager.getInstance(getBaseContext()).setSelectedAddress("");
                startActivity(new Intent(CheckOut.this, AllAddressActivity.class));
            }
        });
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrders();
            }
        });
        CheckOuts();
    }

    private void CheckOuts() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("IDIDIDIDIDID " + ID);
        Call<CheckOuts> callNew = apiInterface.DoCheckOut(ID, "android");
        callNew.enqueue(new Callback<CheckOuts>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CheckOuts> callNew, Response<CheckOuts> response) {
                CheckOuts cart = response.body();
                if (cart.status) {
                    tv_total_price.setText(String.valueOf(cart.data.cartTotal));
                    tv_delivery_fee.setText(String.valueOf(cart.data.deliveryPrice));
                    tv_total.setText(String.valueOf(cart.data.total));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerPaymentAdapter adapter = new RecyclerPaymentAdapter(getApplicationContext(), cart.data.paymentGateway);
                    recyclerView.setAdapter(adapter);
                    List<AddressData> slid = cart.data.addresses;
                    for (AddressData datum : slid) {
                        address_id = datum.id;
                        tv_name.setText(datum.name);
                        tv_address.setText(datum.location);
                        tv_mobile.setText(datum.mobile);
                    }
                } else
                    Toast.makeText(CheckOut.this, cart.message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                isFirst = false;
            }

            @Override
            public void onFailure(Call<CheckOuts> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                isFirst = false;
            }

        });
    }

    private void CreateOrders() {

//        Toast.makeText(this, address_id + " ... " +/, Toast.LENGTH_SHORT).show();
        if (SharedPreferenceManager.getInstance(getApplicationContext()).getPaymentId() != -1) {
            @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            progressBar.setVisibility(View.VISIBLE);
            System.out.println("IDIDIDIDIDID " + ID);
            Call<CheckOutCart> callNew = apiInterface.CreateOrderCart(address_id, SharedPreferenceManager.getInstance(getApplicationContext()).getPaymentId(), ID, "android");
            callNew.enqueue(new Callback<CheckOutCart>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<CheckOutCart> callNew, Response<CheckOutCart> response) {
                    CheckOutCart cart = response.body();
                    if (cart != null) {
                        if (cart.status) {
                            progressBar.setVisibility(View.GONE);
                            ThankOrderSheetFragment thhankOrderSheetFragment = new ThankOrderSheetFragment();
                            thhankOrderSheetFragment.show(getSupportFragmentManager()
                                    , thhankOrderSheetFragment.getTag());
//                            Toast.makeText(CheckOut.this, getString(R.string.thank_for_order), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(CheckOut.this, cart.message, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);

                    }


                }

                @Override
                public void onFailure(Call<CheckOutCart> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }

            });

        } else {
            Toast.makeText(CheckOut.this, R.string.select_payment_method, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        if (!isFirst) {
            if (!SharedPreferenceManager.getInstance(getBaseContext()).getSelectedAddress().equals("")) {
                String[] SelectedAddress = SharedPreferenceManager.getInstance(getBaseContext()).getSelectedAddress().split("&");
                address_id = Integer.parseInt(SelectedAddress[0]);
                tv_name.setText(SelectedAddress[1]);
                tv_address.setText(SelectedAddress[2]);
                tv_mobile.setText(SelectedAddress[3]);
                tv_address_name.setText(SelectedAddress[4]);
            }
        }
        super.onResume();
    }
}