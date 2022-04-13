package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerMerchantOrder2Adapter;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverOrdersActivity extends AppCompatActivity {
    TextView tv_time_req,tv_year;
    RecyclerView recyclerView;
    private Calendar myCalendar;
    ProgressBar progressBar;
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_orders_acticity);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        LinearLayout linear_time = findViewById(R.id.linear_time);
        tv_time_req = findViewById(R.id.tv_time_req);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        tv_year = findViewById(R.id.tv_year);
        ImageView icon_back =toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu =toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search =toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter =toolbar.findViewById(R.id.icon_filter);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        linear_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DriverOrdersActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        getOrders(getCurrentDateAndTime(),getCurrentDateAndTime());
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        getOrders(sdf.format(myCalendar.getTime()),sdf.format(myCalendar.getTime()));
        tv_year.setText(""+myCalendar.get(Calendar.YEAR));
        tv_time_req.setText(getString(R.string.requests));
    }
    private void getOrders(String dateFrom,String dateTo) {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("TAG111111 ssssss "+ID);
        Call<Order> callNew = apiInterface.GetAllOrders(ID,"android","1" +
                "",dateFrom,dateTo,"","","");
        callNew.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> callNew, Response<Order> response) {
                Log.d("TAG111111",response.code()+"");
                Order resource = response.body();
                Log.d("TAG111111","111111111111111111111111111111111 resource "+resource.status);
                if(resource.status){
                    Log.d("TAG111111","111111111111111111111111111111111ww");
                    List<Order.Datum> datumList = resource.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerMerchantOrder2Adapter adapter = new RecyclerMerchantOrder2Adapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
    public static String getCurrentDateAndTime(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(c);
        return formattedDate;
    }
}