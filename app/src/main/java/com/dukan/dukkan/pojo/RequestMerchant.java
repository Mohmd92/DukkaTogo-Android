package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RequestMerchant {

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

        @SerializedName("current_page")
        @Expose
        public Integer currentPage;
        @SerializedName("data")
        @Expose
        public List<Datum> data = null;
        @SerializedName("first_page_url")
        @Expose
        public String firstPageUrl;
        @SerializedName("from")
        @Expose
        public Integer from;
        @SerializedName("last_page")
        @Expose
        public Integer lastPage;
        @SerializedName("last_page_url")
        @Expose
        public String lastPageUrl;
        @SerializedName("links")
        @Expose
        public List<Link> links = null;
        @SerializedName("next_page_url")
        @Expose
        public String nextPageUrl;
        @SerializedName("path")
        @Expose
        public String path;
        @SerializedName("per_page")
        @Expose
        public Integer perPage;
        @SerializedName("prev_page_url")
        @Expose
        public String prevPageUrl;
        @SerializedName("to")
        @Expose
        public Integer to;
        @SerializedName("total")
        @Expose
        public Integer total;

    }
    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("delivery_id")
        @Expose
        public String deliveryId;
        @SerializedName("store_id")
        @Expose
        public String storeId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("string_status")
        @Expose
        public String stringStatus;
        @SerializedName("store")
        @Expose
        public Store store;
        @SerializedName("delivery")
        @Expose
        public Delivery delivery;

    }
    public class Delivery {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("email_verified_at")
        @Expose
        public String emailVerifiedAt;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("lat")
        @Expose
        public String lat;
        @SerializedName("lng")
        @Expose
        public String lng;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("country_id")
        @Expose
        public Integer countryId;
        @SerializedName("city_id")
        @Expose
        public Integer cityId;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("postal_code")
        @Expose
        public String postalCode;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("license_number")
        @Expose
        public String licenseNumber;
        @SerializedName("license_picture")
        @Expose
        public String licensePicture;
        @SerializedName("vehicle_picture")
        @Expose
        public String vehiclePicture;
        @SerializedName("google_id")
        @Expose
        public String googleId;
        @SerializedName("fcm_token")
        @Expose
        public String fcmToken;
        @SerializedName("status_merchant")
        @Expose
        public String statusMerchant;
        @SerializedName("reset_code")
        @Expose
        public String resetCode;
        @SerializedName("points")
        @Expose
        public String points;
        @SerializedName("paid_ponts")
        @Expose
        public String paidPonts;

    }
    public class Link {

        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("label")
        @Expose
        public String label;
        @SerializedName("active")
        @Expose
        public Boolean active;

    }
}
