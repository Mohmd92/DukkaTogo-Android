package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Delivery {

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
    public String emailVerifiedAt;
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
    public String userId;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lng")
    @Expose
    public String lng;
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


}