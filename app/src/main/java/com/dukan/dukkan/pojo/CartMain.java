package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartMain {
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

        @SerializedName("carts")
        @Expose
        public List<Cart> carts = null;
        @SerializedName("cart_total")
        @Expose
        public Integer cartTotal;

    }
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
        public Object user;

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
        public Integer price;
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
        public Boolean isCart;

    }
 }