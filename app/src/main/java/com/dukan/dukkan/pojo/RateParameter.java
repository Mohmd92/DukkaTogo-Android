package com.dukan.dukkan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateParameter {
    @SerializedName("product_id")
    @Expose
    public Integer product_id;
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

    public RateParameter(int product_id, String comment, float rate, String os, String device_id) {
        this.product_id = product_id;
        this.comment = comment;
        this.rate = rate;
        this.os = os;
        this.device_id = device_id;
    }

}
