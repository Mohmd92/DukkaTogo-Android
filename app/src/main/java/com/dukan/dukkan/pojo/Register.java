package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Register {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("user")
    @Expose
    public User user;
    public class User {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("country_id")
        @Expose
        public String countryId;
        @SerializedName("city_id")
        @Expose
        public String cityId;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("postal_code")
        @Expose
        public String postalCode;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("api_token")
        @Expose
        public String apiToken;
        @SerializedName("roles")
        @Expose
        public List<Role> roles =new ArrayList<>();

    }
 }