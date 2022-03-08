package com.dukan.dukkan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Rate {
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
        @SerializedName("comment")
        @Expose
        public String comment;
        @SerializedName("rate")
        @Expose
        public String rate;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("product")
        @Expose
        public NewProduct product;
        @SerializedName("user")
        @Expose
        public UserProfile user;

}
}