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

        @SerializedName("status_merchant")
        @Expose
        public String status_merchant;

        @SerializedName("reset_code")
        @Expose
        public String reset_code;

        @SerializedName("points")
        @Expose
        public String points;

        @SerializedName("paid_ponts")
        @Expose
        public String paid_ponts;

        @SerializedName("orders_count")
        @Expose
        public String orders_count;


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
        @SerializedName("license_number")
        @Expose
        public String licenseNumber;
        @SerializedName("license_picture")
        @Expose
        public String licensePicture;
        @SerializedName("vehicle_picture")
        @Expose
        public String vehiclePicture;
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
    public class City {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("country_id")
        @Expose
        public Integer countryId;
        @SerializedName("state_id")
        @Expose
        public Integer stateId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("deleted_at")
        @Expose
        public String deletedAt;

    }
    public class Country {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("iso2")
        @Expose
        public String iso2;
        @SerializedName("iso3")
        @Expose
        public String iso3;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("phone_code")
        @Expose
        public String phoneCode;
        @SerializedName("dialling_pattern")
        @Expose
        public String diallingPattern;
        @SerializedName("region")
        @Expose
        public String region;
        @SerializedName("sub_region")
        @Expose
        public String subRegion;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("deleted_at")
        @Expose
        public String deletedAt;

    }
    }