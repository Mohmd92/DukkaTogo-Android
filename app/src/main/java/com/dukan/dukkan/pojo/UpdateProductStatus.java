package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductStatus {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("price")
        @Expose
        public float price;
        @SerializedName("code")
        @Expose
        public String code;
        @SerializedName("store_id")
        @Expose
        public String storeId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("order_status")
        @Expose
        public String orderStatus;
        @SerializedName("most_wanted")
        @Expose
        public String mostWanted;
        @SerializedName("new_product")
        @Expose
        public String newProduct;
        @SerializedName("device_id")
        @Expose
        public String deviceId;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("is_favorite")
        @Expose
        public Boolean isFavorite;
        @SerializedName("is_cart")
        @Expose
        public String isCart;
        @SerializedName("rate")
        @Expose
        public Integer rate;
        @SerializedName("images")
        @Expose
        public List<Image> images = new ArrayList<>();
        @SerializedName("store")
        @Expose
        public Store store;
        @SerializedName("category")
        @Expose
        public Category category;
        @SerializedName("brand")
        @Expose
        public Brand brand;
        @SerializedName("product_details")
        @Expose
        public List<ProductDetail> productDetails = new ArrayList<>();

    }
    public class ProductDetail {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("value")
        @Expose
        public String value;
        @SerializedName("unit")
        @Expose
        public String unit;
        @SerializedName("price")
        @Expose
        public String price;

    }
}
