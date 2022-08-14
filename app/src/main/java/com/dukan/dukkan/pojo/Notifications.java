package com.dukan.dukkan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Notifications {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = new ArrayList<>();

    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("type_id")
        @Expose
        public String typeId;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("read_at")
        @Expose
        public Object readAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        public Object deletedAt;
        @SerializedName("external")
        @Expose
        public Integer external;
        @SerializedName("url")
        @Expose
        public Object url;

    }
}
