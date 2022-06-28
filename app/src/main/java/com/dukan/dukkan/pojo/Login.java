package com.dukan.dukkan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login {
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
        @SerializedName("api_token")
        @Expose
        public String apiToken;
        @SerializedName("country")
        @Expose
        public Country country;
        @SerializedName("city")
        @Expose
        public City city;
        @SerializedName("roles")
        @Expose
        public List<Role> roles = null;

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
        public String status;
        @SerializedName("deleted_at")
        @Expose
        public Object deletedAt;

    }
    public class City {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("country_id")
        @Expose
        public String countryId;
        @SerializedName("state_id")
        @Expose
        public String stateId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("deleted_at")
        @Expose
        public String deletedAt;

    }
    public class Role {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("guard_name")
        @Expose
        public String guardName;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("pivot")
        @Expose
        public Pivot pivot;

    }
}
