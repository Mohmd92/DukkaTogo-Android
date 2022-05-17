package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StoreTimes {
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

        @SerializedName("day")
        @Expose
        public String day;
        @SerializedName("from")
        @Expose
        public String from;
        @SerializedName("to")
        @Expose
        public String to;
        @SerializedName("status")
        @Expose
        public Boolean status;
    }
}