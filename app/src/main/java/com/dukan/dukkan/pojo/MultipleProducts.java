package com.dukan.dukkan.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**

 */

public class MultipleProducts {

    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public List<Datum> data = new ArrayList<>();
    public class Datum {
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("description")
        public String description;
        @SerializedName("price")
        public Integer price;
        @SerializedName("code")
        public String code;
        @SerializedName("user_id")
        public Integer user_id;
        @SerializedName("category_id")
        public Integer category_id;
        @SerializedName("brand_id")
        public String brand_id;
        @SerializedName("store_id")
        public Integer store_id;
        @SerializedName("image")
        public String image;
    }
}
