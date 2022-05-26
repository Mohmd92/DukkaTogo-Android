package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.dukan.dukkan.fragment.CategorySheetFragment;
import com.dukan.dukkan.fragment.DriveFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.fragment.MerchantFragment;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainMerchantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TextView title;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<Tabs> tabsArrayList;
    private ImageView header_im_close;
    private TextView tv_location,header_tv_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_merchant);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferenceManager.getInstance(getBaseContext()).setUserCurrentType("Merchant");
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
        RelativeLayout rel_profile = view.findViewById(R.id.rel_profile);
        TextView header_tv_user_name = view.findViewById(R.id.header_tv_user_name);
        if(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name()!=null) {
            if (!SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name().equals(""))
                getProfile();
        }
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMerchantActivity.this, NotificationsActivity.class));

            }
        });
        rel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMerchantActivity.this, MerchantProfileActivity.class));
                closeDrawer();
            }
        });
        header_im_close.setClipToOutline(true);
        viewPager = findViewById(R.id.home_pager_view);
        tabLayout = findViewById(R.id.home_tab_layout);
        tabLayout.setVisibility(View.GONE);
        tabsArrayList = new ArrayList<>();
        tabsArrayList.add(new Tabs(0, "Merchant", new MerchantFragment()));
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
        Menu menu =navigationView.getMenu();
        MenuItem nav_switch_account = menu.findItem(R.id.nav_switch_accounts);
        String[] userProfileInfo =  SharedPreferenceManager.getInstance(getBaseContext()).getUserType().split("&");
        if(userProfileInfo.length==1)
            nav_switch_account.setVisible(false);
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
                case R.id.nav_work_hours:
                    startActivity(new Intent(MainMerchantActivity.this, MerchantWorkingHours.class));
                closeDrawer();
                break;
                case R.id.nav_discount_code:
                    startActivity(new Intent(MainMerchantActivity.this, MerchantDiscountsCode.class));
                closeDrawer();
                break;
                case R.id.nav_drivers:
                    startActivity(new Intent(MainMerchantActivity.this, MerchantDriversActivity.class));
                closeDrawer();
                break;
                case R.id.nav_orders_delivered:
                    startActivity(new Intent(MainMerchantActivity.this, MerchantOrdersDeliveredActivity.class));
                closeDrawer();
                break;
                case R.id.nav_settings:
                startActivity(new Intent(MainMerchantActivity.this, SettingActivity.class));
                closeDrawer();
                break;
            case R.id.nav_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.full_name));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
                closeDrawer();
                break;
                case R.id.nav_switch_accounts:
                    CategorySheetFragment CategorySheetFragment = new CategorySheetFragment();
                    CategorySheetFragment.show(getSupportFragmentManager()
                            , CategorySheetFragment.getTag());
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
}