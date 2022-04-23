package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderStatistics {
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

        @SerializedName("total")
        @Expose
        public Integer total;
        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("orders")
        @Expose
        public List<Order> orders = null;

    }
    public class Order {

        @SerializedName("year")
        @Expose
        public Integer year;
        @SerializedName("month")
        @Expose
        public Integer month;
        @SerializedName("count")
        @Expose
        public Integer count;

    }
}
