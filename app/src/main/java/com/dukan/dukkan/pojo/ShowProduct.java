package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowProduct {


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
        @SerializedName("similar_products")
        @Expose
        public List<NewProduct> similarProducts = null;
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
        public Integer rate;
        @SerializedName("images")
        @Expose
        public List<Image> images = null;
        @SerializedName("store")
        @Expose
        public Store store;
        @SerializedName("category")
        @Expose
        public CategoryProduct category;
        @SerializedName("brand")
        @Expose
        public Brand brand;
        @SerializedName("product_details")
        @Expose
        public List<ProductDetail> productDetails = null;
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
            @SerializedName("key")
            @Expose
            public String key;
        }
    }
}
