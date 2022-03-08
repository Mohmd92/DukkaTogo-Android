package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public Object name;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("ext")
    @Expose
    public String ext;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

}