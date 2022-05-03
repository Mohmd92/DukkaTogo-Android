package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.pojo.Address;
import com.dukan.dukkan.pojo.AddressEditParameter;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.StoreTimeWork;
import com.dukan.dukkan.pojo.StoreTimes;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantWorkingHours extends AppCompatActivity {
    APIInterface apiInterface;
    CardView card_open,card_close;
    CardView card_open2,card_close2;
    CardView card_open3,card_close3;
    CardView card_open4,card_close4;
    CardView card_open5,card_close5;
    CardView card_open6,card_close6;
    CardView card_open7,card_close7;
//    Boolean EditableScreen=false;
    TextView tv_day,tv_time_open,tv_time_close;
    TextView tv_time_open2,tv_time_close2;
    TextView tv_time_open3,tv_time_close3;
    TextView tv_time_open4,tv_time_close4;
    TextView tv_time_open5,tv_time_close5;
    TextView tv_time_open6,tv_time_close6;
    TextView tv_time_open7,tv_time_close7;
    ProgressBar progressBar;
    SwitchCompat swCustom,swCustom2,swCustom3,swCustom4,swCustom5,swCustom6,swCustom7;
    int sw=0,sw2=0,sw3=0,sw4=0,sw5=0,sw6=0,sw7=0;
    int storeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_working_hours);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        progressBar = findViewById(R.id.progressBar);
        tv_day = findViewById(R.id.tv_day);
        card_open = findViewById(R.id.card_open);
        card_close = findViewById(R.id.card_close);
        tv_time_open = findViewById(R.id.tv_time_open);
        tv_time_close = findViewById(R.id.tv_time_close);

        card_open2 = findViewById(R.id.card_open2);
        card_close2 = findViewById(R.id.card_close2);
        tv_time_open2 = findViewById(R.id.tv_time_open2);
        tv_time_close2 = findViewById(R.id.tv_time_close2);

        card_open3 = findViewById(R.id.card_open3);
        card_close3 = findViewById(R.id.card_close3);
        tv_time_open3 = findViewById(R.id.tv_time_open3);
        tv_time_close3 = findViewById(R.id.tv_time_close3);

        card_open4 = findViewById(R.id.card_open4);
        card_close4 = findViewById(R.id.card_close4);
        tv_time_open4 = findViewById(R.id.tv_time_open4);
        tv_time_close4 = findViewById(R.id.tv_time_close4);

        card_open5 = findViewById(R.id.card_open5);
        card_close5 = findViewById(R.id.card_close5);
        tv_time_open5 = findViewById(R.id.tv_time_open5);
        tv_time_close5 = findViewById(R.id.tv_time_close5);

        card_open6 = findViewById(R.id.card_open6);
        card_close6 = findViewById(R.id.card_close6);
        tv_time_open6 = findViewById(R.id.tv_time_open6);
        tv_time_close6 = findViewById(R.id.tv_time_close6);

        card_open7 = findViewById(R.id.card_open7);
        card_close7 = findViewById(R.id.card_close7);
        tv_time_open7 = findViewById(R.id.tv_time_open7);
        tv_time_close7 = findViewById(R.id.tv_time_close7);

        swCustom = findViewById(R.id.swCustom);
        swCustom2 = findViewById(R.id.swCustom2);
        swCustom3 = findViewById(R.id.swCustom3);
        swCustom4 = findViewById(R.id.swCustom4);
        swCustom5 = findViewById(R.id.swCustom5);
        swCustom6 = findViewById(R.id.swCustom6);
        swCustom7 = findViewById(R.id.swCustom7);
        swCustom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                     sw=1;
            }
        });
        swCustom2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    sw2=1;
            }
        });
        swCustom3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    sw3=1;
            }
        });
        swCustom4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    sw4=1;
            }
        });
        swCustom5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    sw5=1;
            }
        });
        swCustom6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    sw6=1;
            }
        });
        swCustom7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    sw7=1;
            }
        });


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
        icon_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimeWork();
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_open.setText ( sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_close.setText (  sHour + ":" + sMinute +":00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        ///////////
        card_open2.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_open2.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        card_close2.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_close2.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        /////////
        card_open3.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_open3.setText (  sHour + ":" + sMinute +":00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        card_close3.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_close3.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        //////////
        card_open4.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_open4.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        card_close4.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_close4.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        ////////////
        card_open5.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        //am pm mode

                        tv_time_open5.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        card_close5.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_close5.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });////////
        card_open6.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_open6.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        card_close6.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_close6.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        ///////
        card_open7.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_open7.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        card_close7.setOnClickListener(new View.OnClickListener() {
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
                        String sHour=String.valueOf(selectedHour);
                        if(selectedHour<10)
                            sHour="0"+sHour;
                        String sMinute=String.valueOf(selectedMinute);
                        if(selectedMinute<10)
                            sMinute="0"+sMinute;
                        tv_time_close7.setText (  sHour + ":" + sMinute+":00" );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        getTimeWork();
    }
    private void setTimeWork() {
        progressBar.setVisibility(View.VISIBLE);

        Call<StoreTimes> call1 = apiInterface.EditTimesWork(storeId,tv_time_open.getText().toString(),tv_time_close.getText().toString(),sw
                ,tv_time_open2.getText().toString(),tv_time_close2.getText().toString(),sw2
                ,tv_time_open3.getText().toString(),tv_time_close3.getText().toString(),sw3
                ,tv_time_open4.getText().toString(),tv_time_close4.getText().toString(),sw4
                ,tv_time_open5.getText().toString(),tv_time_close5.getText().toString(),sw5
                ,tv_time_open6.getText().toString(),tv_time_close6.getText().toString(),sw6
                ,tv_time_open7.getText().toString(),tv_time_close7.getText().toString(),sw7
        );
        call1.enqueue(new Callback<StoreTimes>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<StoreTimes> call, Response<StoreTimes> response) {
                StoreTimes StoreTimess = response.body();
                if (StoreTimess.status) {
                    Toast.makeText(MerchantWorkingHours.this, StoreTimess.message, Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(MerchantWorkingHours.this, StoreTimess.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<StoreTimes> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }
    private void getTimeWork() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Profile> callNew = apiInterface.UserProfile();
        callNew.enqueue(new Callback<Profile>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Profile> callNew, Response<Profile> response) {
                Profile resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    storeId=resource.data.store.id;
                    List<StoreTimeWork> timeWork = resource.data.store.storeTimeWorks;
                    for (StoreTimeWork datum : timeWork) {
                        if(datum.day.equals("Sunday")){
                            swCustom.setChecked(true);
                           tv_time_open.setText(datum.from);
                           tv_time_close.setText(datum.to);
                        }else if(datum.day.equals("Monday")){
                            swCustom2.setChecked(true);
                            tv_time_open2.setText(datum.from);
                            tv_time_close2.setText(datum.to);
                        }else if(datum.day.equals("Tuesday")){
                            swCustom3.setChecked(true);
                            tv_time_open3.setText(datum.from);
                            tv_time_close3.setText(datum.to);
                        }else if(datum.day.equals("Wednesday")){
                            swCustom4.setChecked(true);
                            tv_time_open4.setText(datum.from);
                            tv_time_close4.setText(datum.to);
                        }else if(datum.day.equals("Thursday")){
                            swCustom5.setChecked(true);
                            tv_time_open5.setText(datum.from);
                            tv_time_close5.setText(datum.to);
                        }else if(datum.day.equals("Friday")){
                            swCustom6.setChecked(true);
                            tv_time_open6.setText(datum.from);
                            tv_time_close6.setText(datum.to);
                        }else if(datum.day.equals("Saturday")){
                            swCustom7.setChecked(true);
                            tv_time_open7.setText(datum.from);
                            tv_time_close7.setText(datum.to);
                        }

                    }
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}