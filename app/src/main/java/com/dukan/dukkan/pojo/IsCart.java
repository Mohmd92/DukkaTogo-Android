package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsCart{

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("price")
    @Expose
    public float price;
    @SerializedName("qty")
    @Expose
    public Integer qty;
    @SerializedName("total")
    @Expose
    public float total;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

}