package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
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
import com.dukan.dukkan.model.DataModeLanguage;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CheckOutCart;
import com.dukan.dukkan.pojo.PaymentGateway;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOut extends AppCompatActivity {
    TextView tv_address_name,tv_change,tv_address,tv_name,tv_mobile,tv_total,tv_total_price,tv_delivery_fee;
    ImageView check1,check2;
    Boolean checkOne=true;
    String cartTotal;
    String deliveryPrice;
    String[] payments;
    ProgressBar progressBar;
    APIInterface apiInterface;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out);
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
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        RelativeLayout rel_cash =  findViewById(R.id.rel_cash);
        RelativeLayout rel_card =  findViewById(R.id.rel_card);
        Button checkout_button =  findViewById(R.id.checkout_button);
        Button redeem_points_button =  findViewById(R.id.redeem_points_button);
        String extra_str= getIntent().getExtras().getString("extra_str");
        String[] allDatass = extra_str.split("&");
        cartTotal= allDatass[1];
        deliveryPrice= allDatass[2];
        payments= allDatass[3].split("%");
        tv_total_price.setText(cartTotal);
        tv_delivery_fee.setText(deliveryPrice);
        tv_total.setText(""+allDatass[0]);
        /////////////////////////////////////////////////
        ArrayList<PaymentGateway> arrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        for(int i=1;i<payments.length;i++) {
            arrayList.add(new PaymentGateway(Integer.valueOf(payments[i].split("#")[0]), payments[i].split("#")[1], payments[i].split("#")[2]));
            RecyclerPaymentAdapter adapter = new RecyclerPaymentAdapter(getApplicationContext(), arrayList);
            recyclerView.setAdapter(adapter);
        }
        /////////////////////////////////////////////////

        ImageView img_back =  findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOut.this, AllAddressActivity.class));

            }
        });
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrders();
            }
        });
    }
    private void CreateOrders() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("IDIDIDIDIDID "+ID);
        Call<CheckOutCart> callNew = apiInterface.CreateOrderCart(ID,"android");
        callNew.enqueue(new Callback<CheckOutCart>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CheckOutCart> callNew, Response<CheckOutCart> response) {
                CheckOutCart cart = response.body();
                if (cart.status)
                    finish();
                else
                    Toast.makeText(CheckOut.this, cart.message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<CheckOutCart> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }

        });
    }
}