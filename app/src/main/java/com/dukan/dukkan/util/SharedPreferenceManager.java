package com.market.dokan.util;

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
            sharedPreferences = context.getSharedPreferences("app_sp", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }
    public Boolean getNotificationStatus() {
        return sharedPreferences.getBoolean("NotificationStatus", false);
    }

    public void setNotificationStatus(Boolean NotificationStatus) {
        editor.putBoolean("NotificationStatus", NotificationStatus);
        editor.commit();
    }
    /////////////
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
    public String getDealer() {
        return sharedPreferences.getString("Dealer", null);
    }

    public void setDealer(String Dealer) {
        editor.putString("Dealer", Dealer);
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
    /////////////
    // ///////////
    public String getUserType() {
        return sharedPreferences.getString("UserType", null);
    }

    public void setUserType(String UserType) {
        editor.putString("UserType", UserType);
        editor.commit();
    }
    /////////////

    public String getPassword() {
        return sharedPreferences.getString("Password", null);
    }

    public void setPassword(String Password) {
        editor.putString("Password", Password);
        editor.commit();
    }
    /////////////
    public String getuid() {
        return sharedPreferences.getString("uid", "");
    }

    public void setuid(String uid) {
        editor.putString("uid", uid);
        editor.commit();
    }

    public String getphone() {
        return sharedPreferences.getString("phone", "");
    }

    public void setphone(String phone) {
        editor.putString("phone", phone);
        editor.commit();
    }
    /////////////
    public Boolean getInAppNotifications() {
        return sharedPreferences.getBoolean("InAppNotifications", false);
    }

    public void setInAppNotifications(Boolean InAppNotifications) {
        editor.putBoolean("InAppNotifications", InAppNotifications);
        editor.commit();
    }
    /////////////
    public void setBadge2Chat(int id) {
        editor.putInt("badge", id);
        editor.commit();
    }

    public int getBadge2Chat() {
        return sharedPreferences.getInt("badge", 0);
    }
    public int getBadgeCount(){
        return sharedPreferences.getInt("BadgeCount", 0);
    }
    public void setBadgeCount(int BadgeCount){
        editor.putInt("BadgeCount",BadgeCount);
        editor.commit();
    }

public String getCustomerName() {
    return sharedPreferences.getString("CustomerName", "");
}

    public void setCustomerName(String CustomerName) {
        editor.putString("CustomerName", CustomerName);
        editor.commit();
    }
 /////////////////
public String getCustomerId() {
    return sharedPreferences.getString("CustomerId", "");
}

    public void setCustomerId(String CustomerId) {
        editor.putString("CustomerId", CustomerId);
        editor.commit();
    }


}
