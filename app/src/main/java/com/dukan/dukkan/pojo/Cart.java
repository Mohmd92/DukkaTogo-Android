package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("qty")
    @Expose
    public Integer qty;
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("store_id")
    @Expose
    public String storeId;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("product")
    @Expose
    public NewProduct product;
    @SerializedName("user")
    @Expose
    public UserProfile user;
 }