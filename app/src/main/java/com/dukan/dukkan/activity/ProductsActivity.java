package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
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
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerCartsAdapter;
import com.dukan.dukkan.adapter.RecyclerProductAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.fragment.DriveOrderFilterSheetFragment;
import com.dukan.dukkan.fragment.FilterSheetFragment;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.util.AutoFitGridLayoutManager;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsActivity extends AppCompatActivity implements  RecyclerCartsAdapter.ItemClickListener {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    ProgressBar progressBar;
    private Toolbar toolbar;
    int mostProduct,newProduct=0,store=0;
    String title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        progressBar =findViewById(R.id.progressBar);
        TextView tv_title =findViewById(R.id.tv_title);
        recyclerView =findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar2);
        Bundle extras = getIntent().getExtras();
        newProduct= extras.getInt("new");
        mostProduct= extras.getInt("most");
        store= extras.getInt("store");
        title= extras.getString("title");
        tv_title.setText(title);
        ImageView icon_filter =toolbar.findViewById(R.id.icon_filter);
        ImageView iconMenu =toolbar.findViewById(R.id.icon_menu);
        ImageView iconBack =toolbar.findViewById(R.id.icon_back);
        iconMenu.setVisibility(View.GONE);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        icon_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FilterSheetFragment filterSheetFragment = new FilterSheetFragment();
                bundle.putString("title", title);
                filterSheetFragment.setArguments(bundle);
                filterSheetFragment.show(getSupportFragmentManager()
                        , filterSheetFragment.getTag());
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
       getProducts();


    }
    private void getProducts() {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Call<MultipleProducts> callNew = apiInterface.doGetListProduct(ID,"android",store,0,0,"",newProduct,mostProduct);
        callNew.enqueue(new Callback<MultipleProducts>() {
            @Override
            public void onResponse(Call<MultipleProducts> callNew, Response<MultipleProducts> response) {
                Log.d("TAG111111",response.code()+"");
                MultipleProducts resource = response.body();
                if(resource.status) {
                    List<MultipleProducts.Data.Product> newProduct = resource.data.products;
                RecyclerProductAdapter adapter = new RecyclerProductAdapter(getApplicationContext(), newProduct);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.setLayoutManager(mLayoutManager);

            }}
            @Override
            public void onFailure(Call<MultipleProducts> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }

    @Override
    public void onClick(View view, int position) {

    }
}