package com.dukan.dukkan.pojo;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserProfile {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("email_verified_at")
    @Expose
    public Object emailVerifiedAt;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("user_id")
    @Expose
    public Object userId;
    @SerializedName("lat")
    @Expose
    public Object lat;
    @SerializedName("lng")
    @Expose
    public Object lng;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("country_id")
    @Expose
    public String countryId;
    @SerializedName("city_id")
    @Expose
    public String cityId;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("postal_code")
    @Expose
    public String postalCode;
    @SerializedName("username")
    @Expose
    public String username;

    public UserProfile(String name, String email, String countryId, String cityId, String address, String postalCode, String mobile, String image) {
        this.name = name;
        this.email = email;
        this.countryId = countryId;
        this.cityId = cityId;
        this.address = address;
        this.postalCode = postalCode;
        this.mobile = mobile;
        this.image = image;
    }
}
