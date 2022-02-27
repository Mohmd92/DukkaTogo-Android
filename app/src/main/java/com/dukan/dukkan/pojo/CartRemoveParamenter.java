package com.dukan.dukkan.pojo;

public class CartRemoveParamenter {
    private int product_id;
    private String device_id;
    private int qty;
    private String _method;

    public CartRemoveParamenter(int product_id, String device_id, int qty, String _method) {
        this.product_id = product_id;
        this.device_id = device_id;
        this.qty = qty;
        this._method = _method;
    }


}
