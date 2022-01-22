package com.dukan.dukkan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.util.SharedPreferenceManager;

public class DriverReadCardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_read_card_acticity);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        icon_menu.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);


        ImageView img_image = findViewById(R.id.img_image);
        ImageView img_barcode = findViewById(R.id.img_barcode);
        ImageView img_camera = findViewById(R.id.img_camera);

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        img_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}