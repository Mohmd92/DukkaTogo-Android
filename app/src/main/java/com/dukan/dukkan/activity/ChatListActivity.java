package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerChatListAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.pojo.Chat;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListActivity extends AppCompatActivity {
    APIInterface apiInterface;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout linear_no_account;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        RelativeLayout rel_new_chat = findViewById(R.id.rel_new_chat);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        linear_no_account =findViewById(R.id.linear_no_account);
        scrollView =findViewById(R.id.scrollView);
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rel_new_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        Button but_login = findViewById(R.id.but_login);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatListActivity.this, LoginActivity.class));
                finish();
            }
        });
        if(SharedPreferenceManager.getInstance(getBaseContext()).get_api_token().equals("")) {
            scrollView.setVisibility(View.GONE);
            linear_no_account.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            linear_no_account.setVisibility(View.GONE);
            getChats();
        }
    }
    private void getChats() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Chat> callNew = apiInterface.getChatsList();
        callNew.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> callNew, Response<Chat> response) {
                Log.d("TAG111111", response.code() + "");
                Chat resource = response.body();
                if(resource.status){
                    List<Chat.Datum> datumList = resource.data;

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RecyclerChatListAdapter adapter = new RecyclerChatListAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);


                }else
                    Toast.makeText(ChatListActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }

}