package com.dukan.dukkan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.fragment.DriveOrderFilterSheetFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.util.HorizontalListView;

public class DriverStatisticsActivity extends AppCompatActivity {
    TextView tv_num_orders,tv_total_cost;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_statistics_acticity);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        tv_num_orders = findViewById(R.id.tv_num_orders);
        tv_total_cost = findViewById(R.id.tv_total_cost);

        recyclerView = findViewById(R.id.recyclerView);
        ImageView img_filter = findViewById(R.id.img_filter);
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_menu = toolbar.findViewById(R.id.icon_menu);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);
        ImageView icon_filter = toolbar.findViewById(R.id.icon_filter);
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
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriveOrderFilterSheetFragment driveOrderFilterSheetFragment = new DriveOrderFilterSheetFragment();
                driveOrderFilterSheetFragment.show(getSupportFragmentManager()
                        , driveOrderFilterSheetFragment.getTag());
            }
        });
    }
}