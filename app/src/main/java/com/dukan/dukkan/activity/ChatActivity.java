package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerChatMessagesAdapter;
import com.dukan.dukkan.pojo.ChatMessage;
import com.dukan.dukkan.pojo.SendChat;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    APIInterface apiInterface;
    RecyclerView recyclerView;
    ImageView img_category,img_close,img_profile,img_profile_status;
    LinearLayout linear_chat_option;
    EditText edit_chat_text;
    TextView tv_name,tv_status;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        progressBar = findViewById(R.id.progressBar);
        ImageView img_file = findViewById(R.id.img_file);
        ImageView img_photo = findViewById(R.id.img_photo);
        ImageView img_voice = findViewById(R.id.img_voice);
        ImageView img_camera = findViewById(R.id.img_camera);
        RelativeLayout rel_send = findViewById(R.id.rel_send);
        RelativeLayout rel_category = findViewById(R.id.rel_category);
        edit_chat_text = findViewById(R.id.edit_chat_text);
        linear_chat_option = findViewById(R.id.linear_chat_option);
        img_profile = findViewById(R.id.img_profile);
        img_profile_status = findViewById(R.id.img_profile_status);
        tv_name = findViewById(R.id.tv_name);
        tv_status = findViewById(R.id.tv_status);
        img_category = findViewById(R.id.img_category);
        img_close = findViewById(R.id.img_close);
        recyclerView = findViewById(R.id.recyclerView);
        ImageView img_back = findViewById(R.id.img_back);

        tv_name.setText(getIntent().getExtras().getString("username"));


        Picasso.get()
                .load(getIntent().getExtras().getString("image"))
                .into(img_profile);
        if(getIntent().getExtras().getString("status").equals("0")) {
            img_profile_status.setVisibility(View.GONE);
            tv_status.setText(getString(R.string.offline));
        }



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rel_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(edit_chat_text.getText().toString()))
                    sendMessage();
            }
        });
        rel_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linear_chat_option.getVisibility()==View.GONE){
                    linear_chat_option.setVisibility(View.VISIBLE);
                    img_close.setVisibility(View.VISIBLE);
                    img_category.setVisibility(View.GONE);
                }else {
                    linear_chat_option.setVisibility(View.GONE);
                    img_close.setVisibility(View.GONE);
                    img_category.setVisibility(View.VISIBLE);
                }
            }
        });
        getMessages();
    }
    private void getMessages() {
        
        progressBar.setVisibility(View.VISIBLE);
        Call<ChatMessage> callNew = apiInterface.getChatsMessages(getIntent().getExtras().getInt("chat_id"));
        callNew.enqueue(new Callback<ChatMessage>() {
            @Override
            public void onResponse(Call<ChatMessage> callNew, Response<ChatMessage> response) {
                Log.d("TAG111111",response.code()+"");
                ChatMessage resource = response.body();
                if(resource.status) {
                    List<ChatMessage.Datum> datumList = resource.data;
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setReverseLayout(true);
                   // linearLayoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    RecyclerChatMessagesAdapter adapter = new RecyclerChatMessagesAdapter(getApplicationContext(), datumList);
                    recyclerView.setAdapter(adapter);
                   recyclerView.scrollToPosition(0);
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ChatMessage> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
    private void sendMessage() {

        progressBar.setVisibility(View.VISIBLE);
        Call<SendChat> callNew = apiInterface.SendChat(getIntent().getExtras().getInt("chat_id"),edit_chat_text.getText().toString());
        callNew.enqueue(new Callback<SendChat>() {
            @Override
            public void onResponse(Call<SendChat> callNew, Response<SendChat> response) {
                SendChat resource = response.body();
                if(resource.status) {
                    getMessages();
                    edit_chat_text.setText("");
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (getCurrentFocus() != null) {
                        inputMethodManager.hideSoftInputFromWindow(
                                getCurrentFocus().getWindowToken(), 0);
                    }
                }
                else
                    Toast.makeText(ChatActivity.this, ""+resource.message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<SendChat> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());
                progressBar.setVisibility(View.GONE);

            }

        });
    }
}