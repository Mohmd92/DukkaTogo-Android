package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        progressBar =findViewById(R.id.progressBar);
        recyclerView =findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar2);
        ImageView icon_filter =toolbar.findViewById(R.id.icon_filter);
        ImageView iconMenu =toolbar.findViewById(R.id.icon_menu);
        ImageView iconBack =toolbar.findViewById(R.id.icon_back);
        ImageView icon_notification =toolbar.findViewById(R.id.icon_notification);
        icon_notification.setVisibility(View.GONE);
        icon_filter.setVisibility(View.GONE);
        iconMenu.setVisibility(View.GONE);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
       getFavorites();
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
}