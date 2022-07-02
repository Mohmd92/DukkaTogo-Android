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
    public Data data;

    public class Data {

        @SerializedName("order_number")
        @Expose
        public String orderNumber;
        @SerializedName("note")
        @Expose
        public Object note;
        @SerializedName("discount")
        @Expose
        public Integer discount;
        @SerializedName("total")
        @Expose
        public Integer total;
        @SerializedName("coupon")
        @Expose
        public Object coupon;
        @SerializedName("store_id")
        @Expose
        public String storeId;
        @SerializedName("delivery_id")
        @Expose
        public Object deliveryId;
        @SerializedName("address_id")
        @Expose
        public String addressId;
        @SerializedName("payment_gateway_id")
        @Expose
        public String paymentGatewayId;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("qr_code")
        @Expose
        public String qrCode;
        @SerializedName("string_status")
        @Expose
        public String stringStatus;

    }
}
