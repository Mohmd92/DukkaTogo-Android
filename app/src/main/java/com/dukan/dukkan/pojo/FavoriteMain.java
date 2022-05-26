package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteMain {

        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public List<Datum> data = null;

    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("product_id")
        @Expose
        public String productId;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("device_id")
        @Expose
        public String deviceId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("product")
        @Expose
        public Product product;
        @SerializedName("user")
        @Expose
        public UserProfile user;

    }
    public class Product {

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
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("is_favorite")
        @Expose
        public Boolean isFavorite;
        @SerializedName("is_cart")
        @Expose
        public IsCart isCart;
        @SerializedName("rate")
        @Expose
        public float rate;

    }
}