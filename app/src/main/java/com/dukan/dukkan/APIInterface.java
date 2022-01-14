package com.dukan.dukkan;


import com.dukan.dukkan.pojo.Data;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.MultipleResource;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.User;
import com.dukan.dukkan.pojo.UserList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**

 */

public interface APIInterface {

    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/products")
    Call<MultipleProducts> doGetListProduct();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/stores")
    Call<MultipleStore> doGetListStore();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1")
    Call<Home> doGetListHome();



    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/login")
    Call<Login> login(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}
