package com.dukan.dukkan.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterParameter {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("password_confirmation")
        @Expose
        public String password_confirmation;
        @SerializedName("country_id")
        @Expose
        public long country_id;
        @SerializedName("city_id")
        @Expose
        public long city_id;

        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("postal_code")
        @Expose
        public String postal_code;

    public RegisterParameter(String name, String email, String mobile, String password, String password_confirmation, long country_id, long city_id, String username, String address, String postal_code) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.country_id = country_id;
        this.city_id = city_id;
        this.username = username;
        this.address = address;
        this.postal_code = postal_code;
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+name);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+email);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+mobile);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+password);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+password_confirmation);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+country_id);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+city_id);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+username);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+address);
        System.out.println("SWQQQQQQQQQQQQQQQQQQQ "+postal_code);
    }
}
