package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("qty")
    @Expose
    public String qty;
    @SerializedName("price")
    @Expose
    public float price;
    @SerializedName("discount")
    @Expose
    public String discount;
    @SerializedName("total")
    @Expose
    public float total;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("user_id")
    @Expose
    public String userId;
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
}
