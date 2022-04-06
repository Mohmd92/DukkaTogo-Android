package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.CategoryAdapter;
import com.dukan.dukkan.adapter.RecyclerCategoryAdapter;
import com.dukan.dukkan.adapter.TabAdapter;
import com.dukan.dukkan.adapter.Tabs;
import com.dukan.dukkan.fragment.HomeFragment;
import com.dukan.dukkan.fragment.ProductListFragment;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.CategoryProduct;
import com.dukan.dukkan.util.HorizontalListView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TextView title;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<Tabs> tabsArrayList;
    private ImageView header_im_close;
    private int StoreId;
    private float rateStore;
    private String StoreName,imageStore;
    RecyclerView recyclerView;
    APIInterface apiInterface;
    List<CategoryProduct> categ = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.product_list_main);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bundle extras = getIntent().getExtras();

        rateStore= extras.getInt("rateStore");
        StoreId= extras.getInt("StoreId");
        StoreName= extras.getString("StoreName");
        imageStore= extras.getString("imageStore");
        drawerLayout = findViewById(R.id.home_drawer_layout);
        navigationView = findViewById(R.id.home_nav_view);
        toolbar = findViewById(R.id.home_toolbar2);
        title = findViewById(R.id.home_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_group_11935);
        ViewCompat.setLayoutDirection(toolbar, ViewCompat.LAYOUT_DIRECTION_RTL);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
        ImageView icon_back = toolbar.findViewById(R.id.img_back);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View view = navigationView.getHeaderView(0);
        TextView header_tv_user_name = view.findViewById(R.id.header_tv_user_name);
        TextView tv_rating_num = view.findViewById(R.id.tv_rating_num);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar2);
        recyclerView = view.findViewById(R.id.recyclerView);
        ratingBar.setRating(rateStore);
        header_tv_user_name.setText(StoreName);
        tv_rating_num.setText(""+rateStore);
        header_im_close = view.findViewById(R.id.header_im_close);
        header_im_close.setClipToOutline(true);
        Picasso.get()
                .load(imageStore)
                .into(header_im_close);

        viewPager = findViewById(R.id.home_pager_view);
        tabsArrayList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("StoreId", StoreId);
        bundle.putString("StoreName", StoreName);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(bundle);
        tabsArrayList.add(new Tabs(0, "ProductList", fragment));
//        MaterialShapeDrawable navViewBackground = (MaterialShapeDrawable) navigationView.getBackground();
//        navViewBackground.setShapeAppearanceModel(
//                navViewBackground.getShapeAppearanceModel()
//                        .toBuilder()
//                        .setTopRightCorner(CornerFamily.ROUNDED,160)
//                        .build());
        FragmentManager fragmentManager = getSupportFragmentManager();
        TabAdapter adapter = new TabAdapter(fragmentManager, tabsArrayList);
        viewPager.setAdapter(adapter);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        getCategories();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void getCategories() {
        Log.d("TAG111111","333333333");

        Call<Category> callNew = apiInterface.doGetListCategoryStore(StoreId);
        callNew.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> callNew, Response<Category> response) {
                Log.d("TAG111111",response.code()+"");
                Category resource = response.body();
                Boolean status = resource.status;
                if(status) {

                    categ = resource.data;
                    Log.d("TAG111111","StoreId "+StoreId);
                    Log.d("TAG111111","size "+categ.size());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerCategoryAdapter customAdapter = new RecyclerCategoryAdapter(getApplicationContext(),categ);
                    recyclerView.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
            }
        });
    }
}