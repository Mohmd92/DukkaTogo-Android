package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Request {
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

        @SerializedName("current_page")
        @Expose
        public Integer currentPage;
        @SerializedName("data")
        @Expose
        public List<Datum> data = new ArrayList<>();
        @SerializedName("first_page_url")
        @Expose
        public String firstPageUrl;
        @SerializedName("from")
        @Expose
        public Integer from;
        @SerializedName("last_page")
        @Expose
        public Integer lastPage;
        @SerializedName("last_page_url")
        @Expose
        public String lastPageUrl;
        @SerializedName("links")
        @Expose
        public List<Link> links = new ArrayList<>();
        @SerializedName("next_page_url")
        @Expose
        public String nextPageUrl;
        @SerializedName("path")
        @Expose
        public String path;
        @SerializedName("per_page")
        @Expose
        public Integer perPage;
        @SerializedName("prev_page_url")
        @Expose
        public String prevPageUrl;
        @SerializedName("to")
        @Expose
        public Integer to;
        @SerializedName("total")
        @Expose
        public Integer total;

    }

    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("delivery_id")
        @Expose
        public String deliveryId;
        @SerializedName("store_id")
        @Expose
        public String storeId;
        @SerializedName("status")
        @Expose
        public Object status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("store")
        @Expose
        public Store store;

    }
    public class Link {

        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("label")
        @Expose
        public String label;
        @SerializedName("active")
        @Expose
        public Boolean active;
    }
}
