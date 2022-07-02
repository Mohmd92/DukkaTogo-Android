package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("store_id")
    @Expose
    public String storeId;
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("store")
    @Expose
    public Store store;
    @SerializedName("product")
    @Expose
    public NewProduct product;

}
