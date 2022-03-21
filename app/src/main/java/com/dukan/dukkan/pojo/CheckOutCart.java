package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CheckOutCart {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("data")
    @Expose
    public List<Datum> data = new ArrayList<>();

    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("order_number")
        @Expose
        public String orderNumber;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("device_id")
        @Expose
        public String deviceId;
        @SerializedName("total")
        @Expose
        public String total;
        @SerializedName("discount")
        @Expose
        public String discount;
        @SerializedName("coupon")
        @Expose
        public String coupon;
        @SerializedName("note")
        @Expose
        public String note;
        @SerializedName("delivery_id")
        @Expose
        public String deliveryId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("address_id")
        @Expose
        public String addressId;
        @SerializedName("store_id")
        @Expose
        public String storeId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }
}