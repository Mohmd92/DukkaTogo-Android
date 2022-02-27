package com.dukan.dukkan;

import android.content.Context;

import com.dukan.dukkan.util.SharedPreferenceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anupamchugh on 05/01/17.
 */

public class APIClient {

    private static Retrofit retrofit = null;
    protected Context context;

    public static Retrofit getClient(Context context) {
        context = context.getApplicationContext();
        final String tokeen= SharedPreferenceManager.getInstance(context).get_api_token();
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://store.alkmal.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();

        OkHttpClient client2 = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer "+tokeen)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

         retrofit = new Retrofit.Builder()
                .client(client2)
                .baseUrl("https://store.alkmal.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
