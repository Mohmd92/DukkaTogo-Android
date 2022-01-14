package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public Object description;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("phone")
    @Expose
    public Object phone;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("email")
    @Expose
    public Object email;
    @SerializedName("address")
    @Expose
    public Object address;
    @SerializedName("country_id")
    @Expose
    public Integer countryId;
    @SerializedName("city_id")
    @Expose
    public Integer cityId;
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
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;
    @SerializedName("status")
    @Expose
    public Integer status;

}