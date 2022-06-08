package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendChat {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("data")
    @Expose
    public Data data;
    public class Data {

        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("chat_id")
        @Expose
        public Integer chatId;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("string_created")
        @Expose
        public String stringCreated;
        @SerializedName("user")
        @Expose
        public User user;

    }
}
