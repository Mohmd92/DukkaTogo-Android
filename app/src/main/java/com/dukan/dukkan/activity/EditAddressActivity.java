package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Address;
import com.dukan.dukkan.pojo.AddressEditParameter;
import com.dukan.dukkan.pojo.AddressParameter;
import com.dukan.dukkan.util.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressActivity extends AppCompatActivity {
    EditText edit_country,edit_city,edit_address,edit_name;
    ProgressBar progressBar;
    APIInterface apiInterface;
    int id_address=0;
    String allData="";
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

            confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(edit_address.getText().toString()) && !TextUtils.isEmpty(edit_name.getText().toString()))
                    EditAddress();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        allData= getIntent().getExtras().getString("allData");
        String[] allDatass = allData.split("&&");
        id_address= Integer.parseInt(allDatass[0]);
        edit_name.setText(""+allDatass[1]);
        edit_address.setText(""+allDatass[2]);
        edit_country.setText(""+allDatass[3]);
        edit_city.setText(""+allDatass[4]);

    }
    private void EditAddress() {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        AddressEditParameter address = new AddressEditParameter(edit_name.getText().toString(),edit_country.getText().toString(),edit_city.getText().toString(),edit_address.getText().toString(),"put",ID,"android");
        Call<Address> call1 = apiInterface.EditAddress(id_address,address);
        call1.enqueue(new Callback<Address>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Address addresss = response.body();
                if (addresss.status) {
//                    startActivity(new Intent(EditAddressActivity.this,AllAddressActivity.class));
                    finish();
                }else
                    Toast.makeText(EditAddressActivity.this, addresss.message, Toast.LENGTH_SHORT).show();

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