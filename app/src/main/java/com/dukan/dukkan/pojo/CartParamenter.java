package com.dukan.dukkan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartParamenter {
    private final int product_id;
    private final String device_id;

    public CartParamenter(int product_id, String device_id) {
        this.product_id = product_id;
        this.device_id = device_id;
    }


}
