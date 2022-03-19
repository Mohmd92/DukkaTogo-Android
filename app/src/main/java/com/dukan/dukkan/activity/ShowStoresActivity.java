package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.fragment.FilterSheetFragment;
import com.dukan.dukkan.fragment.LogoutSheetFragment;
import com.dukan.dukkan.fragment.ReviewSheetFragment;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.ShowStore;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.nio.ShortBuffer;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowStoresActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    private Toolbar toolbar;
    ImageView image;
    TextView tv_num_products,tv_rating_num,tv_customer_num,tv_number,tv_address,tv_name;
    RelativeLayout rel_product_list;
    int stoeId;
    String url_telegram,url_twitter,url_whatsapp,url_instagram,url_facebook;
    int storeId;
    float rateStore;
    String imageStore;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_store);
        Bundle extras = getIntent().getExtras();
        stoeId= extras.getInt("productID");
        recyclerView =findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar2);
        image = findViewById(R.id.image);
        tv_num_products = findViewById(R.id.tv_num_products);
        tv_rating_num = findViewById(R.id.tv_rating_num);
        tv_customer_num = findViewById(R.id.tv_customer_num);
        tv_name = findViewById(R.id.tv_name);
        tv_number = findViewById(R.id.tv_number);
        tv_address = findViewById(R.id.tv_address);
        ImageView icon_filter =toolbar.findViewById(R.id.icon_filter);
        ImageView icon_search =toolbar.findViewById(R.id.icon_search);
        ImageView iconMenu =toolbar.findViewById(R.id.icon_menu);
        ImageView iconBack =toolbar.findViewById(R.id.icon_back);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        icon_filter.setVisibility(View.GONE);
        icon_search.setVisibility(View.GONE);
        iconMenu.setVisibility(View.GONE);
        CardView card_rating=findViewById(R.id.card_rating);
        CardView card_chat=findViewById(R.id.card_chat);
        rel_product_list=findViewById(R.id.rel_product_list);
        RelativeLayout rel_facebook=findViewById(R.id.rel_facebook);
        RelativeLayout rel_instagram=findViewById(R.id.rel_instagram);
        RelativeLayout rel_whatsapp=findViewById(R.id.rel_whatsapp);
        RelativeLayout rel_twitter=findViewById(R.id.rel_twitter);
        RelativeLayout rel_telegram=findViewById(R.id.rel_telegram);
        RelativeLayout rel_call=findViewById(R.id.rel_call);
        RelativeLayout rel_map=findViewById(R.id.rel_map);
        rel_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(tv_number.getText().toString())){
                    String phone = tv_number.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            }
        });
        rel_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!latitude.equals("null") && !longitude.equals("null")){
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });
        rel_product_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowStoresActivity.this, ProductList.class);
                i.putExtra("rateStore", rateStore);
                i.putExtra("imageStore", imageStore);
                i.putExtra("StoreId", storeId);
                i.putExtra("StoreName", tv_name.getText().toString());
                startActivity(i);
            }
        });
        card_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                ReviewSheetFragment reviewSheetFragment = new ReviewSheetFragment();
//                bundle.putString("title", prod);
//                reviewSheetFragment.setArguments(bundle);
//                reviewSheetFragment.show(getSupportFragmentManager()
//                        , reviewSheetFragment.getTag());
            }
        });
        card_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        rel_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( url_facebook!=null){
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_facebook));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_facebook)));
                }
            }
            }
        });
        rel_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( url_twitter!=null){
                    Intent i = getOpenTwitterIntent(ShowStoresActivity.this, url_twitter);
                    startActivity(i);
            }
            }
        });
        rel_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( url_telegram!=null){
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_telegram));
                        PackageManager pm = getPackageManager();
                        if (intent.resolveActivity(pm) != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(ShowStoresActivity.this, "Error message", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ignored) {
                    }
            }
            }
        });
        rel_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( url_instagram!=null){
                    Uri uri = Uri.parse(url_instagram);
                    Intent insta = new Intent(Intent.ACTION_VIEW, uri);
                    insta.setPackage("com.instagram.android");

                    if (isIntentAvailable(getApplicationContext(), insta)){
                        startActivity(insta);
                    } else{
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_instagram)));
                    }


            }
            }
        });
        rel_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( url_whatsapp!=null){
                    boolean installed = appInstalledOrNot("com.whatsapp");
                    if (installed){
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+url_whatsapp + "&text="));
                        startActivity(intent);
                    }else {
                        Toast.makeText(ShowStoresActivity.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
       getStores();


    }
    public static Intent getOpenTwitterIntent(Context c, String Username) {

        try {
            c.getPackageManager().getPackageInfo("com.twitter.android", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+ Username));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + Username));
        }
    }
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
    private void getStores() {
        Call<ShowStore> callNew = apiInterface.StoreDetails(stoeId);
        callNew.enqueue(new Callback<ShowStore>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ShowStore> callNew, Response<ShowStore> response) {
                Log.d("TAG111111",response.code()+"");
                ShowStore resource = response.body();
                if(resource.status){
                    storeId=resource.data.id;
                    tv_name.setText(resource.data.name);
                    tv_num_products.setText(""+resource.data.productsCount);
                    tv_customer_num.setText(""+resource.data.customersCount);
                    tv_rating_num.setText(""+resource.data.rate);
                    tv_number.setText(resource.data.phone);
                    tv_address.setText(resource.data.address);
                    url_telegram=resource.data.urlTelegram;
                    url_twitter=resource.data.urlTwitter;
                    url_whatsapp=resource.data.urlWhatsapp;
                    url_instagram=resource.data.urlInstagram;
                    url_facebook=resource.data.urlFacebook;
                    Picasso.get()
                            .load(resource.data.image)
                            .into(image);
                    imageStore=resource.data.image;
                    rateStore=resource.data.rate;
                    latitude=resource.data.lat;
                    longitude=resource.data.lng;


                }else
                    Toast.makeText(ShowStoresActivity.this, resource.message, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<ShowStore> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());

            }

        });
    }

}