package com.dukan.dukkan.activity;

import android.os.Bundle;
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
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerProductAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.util.AutoFitGridLayoutManager;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        progressBar =findViewById(R.id.progressBar);
        recyclerView =findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar2);
        ImageView iconMenu =toolbar.findViewById(R.id.icon_menu);
        ImageView iconBack =toolbar.findViewById(R.id.icon_back);
        iconMenu.setVisibility(View.GONE);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apiInterface = APIClient.getClient().create(APIInterface.class);
       getProducts();


    }
    private void getProducts() {
        int countryId= Integer.parseInt(SharedPreferenceManager.getInstance(getBaseContext()).getCountry());
        int cityId= Integer.parseInt(SharedPreferenceManager.getInstance(getBaseContext()).getCity());
        progressBar.setVisibility(View.VISIBLE);
        Call<MultipleProducts> callNew = apiInterface.doGetListProduct();
        callNew.enqueue(new Callback<MultipleProducts>() {
            @Override
            public void onResponse(Call<MultipleProducts> callNew, Response<MultipleProducts> response) {
                Log.d("TAG111111",response.code()+"");
                MultipleProducts resource = response.body();
                String status = resource.status;
                List<MultipleProducts.Datum> newProduct = resource.data;
                AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(getApplicationContext(), 400);

//                recyclerView.setLayoutManager(layoutManager);
                RecyclerProductAdapter adapter = new RecyclerProductAdapter(getApplicationContext(), newProduct);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.setLayoutManager(mLayoutManager);

            }
            @Override
            public void onFailure(Call<MultipleProducts> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }

}