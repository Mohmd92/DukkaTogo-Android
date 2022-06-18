package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartMain2 {

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
        public List<Cart> carts = new ArrayList<>();
        @SerializedName("cart_total")
        @Expose
        public float cartTotal;
        @SerializedName("delivery_price")
        @Expose
        public Integer deliveryPrice;

        @SerializedName("total")
        @Expose
        public float total;

        @SerializedName("discount_price")
        @Expose
        public float discount_price;

        @SerializedName("redeem_points")
        @Expose
        public float redeem_points;

        @SerializedName("redeem_price")
        @Expose
        public float redeem_price;

        @SerializedName("discount_code")
        @Expose
        public String discount_code;

        @SerializedName("discount_message")
        @Expose
        public String discount_message;

        @SerializedName("redeem_message")
        @Expose
        public String redeem_message;

    }
  }