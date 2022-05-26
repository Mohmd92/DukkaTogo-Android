package com.dukan.dukkan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**

 */

public class MultipleProducts {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("data")
    @Expose
    public Data data;
    public class Data {

        @SerializedName("products")
        @Expose
        public List<Product> products =new ArrayList<>();
        @SerializedName("advertisement")
        @Expose
        public Advertisement advertisement;



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
        @SerializedName("order_status")
        @Expose
        public String order_status;
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
}
