package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Country {
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
        @SerializedName("timezones")
        @Expose
        public List<Timezone> timezones = null;
        @SerializedName("currency")
        @Expose
        public Currency currency;

    }
    public class Timezone {

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
    public class Currency {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("country_id")
        @Expose
        public Integer countryId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("code")
        @Expose
        public String code;
        @SerializedName("precision")
        @Expose
        public Integer precision;
        @SerializedName("symbol")
        @Expose
        public String symbol;
        @SerializedName("symbol_native")
        @Expose
        public String symbolNative;
        @SerializedName("symbol_first")
        @Expose
        public Integer symbolFirst;
        @SerializedName("decimal_mark")
        @Expose
        public String decimalMark;
        @SerializedName("thousands_separator")
        @Expose
        public String thousandsSeparator;
    }
 }