package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressEditParameter {


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
        @SerializedName("_method")
        @Expose
        public String _method;
        @SerializedName("device_id")
        @Expose
        public String device_id;
        @SerializedName("os")
        @Expose
        public String os;
    public AddressEditParameter(String name, String country, String city, String location, String _method, String device_id, String os) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.location = location;
        this._method = _method;
        this.device_id = device_id;
        this.os = os;
    }
}
