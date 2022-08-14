package com.dukan.dukkan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.RecyclerLanguageAdapter;
import com.dukan.dukkan.adapter.RecyclerStoreAdapter;
import com.dukan.dukkan.model.DataModeLanguage;
import com.dukan.dukkan.util.SharedPreferenceManager;

import java.util.ArrayList;

public class LanguageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<DataModeLanguage> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languages);
        recyclerView =findViewById(R.id.recyclerView);
        ImageView img_back =findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getLanguages();
    }
    private void  getLanguages(){
        arrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        arrayList.add(new DataModeLanguage(1, getString(R.string.arabia), R.drawable.suadi,false));
        arrayList.add(new DataModeLanguage(2, getString(R.string.english), R.drawable.england,true));
        arrayList.add(new DataModeLanguage(3, getString(R.string.german), R.drawable.germany,false));
        arrayList.add(new DataModeLanguage(4, getString(R.string.italian), R.drawable.italy,false));
        arrayList.add(new DataModeLanguage(5, getString(R.string.turkish), R.drawable.turky,false));

        RecyclerLanguageAdapter adapter = new RecyclerLanguageAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

    }
}