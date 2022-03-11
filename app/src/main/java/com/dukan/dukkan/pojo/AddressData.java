package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressData {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("location")
        @Expose
        public String location;
        @SerializedName("zip_code")
        @Expose
        public String zipCode;
        @SerializedName("note")
        @Expose
        public String note;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("id")
        @Expose
        public Integer id;

}
