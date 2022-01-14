package com.dukan.dukkan.pojo;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class User {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("api_token")
    @Expose
    public String apiToken;
    @SerializedName("password")
    @Expose
    public String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
