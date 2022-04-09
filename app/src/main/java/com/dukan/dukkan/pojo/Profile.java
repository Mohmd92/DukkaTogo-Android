package com.dukan.dukkan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Profile {
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
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("email_verified_at")
        @Expose
        public Object emailVerifiedAt;
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
        public Object userId;
        @SerializedName("lat")
        @Expose
        public Object lat;
        @SerializedName("lng")
        @Expose
        public Object lng;
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
        @SerializedName("google_id")
        @Expose
        public Object googleId;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("postal_code")
        @Expose
        public String postalCode;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("store")
        @Expose
        public Store store;
        @SerializedName("orders")
        @Expose
        public List<OrderItem> orders = new ArrayList<>();
        @SerializedName("country")
        @Expose
        public Country country;
        @SerializedName("city")
        @Expose
        public City city;
        @SerializedName("roles")
        @Expose
        public List<Role> roles =  new ArrayList<>();

    }

    }