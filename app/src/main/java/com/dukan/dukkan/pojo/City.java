package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class City {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("data")
    @Expose
    public List<Datum> data = new ArrayList<>();

    public static class Datum {

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
        @SerializedName("country")
        @Expose
        public Country country;
        @SerializedName("state")
        @Expose
        public State state;

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

    }
    public class State {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("country_id")
        @Expose
        public Integer countryId;
        @SerializedName("name")
        @Expose
        public String name;

    }
 }