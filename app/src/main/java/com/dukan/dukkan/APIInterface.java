package com.dukan.dukkan;



import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Country;
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
import retrofit2.http.Path;
import retrofit2.http.Query;

/**

 */

public interface APIInterface {

    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/products")
    Call<MultipleProducts> doGetListProduct();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/stores?")
    Call<MultipleStore> doGetListStore(@Query("city_id") int city_id,@Query("country_id") int country_id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1")
    Call<Home> doGetListHome(@Query("device_id") String device_id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/categories")
    Call<Category> doGetListCategory();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/countries")
    Call<Country> doGetListCountry();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/cities/{id}")
    Call<City> doGetCity(@Path("id") Long id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/carts")
    Call<CartMain> doGetListCart(@Query("device_id") String device_id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/carts")
    Call<CartMain> cart(@Body CartParamenter cartParamenter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/carts")
    Call<CartMain> cartRemove(@Body CartRemoveParamenter cartRemoveParamenter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/login")
    Call<Login> login(@Body User user);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}
