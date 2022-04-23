package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerCategoryAdapter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.OrderStatistics;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yihsian.slider.library.SliderItemView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsActivity extends AppCompatActivity {
    APIInterface apiInterface;
    TextView process_date,process_date2,tv_total_amount,tv_num_orders;
    private Calendar myCalendar;
    private Calendar myCalendar2;
    private Long date_str;
    private Long date_str2;
    BarChart barChart;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        progressBar =  findViewById(R.id.progressBar);
        tv_num_orders = (TextView) findViewById(R.id.tv_num_orders);
        tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);
        process_date = (TextView) findViewById(R.id.process_date);
        process_date2 = (TextView) findViewById(R.id.process_date2);
        barChart = (BarChart) findViewById(R.id.barchart);
        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        Button filter_button =  findViewById(R.id.filter_button);
        ImageView img_back =  findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(process_date.getText().toString()) && !TextUtils.isEmpty(process_date.getText().toString())) {
                    tv_num_orders.setText("");
                    tv_total_amount.setText("");
                    barChart.setVisibility(View.GONE);
                    getStatistics(process_date.getText().toString(), process_date2.getText().toString());
                }
            }
        });
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
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }
        };
        process_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(process_date.getWindowToken(), 0);

                }catch(Exception e){
                    Toast.makeText(StatisticsActivity.this, "1111", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                // TODO Auto-generated method stub
                new DatePickerDialog(StatisticsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        process_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(process_date2.getWindowToken(), 0);
                // TODO Auto-generated method stub
                new DatePickerDialog(StatisticsActivity.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        getStatistics("","");


    }
    private void updateLabel() {
        String myFormat = "yyyy-MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        process_date.setText(sdf.format(myCalendar.getTime()));
        date_str=myCalendar.getTimeInMillis();
    }
    private void updateLabel2() {
        String myFormat = "yyyy-MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        process_date2.setText(sdf.format(myCalendar2.getTime()));
        date_str2=myCalendar2.getTimeInMillis();
    }

    private void getStatistics(String dateFrom,String dateTo) {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Call<OrderStatistics> callNew = apiInterface.GetOrderStatistics(ID,"android",dateFrom,dateTo);
        callNew.enqueue(new Callback<OrderStatistics>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OrderStatistics> callNew, Response<OrderStatistics> response) {
                Log.d("TAG11111ddddd1",response.code()+"");
                OrderStatistics resource = response.body();
                Log.d("TAG11111ddddd1",response.code()+"");
                progressBar.setVisibility(View.GONE);

                Boolean status = resource.status;

                if(status) {
                    barChart.setVisibility(View.VISIBLE);
                    tv_num_orders.setText(""+resource.data.count);
                    tv_total_amount.setText(""+resource.data.total);
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    BarDataSet bardataset = new BarDataSet(entries, "");
                    ArrayList<String> labels = new ArrayList<String>();
                    List<OrderStatistics.Order> ords = resource.data.orders;
                    int indx=0;
                    for (OrderStatistics.Order datum : ords) {
                        labels.add(""+datum.month);
                        entries.add(new BarEntry((float) datum.count, indx));
                        indx++;
                    }

                    BarData data = new BarData(labels, bardataset);
                    barChart.setData(data); // set the data and list of labels into chart
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    barChart.animateY(1000);
                    barChart.setDescription("");  // set the description
                }else
                    Toast.makeText(StatisticsActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<OrderStatistics> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG111111","  e "+t.getMessage());
            }
        });
    }
}