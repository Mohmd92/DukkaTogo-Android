package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Order {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("data")
    @Expose
    public List<Datum> data =new ArrayList<>();
    public class Datum {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("order_number")
        @Expose
        public String orderNumber;
        @SerializedName("note")
        @Expose
        public String note;
        @SerializedName("discount")
        @Expose
        public String discount;
        @SerializedName("total")
        @Expose
        public float total;
        @SerializedName("coupon")
        @Expose
        public String coupon;
        @SerializedName("buyer_id")
        @Expose
        public String buyerId;
        @SerializedName("driver_id")
        @Expose
        public String driverId;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("store_id")
        @Expose
        public String storeId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("device_id")
        @Expose
        public String deviceId;
        @SerializedName("delivery_id")
        @Expose
        public String deliveryId;
        @SerializedName("address_id")
        @Expose
        public String addressId;
        @SerializedName("payment_gateway_id")
        @Expose
        public String paymentGatewayId;
        @SerializedName("qr_code")
        @Expose
        public String qrCode;
        @SerializedName("store")
        @Expose
        public Store store;
        @SerializedName("user")
        @Expose
        public User user;
        @SerializedName("delivery")
        @Expose
        public Delivery delivery;
        @SerializedName("address")
        @Expose
        public AddressData address;
        @SerializedName("payment_gateway")
        @Expose
        public PaymentGateway paymentGateway;
        @SerializedName("order_details")
        @Expose
        public List<OrderDetail> orderDetails = new ArrayList<>();

    }
}
