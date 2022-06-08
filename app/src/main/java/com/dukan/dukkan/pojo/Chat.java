package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = new ArrayList<>();
    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("deleted_at")
        @Expose
        public String deletedAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("participants")
        @Expose
        public List<Participant> participants =new ArrayList<>();
        @SerializedName("message")
        @Expose
        public Message message;

    }
    public class Participant {

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
        @SerializedName("pivot")
        @Expose
        public Pivot pivot;
    }
    public class Message {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("chat_id")
        @Expose
        public Integer chatId;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("deleted_at")
        @Expose
        public String deletedAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("string_created")
        @Expose
        public String stringCreated;

    }
}
