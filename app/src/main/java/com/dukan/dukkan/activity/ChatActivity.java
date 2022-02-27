package com.dukan.dukkan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;

public class ChatActivity extends AppCompatActivity {
    APIInterface apiInterface;
    RecyclerView recyclerView;
    ImageView img_category,img_close,img_profile,img_profile_status;
    LinearLayout linear_chat_option;
    EditText edit_chat_text;
    TextView tv_name,tv_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
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
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rel_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

}