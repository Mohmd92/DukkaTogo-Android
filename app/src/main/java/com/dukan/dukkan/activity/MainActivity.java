package com.dukan.dukkan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.TabAdapter;
import com.dukan.dukkan.fragment.CategorySheetFragment;
import com.dukan.dukkan.fragment.FilterSheetFragment;
import com.dukan.dukkan.fragment.HomeFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.fragment.TermsSheetFragment;
import com.dukan.dukkan.pojo.UserProfile;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.dukan.dukkan.adapter.Tabs;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TextView title;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<Tabs> tabsArrayList;
    private ImageView header_im_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        drawerLayout = findViewById(R.id.home_drawer_layout);
        navigationView = findViewById(R.id.home_nav_view);
        toolbar = findViewById(R.id.home_toolbar);
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
        RelativeLayout rel_profile = view.findViewById(R.id.rel_profile);
        TextView header_tv_user_name = view.findViewById(R.id.header_tv_user_name);
        if(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name()!=null) {
            if (!SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name().equals(""))
                header_tv_user_name.setText(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name());
        }

        header_im_close.setClipToOutline(true);
        ImageView icon_buy = findViewById(R.id.icon_buy);
        icon_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
        rel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            }
        });
        viewPager = findViewById(R.id.home_pager_view);
        tabLayout = findViewById(R.id.home_tab_layout);
        tabLayout.setVisibility(View.GONE);
        tabsArrayList = new ArrayList<>();
        tabsArrayList.add(new Tabs(0, "Home", new HomeFragment()));
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
    }
    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START, true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                tabLayout.getTabAt(0).select();
                closeDrawer();
                break;
            case R.id.nav_Stores:
                startActivity(new Intent(MainActivity.this, StoresActivity.class));
                closeDrawer();
                break;
            case R.id.nav_store_map:
                closeDrawer();
                break;
            case R.id.nav_favorite:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                closeDrawer();
                break;
            case R.id.nav_points:
                closeDrawer();
                break;
            case R.id.nav_chat:
                closeDrawer();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                closeDrawer();
                break;
            case R.id.nav_switch_accounts:
                CategorySheetFragment CategorySheetFragment = new CategorySheetFragment();
                CategorySheetFragment.show(getSupportFragmentManager()
                        , CategorySheetFragment.getTag());
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