package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Pivot {

    @SerializedName("model_id")
    @Expose
    public String modelId;
    @SerializedName("role_id")
    @Expose
    public String roleId;
    @SerializedName("model_type")
    @Expose
    public String modelType;

}