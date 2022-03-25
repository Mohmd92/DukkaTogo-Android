package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryProduct {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("category_id")
    @Expose
    public int categoryId;
    @SerializedName("store_id")
    @Expose
    public int storeId;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    public CategoryProduct(int id,String name,String description,String image,String userId,int categoryId,int storeId,String createdAt){
        this.id=id;
        this.name=name;
        this.description=description;
        this.image=image;
        this.userId=userId;
        this.categoryId=categoryId;
        this.storeId=storeId;
        this.createdAt=createdAt;
    }
}
