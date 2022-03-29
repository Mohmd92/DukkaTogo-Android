package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreTimeWork {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("store_id")
    @Expose
    public String storeId;
    @SerializedName("day")
    @Expose
    public String day;
    @SerializedName("from")
    @Expose
    public String from;
    @SerializedName("to")
    @Expose
    public String to;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public String deletedAt;
}
