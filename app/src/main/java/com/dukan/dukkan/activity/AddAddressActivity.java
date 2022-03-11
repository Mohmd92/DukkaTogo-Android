package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerCartsAdapter;
import com.dukan.dukkan.pojo.Address;
import com.dukan.dukkan.pojo.AddressParameter;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Address;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {
    EditText edit_country,edit_city,edit_address,edit_name;
    ProgressBar progressBar;
    APIInterface apiInterface;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address);
        progressBar = findViewById(R.id.progressBar);
        edit_country = findViewById(R.id.edit_country);
        edit_city = findViewById(R.id.edit_city);
        edit_address = findViewById(R.id.edit_address);
        edit_name = findViewById(R.id.edit_name);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);

        ImageView img_back =  findViewById(R.id.img_back);
        Button confirm_button =  findViewById(R.id.confirm_button);
        if(SharedPreferenceManager.getInstance(getBaseContext()).getCountry()!=null)
            edit_country.setText(""+SharedPreferenceManager.getInstance(getBaseContext()).getCountry());
        if(SharedPreferenceManager.getInstance(getBaseContext()).getCity()!=null)
            edit_city.setText(""+SharedPreferenceManager.getInstance(getBaseContext()).getCity());

            confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(edit_address.getText().toString()) && !TextUtils.isEmpty(edit_name.getText().toString()))
                    AddNewAddress();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void AddNewAddress() {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        AddressParameter address = new AddressParameter(edit_name.getText().toString(),edit_country.getText().toString(),edit_city.getText().toString(),edit_address.getText().toString(),ID,"android");
        Call<Address> call1 = apiInterface.AddAddress(address);
        call1.enqueue(new Callback<Address>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Address addresss = response.body();
                if (addresss.status) {
                    Toast.makeText(AddAddressActivity.this, "done", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(AddAddressActivity.this, addresss.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                call.cancel();
            }
        });
}
}