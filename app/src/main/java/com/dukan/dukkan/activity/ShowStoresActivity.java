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
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
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
import com.dukan.dukkan.fragment.ReviewStoreSheetFragment;
import com.dukan.dukkan.pojo.Advertisement;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.ChatNew;
import com.dukan.dukkan.pojo.ShowStore;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.squareup.picasso.Picasso;
import com.yihsian.slider.library.SliderItemView;
import com.yihsian.slider.library.SliderLayout;

import java.nio.ShortBuffer;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class ShowStoresActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    private Toolbar toolbar;
    ImageView image;
    TextView tv_num_products,tv_rating_num,tv_customer_num,tv_number,tv_address,tv_name;
    RelativeLayout rel_product_list;
    String url_telegram,url_twitter,url_whatsapp,url_instagram,url_facebook;
    int storeId;
    float rateStore;
    String imageStore;
    String latitude, longitude;
    private SliderLayout sliderLayout;
    int tempCount=0;
    TextView tv_sala;
    ProgressBar progressBar;
    String image_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_store);
        Bundle extras = getIntent().getExtras();
        storeId= extras.getInt("StoreID");
        recyclerView =findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar2);
        image = findViewById(R.id.image);
        tv_num_products = findViewById(R.id.tv_num_products);
        tv_rating_num = findViewById(R.id.tv_rating_num);
        progressBar = findViewById(R.id.progressBar);
        tv_customer_num = findViewById(R.id.tv_customer_num);
        tv_name = findViewById(R.id.tv_name);
        tv_number = findViewById(R.id.tv_number);
        tv_address = findViewById(R.id.tv_address);
        sliderLayout = findViewById(R.id.sliderLayout);
        ImageView icon_filter =toolbar.findViewById(R.id.icon_filter);
        ImageView icon_search =toolbar.findViewById(R.id.icon_search);
        ImageView iconMenu =toolbar.findViewById(R.id.icon_menu);
        ImageView iconBack =toolbar.findViewById(R.id.icon_back);
        ImageView icon_buy =toolbar.findViewById(R.id.icon_buy);
         tv_sala =toolbar.findViewById(R.id.tv_sala);
        FrameLayout frame_buy = toolbar.findViewById(R.id.frame_buy);
        icon_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowStoresActivity.this, CartActivity.class));
                finish();
            }
        });
        frame_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowStoresActivity.this, CartActivity.class));
                finish();
            }
        });
        if(extras.getString("user").equals("delivery")){
            icon_buy.setVisibility(View.GONE);
            frame_buy.setVisibility(View.GONE);
        }
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowStoresActivity.this, NotificationsActivity.class));

            }
        });
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
                if(latitude!=null && longitude!=null){
                    float lat= Float.parseFloat(latitude);
                    float lng= Float.parseFloat(longitude);
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
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
                Bundle bundle = new Bundle();
                ReviewStoreSheetFragment reviewSheetFragment = new ReviewStoreSheetFragment();
                bundle.putInt("storeIds", storeId);
                reviewSheetFragment.setArguments(bundle);
                reviewSheetFragment.show(getSupportFragmentManager()
                        , reviewSheetFragment.getTag());

            }
        });
        card_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateChat();
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
        Call<ShowStore> callNew = apiInterface.StoreDetails(storeId);
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
                    rateStore=0;//resource.data.rate;
                    latitude=resource.data.lat;
                    longitude=resource.data.lng;
                    List<Advertisement> advertisement1 = resource.data.advertisements;
                    SliderItemView view01 = new SliderItemView(getApplicationContext());
                    for (Advertisement datum : advertisement1) {
                        view01 = new SliderItemView(getApplicationContext());
                        view01.setItem2(datum.image,datum.title,"");
                        sliderLayout.addSlider(view01);
                    }
                    if(advertisement1.size()==0)
                        sliderLayout.setVisibility(View.GONE);

                }else
                    Toast.makeText(ShowStoresActivity.this, resource.message, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<ShowStore> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());

            }

        });
    }
    private void CreateChat() {
        progressBar.setVisibility(View.VISIBLE);
        Call<ChatNew> callNew = apiInterface.NewChat(storeId);
        callNew.enqueue(new Callback<ChatNew>() {
            @Override
            public void onResponse(Call<ChatNew> callNew, Response<ChatNew> response) {
                ChatNew resource = response.body();
                if(resource.status) {
                    Intent i2 = new Intent(ShowStoresActivity.this, ChatActivity.class);
                    i2.putExtra("username", tv_name.getText().toString());
                    i2.putExtra("image", imageStore);
                    i2.putExtra("status", String.valueOf(resource.data.status));
                    i2.putExtra("chat_id", resource.data.id);
                    i2.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i2);
                    progressBar.setVisibility(View.GONE);

                }
                else
                    Toast.makeText(ShowStoresActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<ChatNew> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
}