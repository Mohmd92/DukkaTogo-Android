package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("sliders")
    @Expose
    public List<Slider> sliders = new ArrayList<>();
    @SerializedName("stores")
    @Expose
    public List<Store> stores = null;
    @SerializedName("most_wanted")
    @Expose
    public List<MostWanted> mostWanted = null;
    @SerializedName("new_products")
    @Expose
    public List<NewProduct> newProducts = null;
    @SerializedName("brands")
    @Expose
    public List<Brand> brands = null;
    @SerializedName("deliveries")
    @Expose
    public List<Object> deliveries = null;

}