package com.dukan.dukkan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerCartsAdapter;
import com.dukan.dukkan.adapter.TabAdapter;
import com.dukan.dukkan.fragment.CategorySheetFragment;
import com.dukan.dukkan.fragment.FilterSheetFragment;
import com.dukan.dukkan.fragment.HomeFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.fragment.TermsSheetFragment;
import com.dukan.dukkan.pojo.CartMain2;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.UserProfile;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.dukan.dukkan.adapter.Tabs;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TextView title,tv_location,header_tv_user_name,tv_sala;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<Tabs> tabsArrayList;
    private ImageView header_im_close;
    APIInterface apiInterface;
    int tempCount=0;
    boolean dialogShow=false;
    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1*1000 - SystemClock.elapsedRealtime()%1000);
            if (!dialogShow && !isNetworkAvailable()) {
                Dialog();
                dialogShow = true;
            }
            if(SharedPreferenceManager.getInstance(getApplicationContext()).getCartCount()>0) {
                if (tempCount != SharedPreferenceManager.getInstance(getApplicationContext()).getCartCount()){
                    tempCount = SharedPreferenceManager.getInstance(getApplicationContext()).getCartCount();
                    tv_sala.setText("" + SharedPreferenceManager.getInstance(getApplicationContext()).getCartCount());
                tv_sala.setVisibility(View.VISIBLE);
            }
            }else {
                tv_sala.setVisibility(View.GONE);
                tempCount=0;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferenceManager.getInstance(getBaseContext()).setUserCurrentType("Customer");
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        drawerLayout = findViewById(R.id.home_drawer_layout);
        navigationView = findViewById(R.id.home_nav_view);
        toolbar = findViewById(R.id.home_toolbar);
        title = findViewById(R.id.home_title);
        tv_sala = findViewById(R.id.tv_sala);
        ImageView icon_search = toolbar.findViewById(R.id.icon_search);

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
        RelativeLayout rel_sign_in = view.findViewById(R.id.rel_sign_in);
        Button signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        TextView header_tv_user_name = view.findViewById(R.id.header_tv_user_name);
        if(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name()!=null) {
            if (!SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name().equals(""))
                header_tv_user_name.setText(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name());
        }

        header_im_close.setClipToOutline(true);
        ImageView icon_buy = findViewById(R.id.icon_buy);
        FrameLayout frame_buy = findViewById(R.id.frame_buy);
        icon_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
        frame_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                finish();
            }
        });
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotificationsActivity.class));

            }
        });
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
        rel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                closeDrawer();
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
        if(SharedPreferenceManager.getInstance(getBaseContext()).getLoginType().equals(""))
             getProfile();
        else if(SharedPreferenceManager.getInstance(getBaseContext()).getLoginType().equals("google") || SharedPreferenceManager.getInstance(getBaseContext()).getLoginType().equals("facebook"))
             getProfileGoogle();
        Menu menu =navigationView.getMenu();
        MenuItem nav_switch_account = menu.findItem(R.id.nav_switch_accounts);
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);
        MenuItem nav_Stores = menu.findItem(R.id.nav_Stores);
        nav_Stores.setVisible(false);
        if(SharedPreferenceManager.getInstance(getBaseContext()).getUserType()!=null) {
            String[] userProfileInfo = SharedPreferenceManager.getInstance(getBaseContext()).getUserType().split("&");
            if (userProfileInfo.length == 1)
                nav_switch_account.setVisible(false);
        }else
            nav_switch_account.setVisible(false);

        if(!SharedPreferenceManager.getInstance(getBaseContext()).get_api_token().equals("")) {
            rel_profile.setVisibility(View.VISIBLE);
            rel_sign_in.setVisibility(View.GONE);
            nav_logout.setVisible(true);
        }else {
            nav_logout.setVisible(false);
            rel_profile.setVisibility(View.GONE);
            rel_sign_in.setVisibility(View.VISIBLE);
        }



        getCartsCount();
//        printHashKey();
    }
    public  void printHashKey()
    {

        // Add code to print out the key hash
        try {

            PackageInfo info
                    = getPackageManager().getPackageInfo(
                    "com.dukan.dukkan",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {

                MessageDigest md
                        = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(
                                md.digest(),
                                Base64.DEFAULT));
            }
        }

        catch (PackageManager.NameNotFoundException e) {
        }

        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private  void getProfileGoogle(){
        header_tv_user_name.setText(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name());
        tv_location.setText("");
        Picasso.get()
                .load(SharedPreferenceManager.getInstance(getBaseContext()).getUserImage())
                .into(header_im_close);
    }
    private void getProfile() {
        if(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name()!=null){
            if(!SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name().equals("")){
                header_tv_user_name.setText(SharedPreferenceManager.getInstance(getBaseContext()).getUser_Name());
                tv_location.setText(SharedPreferenceManager.getInstance(getBaseContext()).getAddress());
                if(!SharedPreferenceManager.getInstance(getBaseContext()).getUserImage().equals("")){
                    Picasso.get().load(SharedPreferenceManager.getInstance(getBaseContext()).getUserImage())
                            .into(header_im_close);
                }

            }
        }

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
                startActivity(new Intent(MainActivity.this, StoreMapActivity.class));
                closeDrawer();
                break;
            case R.id.nav_favorite:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                closeDrawer();
                break;
                case R.id.nav_points:
                    if(!SharedPreferenceManager.getInstance(getBaseContext()).get_api_token().equals(""))
                          startActivity(new Intent(MainActivity.this, MyPointsActivity.class));
                    else
                        Toast.makeText(this,getString(R.string.sign_in_first_point), Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
                case R.id.nav_privacy_policy:
                    startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                    closeDrawer();
                break;
            case R.id.nav_chat:
                startActivity(new Intent(MainActivity.this, ChatListActivity.class));
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
                String shareBody = "Download Dukkan Togo Store From Here: "+"http://play.google.com/store/apps/details?id=" + getPackageName();
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

    @Override
    protected void onResume() {
        getCartsCount();
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
    private void getCartsCount() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println("KKKKKKKKKKKKK12223 "+ID);
        Call<CartMain2> callNew = apiInterface.doGetListCart(ID,"android");
        callNew.enqueue(new Callback<CartMain2>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CartMain2> callNew, Response<CartMain2> response) {
                CartMain2 cart = response.body();
                if (cart.status) {
                    SharedPreferenceManager.getInstance(getApplicationContext()).setCartCount(cart.data.carts.size());
                }

            }
            @Override
            public void onFailure(Call<CartMain2> call, Throwable t) {
            }

        });
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
        final Dialog EndDialog=new Dialog( MainActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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
                    Toast.makeText( MainActivity.this, getString(R.string.no_internet_connect), Toast.LENGTH_SHORT).show();
                }
            }
        });
        EndDialog.show();
    }
}