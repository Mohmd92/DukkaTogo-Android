package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UpdateCouponStatus {

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
        @SerializedName("code")
        @Expose
        public String code;
        @SerializedName("value")
        @Expose
        public String value;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("count")
        @Expose
        public String count;
        @SerializedName("used")
        @Expose
        public String used;
        @SerializedName("expiration")
        @Expose
        public String expiration;
        @SerializedName("product_id")
        @Expose
        public String productId;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("store_id")
        @Expose
        public String storeId;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("type_status")
        @Expose
        public String typeStatus;
        @SerializedName("status")
        @Expose
        public Integer status;

    }
}
