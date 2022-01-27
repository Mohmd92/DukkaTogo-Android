package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.Calendar;

public class MerchantWorkingHours extends AppCompatActivity {
    APIInterface apiInterface;
    CardView card_open,card_close;
    Boolean EditableScreen=false;
    TextView tv_day,tv_time_open,tv_time_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_working_hours);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        tv_day = findViewById(R.id.tv_day);
        card_open = findViewById(R.id.card_open);
        card_close = findViewById(R.id.card_close);
        tv_time_open = findViewById(R.id.tv_time_open);
        tv_time_close = findViewById(R.id.tv_time_close);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_edit = toolbar.findViewById(R.id.icon_edit);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MerchantWorkingHours.this, NotificationsActivity.class));
            }
        });
        if(EditableScreen){
            card_open.setEnabled(true);
            card_close.setEnabled(true);
        }else {
            card_open.setEnabled(false);
            card_close.setEnabled(false);
        }
        icon_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditableScreen){
                    card_open.setEnabled(true);
                    card_close.setEnabled(true);
                }else {
                    //save
                }
                //        SharedPreferenceManager.getInstance(getBaseContext()).setCountry(countryId);
//        SharedPreferenceManager.getInstance(getBaseContext()).setCity(cityId);
            }
        });
        card_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MerchantWorkingHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //am pm mode
                    String AM_PM;
                    if (selectedHour>=0&&selectedHour<12){
                        AM_PM=" AM";
                    }else {
                        AM_PM=" PM";
                    }
                        tv_time_open.setText ( selectedHour + ":" + selectedMinute+""+AM_PM );
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        card_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MerchantWorkingHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM;
                        if (selectedHour>=0&&selectedHour<12){
                            AM_PM=" AM";
                        }else {
                            AM_PM=" PM";
                        }
                        tv_time_close.setText ( selectedHour + ":" + selectedMinute+""+AM_PM );
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

}