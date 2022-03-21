package com.dukan.dukkan;

import com.dukan.dukkan.pojo.Address;
import com.dukan.dukkan.pojo.AddressEditParameter;
import com.dukan.dukkan.pojo.AddressParameter;
import com.dukan.dukkan.pojo.AllAddress;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.ChangePassParameter;
import com.dukan.dukkan.pojo.ChangePassword;
import com.dukan.dukkan.pojo.CheckOutCart;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Country;
import com.dukan.dukkan.pojo.Coupon;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.MultipleResource;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.Rate;
import com.dukan.dukkan.pojo.RateParameter;
import com.dukan.dukkan.pojo.RateStore;
import com.dukan.dukkan.pojo.RateStoreParameter;
import com.dukan.dukkan.pojo.Register;
import com.dukan.dukkan.pojo.RegisterParameter;
import com.dukan.dukkan.pojo.ShowProduct;
import com.dukan.dukkan.pojo.ShowStore;
import com.dukan.dukkan.pojo.User;
import com.dukan.dukkan.pojo.UserList;
import com.dukan.dukkan.pojo.UserProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/products")
    Call<MultipleProducts> doGetListProduct(@Query("device_id") String device_id,@Query("os") String os,@Query("store_id") int store_id,@Query("brand_id") int brand_id
            ,@Query("category_id") int category_id,@Query("search") String search,@Query("new") int news,@Query("most_wanted") int most_wanted);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/stores?")
    Call<MultipleStore> doGetListStore(@Query("city_id") int city_id,@Query("country_id") int country_id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1")
    Call<Home> doGetListHome(@Query("device_id") String device_id,@Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/categories")
    Call<Category> doGetListCategory();

    @Headers({"api-token: API-TEST-TOKEN","Accept: application/json"})
    @GET("/api/v1/profile")
    Call<Profile> UserProfile();

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/profile")
    Call<Profile> updateProfile(@Body UserProfile userProfile);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/countries")
    Call<Country> doGetListCountry();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/cities/{id}")
    Call<City> doGetCity(@Path("id") Long id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/check_coupon/{id}")
    Call<Coupon> doCheckCoupon(@Path("id") String id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/address")
    Call<Address> AddAddress(@Body AddressParameter addressParameter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/address/{id}")
    Call<Address> EditAddress(@Path("id") int id,@Body AddressEditParameter addressEditParameter);

    @Headers({"api-token: API-TEST-TOKEN","Accept: application/json"})
    @GET("/api/v1/address")
    Call<AllAddress> GetAllAddress(@Query("device_id") String device_id,@Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN","Accept: application/json"})
    @POST("/api/v1/orders")
    Call<CheckOutCart> CreateOrderCart(@Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/favorites")
    Call<FavoriteMain> favorite(@Body CartParamenter cartParamenter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/register")
    Call<Register> register(@Body RegisterParameter registerParameter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/favorites")
    Call<FavoriteMain> favoriteRemove(@Body CartRemoveParamenter cartRemoveParamenter);


    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/favorites")
    Call<FavoriteMain> doGetListFavorite(@Query("device_id") String device_id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/carts")
    Call<CartMain> doGetListCart(@Query("device_id") String device_id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/product_rates")
    Call<Rate> ProductRates(@Query("product_id") int product_id, @Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/product_rates")
    Call<Rate> DoRate(@Body RateParameter rateParameter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/store_rates")
    Call<RateStore> DoStoreRate(@Body RateStoreParameter rateStoreParameter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/change_password")
    Call<ChangePassword> DoChangePassword(@Body ChangePassParameter changePassParameter);


    @Headers({"api-token: API-TEST-TOKEN","Accept: application/json"})
    @POST("/api/v1/carts")
    Call<CartMain> cart(@Query("device_id") String device_id,@Body CartParamenter cartParamenter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/carts")
    Call<CartMain> cartRemove(@Body CartRemoveParamenter cartRemoveParamenter);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/login")
    Call<Login> login(@Body User user);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/stores/{id}")
    Call<ShowStore> StoreDetails(@Path("id") int id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/products/{id}")
    Call<ShowProduct> productDetails(@Path("id") int id);

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
