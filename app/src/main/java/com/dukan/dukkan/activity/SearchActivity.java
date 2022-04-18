package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.CategoryAdapter;
import com.dukan.dukkan.adapter.RecyclerProductAdapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.CategoryProduct;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.util.HorizontalListView;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private EditText edit_search;
    HorizontalListView HorizontalListView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    APIInterface apiInterface;
    View previousSelectedItem;
    RelativeLayout relative;
    TextView category_name_all;
    CategoryAdapter customAdapter;
    int categoryId=0;
    List<CategoryProduct> categ = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        ImageView icon_back = toolbar.findViewById(R.id.icon_back);
        ImageView icon_reset = toolbar.findViewById(R.id.icon_reset);
        icon_reset.setVisibility(View.GONE);
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        setSupportActionBar(toolbar);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit_search = findViewById(R.id.edit_search);
        HorizontalListView = findViewById(R.id.HorizontalListView);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        relative = findViewById(R.id.relative);
        category_name_all = findViewById(R.id.category_name);
        CardView card_search = findViewById(R.id.card_search);
        card_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getProducts(edit_search.getText().toString(), categoryId);
                    icon_reset.setVisibility(View.VISIBLE);
            }
        });
        icon_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_search.setText("");
                    icon_reset.setVisibility(View.GONE);
            }
        });
        edit_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!TextUtils.isEmpty(edit_search.getText().toString())) {
                    icon_reset.setVisibility(View.VISIBLE);
                }
            }
        });
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAdapter.notifyDataSetChanged();
                relative.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button55));
                category_name_all.setTextColor(Color.WHITE);
                categoryId=0;
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        getCategories();
//        getProducts("",0);
    }
    private void getProducts(String txt,int categ_id) {
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Call<MultipleProducts> callNew = apiInterface.doGetListProduct(ID,"android",0,0,categ_id,txt,0,0,0,0);
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
    private void getCategories() {
        Call<Category> callNew = apiInterface.doGetListCategory();
        callNew.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> callNew, Response<Category> response) {
                Log.d("TAG111111",response.code()+"");
                Category resource = response.body();
                Boolean status = resource.status;
                if(status) {
                    Integer[] cateIds=new Integer[resource.data.size()];

                    categ = resource.data;
                    int i=0;
                    for (CategoryProduct datum : categ) {
                        cateIds[i]= datum.id;
                        i++;
                    }
                     customAdapter = new CategoryAdapter(getApplicationContext(),categ);
                    HorizontalListView.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();
                    relative.setVisibility(View.VISIBLE);
                    HorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            relative.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button515));
                            category_name_all.setTextColor(Color.BLACK);
                            if (previousSelectedItem!=null) {
                                previousSelectedItem.findViewById(R.id.relative).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button515));
                                ((TextView) previousSelectedItem.findViewById(R.id.category_name)).setTextColor(Color.BLACK);
                            }
                            previousSelectedItem=view;
                            view.findViewById(R.id.relative).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button55));
                            ((TextView) view.findViewById(R.id.category_name)).setTextColor(Color.WHITE);
                            categoryId=cateIds[position];
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
            }
        });
    }
}