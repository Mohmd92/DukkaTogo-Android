package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestOffer {

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
    public Integer price;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("is_favorite")
    @Expose
    public Boolean isFavorite;
    @SerializedName("is_cart")
    @Expose
    public IsCart isCart;
    @SerializedName("rate")
    @Expose
    public Integer rate;

}