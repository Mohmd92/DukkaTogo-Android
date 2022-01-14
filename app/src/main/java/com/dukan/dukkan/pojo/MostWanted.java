package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostWanted {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("category_id")
    @Expose
    public Integer categoryId;
    @SerializedName("brand_id")
    @Expose
    public Object brandId;
    @SerializedName("store_id")
    @Expose
    public Integer storeId;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;
    @SerializedName("image")
    @Expose
    public String image;

}