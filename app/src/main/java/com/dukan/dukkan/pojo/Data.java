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
    public List<Store> stores = new ArrayList<>();
    @SerializedName("most_wanted")
    @Expose
    public List<MostWanted> mostWanted = new ArrayList<>();
    @SerializedName("new_products")
    @Expose
    public List<NewProduct> newProducts =new ArrayList<>();
    @SerializedName("brands")
    @Expose
    public List<Brand> brands =new ArrayList<>();
    @SerializedName("deliveries")
    @Expose
    public List<Delivery> deliveries = new ArrayList<>();
    @SerializedName("advertisement")
    @Expose
    public Advertisement advertisement;
    @SerializedName("advertisement2")
    @Expose
    public Advertisement2 advertisement2;

}