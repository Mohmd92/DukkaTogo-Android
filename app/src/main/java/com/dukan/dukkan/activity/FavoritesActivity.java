package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerCartsAdapter;
import com.dukan.dukkan.adapter.RecyclerFavoriteAdapter;
import com.dukan.dukkan.adapter.RecyclerProductAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartMain2;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoritesActivity extends AppCompatActivity implements  RecyclerCartsAdapter.ItemClickListener {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    ProgressBar progressBar;
    private Toolbar toolbar;
    TextView tv_sala;
    int tempCount=0;
    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1*1000 - SystemClock.elapsedRealtime()%1000);
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
        setContentView(R.layout.activity_favorite);
        progressBar =findViewById(R.id.progressBar);
        recyclerView =findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar2);
        tv_sala = findViewById(R.id.tv_sala);
        ImageView icon_filter =toolbar.findViewById(R.id.icon_filter);
        ImageView iconMenu =toolbar.findViewById(R.id.icon_menu);
        ImageView iconBack =toolbar.findViewById(R.id.icon_back);
        ImageView icon_notification =toolbar.findViewById(R.id.icon_notification);
        ImageView icon_buy =toolbar.findViewById(R.id.icon_buy);
        icon_notification.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        iconMenu.setVisibility(View.GONE);
        FrameLayout frame_buy = toolbar.findViewById(R.id.frame_buy);
        icon_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoritesActivity.this, CartActivity.class));
            }
        });
        frame_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoritesActivity.this, CartActivity.class));
                finish();
            }
        });
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
       getFavorites();
        getCartsCount();
    }
    private void getFavorites() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println("KKKKKKKKKKKKK12223 "+ID);
        progressBar.setVisibility(View.VISIBLE);
        Call<FavoriteMain> callNew = apiInterface.doGetListFavorite(ID);
        callNew.enqueue(new Callback<FavoriteMain>() {
            @Override
            public void onResponse(Call<FavoriteMain> callNew, Response<FavoriteMain> response) {
                FavoriteMain favorite = response.body();
                if (favorite.status) {
                    List<FavoriteMain.Datum> datumList = favorite.data;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerFavoriteAdapter adapter = new RecyclerFavoriteAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    recyclerView.setLayoutManager(mLayoutManager);


                }


                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<FavoriteMain> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }

        });
    }


    @Override
    public void onClick(View view, int position) {

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

}