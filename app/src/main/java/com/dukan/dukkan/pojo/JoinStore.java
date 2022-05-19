package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinStore {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;
    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("store_id")
        @Expose
        public String storeId;
        @SerializedName("user_id")
        @Expose
        public String userId;
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
}
