package com.dukan.dukkan.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MerchantCreateDiscount extends AppCompatActivity {
    APIInterface apiInterface;
    CardView card_open,card_close;
    EditText edit_street;
    TextView tv_starting_date,tv_expiry_date;
    ImageView image_status;
    Spinner spinner_discount,spinner_section;
    private Calendar myCalendar;
    private Calendar myCalendar2;
    private Long date_str;
    private Long date_str2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_create_discount);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        card_open = findViewById(R.id.card_open);
        card_close = findViewById(R.id.card_close);
        tv_starting_date = findViewById(R.id.tv_starting_date);
        tv_expiry_date = findViewById(R.id.tv_expiry_date);
        edit_street = findViewById(R.id.edit_street);
        image_status = findViewById(R.id.image_status);
        spinner_section = findViewById(R.id.spinner_section);
        spinner_discount = findViewById(R.id.spinner_discount);
        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_edit = toolbar.findViewById(R.id.icon_edit);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        setSupportActionBar(toolbar);
        icon_edit.setVisibility(View.GONE);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MerchantCreateDiscount.this, NotificationsActivity.class));
            }
        });

        icon_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        card_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MerchantCreateDiscount.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        card_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MerchantCreateDiscount.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tv_starting_date.setText(sdf.format(myCalendar.getTime()));
        date_str=myCalendar.getTimeInMillis();
    }
    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tv_expiry_date.setText(sdf.format(myCalendar2.getTime()));
        date_str2=myCalendar2.getTimeInMillis();
    }
}