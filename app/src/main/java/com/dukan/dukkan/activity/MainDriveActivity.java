package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.TabAdapter;
import com.dukan.dukkan.adapter.Tabs;
import com.dukan.dukkan.fragment.DriveFragment;
import com.dukan.dukkan.fragment.HomeFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainDriveActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TextView title,tv_location,header_tv_user_name;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<Tabs> tabsArrayList;
    private ImageView header_im_close;
    boolean dialogShow=false;
    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 10 * 1000 - SystemClock.elapsedRealtime() % 1000);
            if (!dialogShow && !isNetworkAvailable()) {
                Dialog();
                dialogShow = true;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drive);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferenceManager.getInstance(getBaseContext()).setUserCurrentType("Delivery");
        drawerLayout = findViewById(R.id.home_drawer_layout);
        navigationView = findViewById(R.id.home_nav_view);
        toolbar = findViewById(R.id.home_toolbar);
        ImageView icon_buy =toolbar.findViewById(R.id.icon_buy);
        ImageView icon_search =toolbar.findViewById(R.id.icon_search);
        icon_buy.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        title = findViewById(R.id.home_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View view = navigationView.getHeaderView(0);
        header_im_close = view.findViewById(R.id.header_im_close);
        header_tv_user_name = view.findViewById(R.id.header_tv_user_name);
        tv_location = view.findViewById(R.id.tv_location);
        header_im_close.setClipToOutline(true);
        viewPager = findViewById(R.id.home_pager_view);
        tabLayout = findViewById(R.id.home_tab_layout);
        tabLayout.setVisibility(View.GONE);
        tabsArrayList = new ArrayList<>();
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainDriveActivity.this, NotificationsActivity.class));

            }
        });
        tabsArrayList.add(new Tabs(0, "Drive", new DriveFragment()));
        MaterialShapeDrawable navViewBackground = (MaterialShapeDrawable) navigationView.getBackground();
        navViewBackground.setShapeAppearanceModel(
                navViewBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED,160)
                        .build());
        FragmentManager fragmentManager = getSupportFragmentManager();
        TabAdapter adapter = new TabAdapter(fragmentManager, tabsArrayList);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //    title.setText(tab.getText());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        RelativeLayout rel_profile = view.findViewById(R.id.rel_profile);
        rel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainDriveActivity.this, DriverProfileActivity.class));
                closeDrawer();
            }
        });
        Menu menu =navigationView.getMenu();
        MenuItem nav_switch_account = menu.findItem(R.id.nav_switch_accounts);
        String[] userProfileInfo =  SharedPreferenceManager.getInstance(getBaseContext()).getUserType().split("&");
        if(userProfileInfo.length==1)
            nav_switch_account.setVisible(false);
        getProfile();
    }
    private void getProfile() {
        header_tv_user_name.setText(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name());
        tv_location.setText(SharedPreferenceManager.getInstance(getBaseContext()).getAddress());
        Picasso.get()
                .load(SharedPreferenceManager.getInstance(getBaseContext()).getUserImage())
                .into(header_im_close);
    }
    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START, true);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                tabLayout.getTabAt(0).select();
                closeDrawer();
                break;
            case R.id.nav_order:
                startActivity(new Intent(MainDriveActivity.this, DriverOrdersActivity.class));
                closeDrawer();
                break;
            case R.id.nav_store_need:
                startActivity(new Intent(MainDriveActivity.this, StoreNeedActivity.class));
                closeDrawer();
                break;
                case R.id.nav_delivery_stores:
                startActivity(new Intent(MainDriveActivity.this, DeliveryStoresActivity.class));
                closeDrawer();
                break;
                case R.id.nav_store_invite:
                startActivity(new Intent(MainDriveActivity.this, DeliveryStoresInviteActivity.class));
                closeDrawer();
                break;
                case R.id.nav_statistics:
                startActivity(new Intent(MainDriveActivity.this, DriverStatisticsActivity.class));
                closeDrawer();
                break;
            case R.id.nav_privacy_policy:
                startActivity(new Intent(MainDriveActivity.this, PrivacyPolicyActivity.class));
                closeDrawer();
                break;
            case R.id.nav_read_code:
                startActivity(new Intent(MainDriveActivity.this, QrCodeScaner.class));
                closeDrawer();
                break;
                case R.id.nav_call_us:
                startActivity(new Intent(MainDriveActivity.this, DriverCallUSActivity.class));
                closeDrawer();
                break;
//                case R.id.nav_history_orders:
//                startActivity(new Intent(MainDriveActivity.this, DriverHistoryOrdersActivity.class));
//                closeDrawer();
//                break;
                case R.id.nav_learn_with_us:
                startActivity(new Intent(MainDriveActivity.this, DriverLearnActivity.class));
                closeDrawer();
                break;
                case R.id.nav_settings:
                startActivity(new Intent(MainDriveActivity.this, SettingActivity.class));
                closeDrawer();
                break;
            case R.id.nav_logout:
                LogoutSheetFragment logoutSheetFragment = new LogoutSheetFragment();
                logoutSheetFragment.show(getSupportFragmentManager()
                        , logoutSheetFragment.getTag());
                closeDrawer();
                break;
        }
        return false;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("https://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                return urlc.getResponseCode() == 200;
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;

    }
    private void Dialog(){
        final Dialog EndDialog=new Dialog( MainDriveActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        EndDialog.setContentView(R.layout.no_internet);
        EndDialog.setCancelable(false);
        Button button =  EndDialog.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected(getApplicationContext())){
                    dialogShow=false;
                    EndDialog.dismiss();
                }else{
                    Toast.makeText( MainDriveActivity.this, getString(R.string.no_internet_connect), Toast.LENGTH_SHORT).show();
                }
            }
        });
        EndDialog.show();
    }
    @Override
    protected void onResume() {
        handler.post(periodicUpdate);

        super.onResume();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(periodicUpdate);

        super.onStop();
    }

    @Override
    protected void onStart() {
        handler.post(periodicUpdate);

        super.onStart();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(periodicUpdate);

        super.onDestroy();
    }
}