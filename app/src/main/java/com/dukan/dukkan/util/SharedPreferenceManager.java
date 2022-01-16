package com.dukan.dukkan.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SharedPreferenceManager instance;


    private SharedPreferenceManager(Context context) {
        if (context != null) {
            this.mContext = context;
            sharedPreferences = context.getSharedPreferences("app_sps", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }
    public static SharedPreferenceManager getInstance(Context context) {
        if (instance == null) return new SharedPreferenceManager(context);

        return instance;
    }
    public String getUser_Name() {
        return sharedPreferences.getString("User_Name", null);
    }

    public void setUser_Name(String User_Name) {
        editor.putString("User_Name", User_Name);
        editor.commit();
    }

    /////////////
    public String getStatus() {
        return sharedPreferences.getString("Status", null);
    }

    public void setStatus(String Status) {
        editor.putString("Status", Status);
        editor.commit();
    }
    public String getPassword() {
        return sharedPreferences.getString("Password", null);
    }

    public void setPassword(String Password) {
        editor.putString("Password", Password);
        editor.commit();
    }
    /////////////
    public String get_api_token() {
        return sharedPreferences.getString("api_token", "");
    }

    public void set_api_token(String api_token) {
        editor.putString("api_token", api_token);
        editor.commit();
    }

    public String get_email() {
        return sharedPreferences.getString("email", "");
    }

    public void set_email(String email) {
        editor.putString("email", email);
        editor.commit();
    }
    public String getSMScode() {
        return sharedPreferences.getString("SMScode", null);
    }

    public void setSMScode(String SMScode) {
        editor.putString("SMScode", SMScode);
        editor.commit();
    }

    public String getCountry() {
        return sharedPreferences.getString("Country", null);
    }

    public void setCountry(String Country) {
        editor.putString("Country", Country);
        editor.commit();
    }

    public String getCity() {
        return sharedPreferences.getString("City", null);
    }

    public void setCity(String City) {
        editor.putString("City", City);
        editor.commit();
    }
}
