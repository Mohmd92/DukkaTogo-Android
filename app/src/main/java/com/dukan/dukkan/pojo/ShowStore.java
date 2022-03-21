package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShowStore {

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
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("country_id")
        @Expose
        public String countryId;
        @SerializedName("city_id")
        @Expose
        public String cityId;
        @SerializedName("lat")
        @Expose
        public String lat;
        @SerializedName("lng")
        @Expose
        public String lng;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("url_facebook")
        @Expose
        public String urlFacebook;
        @SerializedName("url_instagram")
        @Expose
        public String urlInstagram;
        @SerializedName("url_whatsapp")
        @Expose
        public String urlWhatsapp;
        @SerializedName("url_twitter")
        @Expose
        public String urlTwitter;
        @SerializedName("url_telegram")
        @Expose
        public String urlTelegram;
        @SerializedName("products_count")
        @Expose
        public Integer productsCount;
        @SerializedName("advertisements")
        @Expose
        public List<Advertisement> advertisements =new ArrayList<>();
        @SerializedName("most_wanted")
        @Expose
        public List<NewProduct> mostWanted =new ArrayList<>();
        @SerializedName("new_products")
        @Expose
        public List<NewProduct> newProducts =new ArrayList<>();
        @SerializedName("latest_offers")
        @Expose
        public List<NewProduct> latestOffers = new ArrayList<>();
        @SerializedName("customers_count")
        @Expose
        public Integer customersCount;
        @SerializedName("rate")
        @Expose
        public Double rate;
        @SerializedName("store_time_works")
        @Expose
        public List<StoreTimeWork> storeTimeWorks = new ArrayList<>();
        @SerializedName("products")
        @Expose
        public List<NewProduct> products =new ArrayList<>();

    }
}
