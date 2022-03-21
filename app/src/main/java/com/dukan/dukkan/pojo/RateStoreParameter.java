package com.dukan.dukkan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateStoreParameter {
    @SerializedName("store_id")
    @Expose
    public Integer store_id;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("rate")
    @Expose
    public float rate;
    @SerializedName("os")
    @Expose
    public String os;
    @SerializedName("device_id")
    @Expose
    public String device_id;

    public RateStoreParameter(int store_id, String comment, float rate, String os, String device_id) {
        this.store_id = store_id;
        this.comment = comment;
        this.rate = rate;
        this.os = os;
        this.device_id = device_id;
    }

}
