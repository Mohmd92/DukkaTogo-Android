package com.dukan.dukkan.model;


import com.google.gson.annotations.SerializedName;

public class DataModeStore {

    public Integer id;
    public String name;
    public String description;
    public String image;
    public Integer user_id;
    public String phone;
    public Integer mobile;
    public String email;
    public String address;
    public Integer country_id;
    public Integer city_id;
    public String lat;
    public String lng;
    public Integer status;


    public DataModeStore(Integer ids, String name2,String description2,String image2, Integer user_id2, String phone2, Integer mobile2, String email2,
                         String address2, Integer country_id2, Integer city_id2, String lat2, String lng2, Integer status2)
    {
        id=ids;
        name=name2;
        description=description2;
        image=image2;
        user_id=user_id2;
        phone=phone2;
        mobile=mobile2;
        email=email2;
        address=address2;
        country_id=country_id2;
        city_id=city_id2;
        lat=lat2;
        lng=lng2;
        status=status2;
    }
}
