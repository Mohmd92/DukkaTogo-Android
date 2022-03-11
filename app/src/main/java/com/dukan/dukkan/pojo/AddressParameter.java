package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressParameter {


        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("location")
        @Expose
        public String location;
        @SerializedName("device_id")
        @Expose
        public String device_id;
        @SerializedName("os")
        @Expose
        public String os;

    public AddressParameter(String name, String country, String city, String location, String device_id, String os) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.location = location;
        this.device_id = device_id;
        this.os = os;
    }
}
