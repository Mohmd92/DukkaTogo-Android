package com.dukan.dukkan.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**

 */

public class MultipleStore {
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public List<Datum> data = new ArrayList<>();
    public class Datum {
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("description")
        public String description;
        @SerializedName("image")
        public String image;
        @SerializedName("user_id")
        public Integer user_id;
        @SerializedName("phone")
        public String phone;
        @SerializedName("mobile")
        public Integer mobile;
        @SerializedName("email")
        public String email;
        @SerializedName("address")
        public String address;
        @SerializedName("country_id")
        public Integer country_id;
        @SerializedName("city_id")
        public Integer city_id;
        @SerializedName("lat")
        public String lat;
        @SerializedName("lng")
        public String lng;
        @SerializedName("status")
        public Integer status;

    }
}
