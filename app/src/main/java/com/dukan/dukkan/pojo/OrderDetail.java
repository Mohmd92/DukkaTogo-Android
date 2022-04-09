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
    public String price;
    @SerializedName("discount")
    @Expose
    public Object discount;
    @SerializedName("total")
    @Expose
    public String total;
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
