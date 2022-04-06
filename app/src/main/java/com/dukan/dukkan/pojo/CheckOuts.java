package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CheckOuts {
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
        public List<Cart> carts =new ArrayList<>();
        @SerializedName("cart_total")
        @Expose
        public Integer cartTotal;
        @SerializedName("delivery_price")
        @Expose
        public Integer deliveryPrice;
        @SerializedName("total")
        @Expose
        public Integer total;
        @SerializedName("payment_gateway")
        @Expose
        public List<PaymentGateway> paymentGateway =new ArrayList<>();
        @SerializedName("addresses")
        @Expose
        public List<AddressData> addresses = new ArrayList<>();

    }
}
