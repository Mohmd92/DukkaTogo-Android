package com.dukan.dukkan;

import com.dukan.dukkan.pojo.Address;
import com.dukan.dukkan.pojo.AddressEditParameter;
import com.dukan.dukkan.pojo.AddressParameter;
import com.dukan.dukkan.pojo.AllAddress;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartMain2;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Category;
import com.dukan.dukkan.pojo.ChangePassParameter;
import com.dukan.dukkan.pojo.ChangePassword;
import com.dukan.dukkan.pojo.Chat;
import com.dukan.dukkan.pojo.ChatMessage;
import com.dukan.dukkan.pojo.ChatNew;
import com.dukan.dukkan.pojo.CheckOuts;
import com.dukan.dukkan.pojo.CheckOutCart;
import com.dukan.dukkan.pojo.City;
import com.dukan.dukkan.pojo.Country;
import com.dukan.dukkan.pojo.CouponList;
import com.dukan.dukkan.pojo.CouponMain;
import com.dukan.dukkan.pojo.Driver;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.Home;
import com.dukan.dukkan.pojo.JoinStore;
import com.dukan.dukkan.pojo.Login;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.MultipleResource;
import com.dukan.dukkan.pojo.MultipleStore;
import com.dukan.dukkan.pojo.Notifications;
import com.dukan.dukkan.pojo.Order;
import com.dukan.dukkan.pojo.OrderStatistics;
import com.dukan.dukkan.pojo.OrderToDelevey;
import com.dukan.dukkan.pojo.Privacy;
import com.dukan.dukkan.pojo.Profile;
import com.dukan.dukkan.pojo.QrCode;
import com.dukan.dukkan.pojo.Rate;
import com.dukan.dukkan.pojo.RateParameter;
import com.dukan.dukkan.pojo.RateStore;
import com.dukan.dukkan.pojo.RateStoreParameter;
import com.dukan.dukkan.pojo.Register;
import com.dukan.dukkan.pojo.RegisterParameter;
import com.dukan.dukkan.pojo.Request;
import com.dukan.dukkan.pojo.RequestMerchant;
import com.dukan.dukkan.pojo.RequestStatus;
import com.dukan.dukkan.pojo.SendChat;
import com.dukan.dukkan.pojo.ShowOrder;
import com.dukan.dukkan.pojo.ShowProduct;
import com.dukan.dukkan.pojo.ShowStore;
import com.dukan.dukkan.pojo.StoreTimes;
import com.dukan.dukkan.pojo.UpdateCouponStatus;
import com.dukan.dukkan.pojo.UpdateProductStatus;
import com.dukan.dukkan.pojo.User;
import com.dukan.dukkan.pojo.UserList;
import com.dukan.dukkan.pojo.UserProfile;
import com.dukan.dukkan.pojo.Video;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/products")
    Call<MultipleProducts> doGetListProduct(@Query("device_id") String device_id,
                                            @Query("os") String os,
                                            @Query("store_id") int store_id,
                                            @Query("brand_id") int brand_id
                                          , @Query("category_id") int category_id,
                                            @Query("search") String search,
                                            @Query("new") int news,
                                            @Query("most_wanted") int most_wanted,
                                            @Query("price_from") int price_from,
                                            @Query("price_to") int price_to);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/products")
    Call<MultipleProducts> doGetListProduct(@Query("device_id") String device_id,
                                            @Query("os") String os,
                                            @Query("store_id") int store_id,
                                            @Query("brand_id") int brand_id
                                          , @Query("category_id") int category_id,
                                            @Query("search") String search,
                                            @Query("new") int news,
                                            @Query("most_wanted") int most_wanted,
                                            @Query("price_from") int price_from,
                                            @Query("price_to") int price_to,
                                            @Query("api_token") String token);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/stores?")
    Call<MultipleStore> doGetListStore(@Query("city_id") int city_id, @Query("country_id") int country_id);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/stores?")
    Call<MultipleStore> doGetListStoreDelivery(@Query("city_id") int city_id, @Query("country_id") int country_id, @Query("need_delivery") int need_delivery, @Query("delivery_stores") int delivery_stores);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @POST("/api/v1/stores/{id}/join")
    Call<JoinStore> JoinStores(@Path("id") int id);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/pages")
    Call<Privacy> getPrivacyList();

//    @Headers({"api-token: API-TEST-TOKEN","Accept: application/json"})
//    @GET("/api/v1/requests")
//    Call<RequestMerchant> getRequests();

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/requests")
    Call<RequestMerchant> getRequestsMerchant();

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/chats")
    Call<Chat> getChatsList();

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/messages?")
    Call<ChatMessage> getChatsMessages(@Query("chat_id") int chat_id);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @POST("/api/v1/messages?")
    Call<SendChat> SendChat(@Query("chat_id") int chat_id, @Field("message") String message);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @POST("/api/v1/chats")
    Call<ChatNew> NewChat(@Field("user_id") int user_id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1")
    Call<Home> doGetListHome(@Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/videos?")
    Call<Video> doGetListVideo(@Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/orders/statistics?")
    Call<OrderStatistics> GetOrderStatistics(@Query("device_id") String device_id, @Query("os") String os, @Query("date_from") String date_from, @Query("date_to") String date_to);


    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/categories?")
    Call<Category> doGetListCategoryStore(@Query("store_id") int store_id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/categories")
    Call<Category> doGetListCategory();


    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/profile")
    Call<Profile> UserProfile();

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/profile")
    Call<Profile> updateProfile(@Body UserProfile userProfile);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/countries")
    Call<Country> doGetListCountry();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/check_coupon")
    Call<CouponList> doGetListCoupon();

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/cities/{id}")
    Call<City> doGetCity(@Path("id") Long id);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/check_coupon/{id}")
    Call<CouponMain> doCheckCoupon(@Path("id") String id);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @POST("/api/v1/address")
    Call<Address> AddAddress(@Field("name") String name, @Field("location") String location, @Query("device_id") String device_id, @Query("os") String os);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @POST("/api/v1/request_delivery_status")
    Call<RequestStatus> RequestDeliveryStatus(@Field("request_id") int request_id, @Field("status") String status);


    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/address/{id}")
    Call<Address> DeleteAddress(@Path("id") int id, @Query("device_id") String device_id, @Query("os") String os);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/store_times")
    Call<StoreTimes> EditTimesWork(@Field("store_id") int store_id
            , @Field("days[Sunday][from]") String Sundayfrom, @Field("days[Sunday][to]") String Sundayto, @Field("days[Sunday][status]") int Sundaystatus
            , @Field("days[Monday][from]") String Mondayfrom, @Field("days[Monday][to]") String Mondayto, @Field("days[Monday][status]") int Mondaystatus
            , @Field("days[Tuesday][from]") String Tuesdayfrom, @Field("days[Tuesday][to]") String Tuesdayto, @Field("days[Tuesday][status]") int Tuesdaystatus
            , @Field("days[Wednesday][from]") String Wednesdayfrom, @Field("days[Wednesday][to]") String Wednesdayto, @Field("days[Wednesday][status]") int Wednesdaystatus
            , @Field("days[Thursday][from]") String Thursdayfrom, @Field("days[Thursday][to]") String Thursdayto, @Field("days[Thursday][status]") int Thursdaystatus
            , @Field("days[Friday][from]") String Fridayfrom, @Field("days[Friday][to]") String Fridayto, @Field("days[Friday][status]") int status
            , @Field("days[Saturday][from]") String Saturdayfrom, @Field("days[Saturday][to]") String Saturdayto, @Field("days[Saturday][status]") int Saturdaystatus);

    @Headers({"api-token: API-TEST-TOKEN"})
    @POST("/api/v1/address/{id}")
    Call<Address> EditAddress(@Path("id") int id, @Body AddressEditParameter addressEditParameter);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/address")
    Call<AllAddress> GetAllAddress(@Query("device_id") String device_id, @Query("os") String os);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @POST("/api/v1/orders/{id}")
    Call<OrderToDelevey> OrderToDelevry(@Path("id") int id, @Query("device_id") String device_id, @Query("os") String os, @Field("note") String note, @Field("_method") String _method, @Field("status") int status, @Field("add") int add);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @POST("/api/v1/orders")
    Call<CheckOutCart> CreateOrderCart(@Field("address_id") int address_id, @Field("payment_gateway_id") int payment_gateway_id, @Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/orders/{id}")
    Call<ShowOrder> OrderDetails(@Path("id") int id, @Query("merchant") String merchant, @Query("delivery") String delivery, @Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @DELETE("/api/v1/orders/{id}")
    Call<ShowOrder> OrderReject(@Path("id") int id);
//
//    @Headers({"api-token: API-TEST-TOKEN","Accept: application/json"})
//    @GET("/api/v1/users")
//    Call<Driver> GetDrivers(@Query("store_id") int store_id , @Query("device_id") String device_id, @Query("os") String os ,@Query("delivery") String delivery);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/users")
    Call<Driver> GetDrivers(@Query("device_id") String device_id, @Query("os") String os, @Query("delivery") String delivery);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/request_delivery")
    Call<RequestStatus> RequestDelivery(@Query("delivery_id") int delivery_id);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/checkout")
    Call<CheckOuts> DoCheckOut(@Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/notifications")
    Call<Notifications> getNotifications();

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/orders/{id}")
    Call<QrCode> OrderQR(@Path("id") String id, @Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/orders")
    Call<Order> GetAllOrders(@Query("device_id") String device_id, @Query("os") String os, @Query("delivery") String delivery, @Query("date_from") String date_from,
                             @Query("date_to") String date_to,
                             @Query("user_id") String user_id,
                             @Query("merchant") String merchant,
                             @Query("status") String status);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN"})
    @PUT("/api/v1/orders/{id}")
    Call<Order> cancelOrders(@Path("id") int id, @Field("status") int status);

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
    Call<CartMain2> doGetListCart(@Query("device_id") String device_id, @Query("os") String os);

    @Headers({"api-token: API-TEST-TOKEN"})
    @GET("/api/v1/carts")
    Call<CartMain2> doGetListCartCoupon(@Query("device_id") String device_id, @Query("os") String os, @Query("coupon") String coupon);

    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @GET("/api/v1/carts")
    Call<CartMain2> doGetListCartRedeem(@Query("device_id") String device_id, @Query("os") String os, @Query("redeem") int coupon);

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


    @Headers({"api-token: API-TEST-TOKEN", "Accept: application/json"})
    @POST("/api/v1/carts")
    Call<CartMain> cart(@Query("device_id") String device_id, @Body CartParamenter cartParamenter);

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

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN"})
    @PUT("/api/v1/products/{id}/update_status")
    Call<UpdateProductStatus> UpdateStatusProduct(@Path("id") int id, @Field("status") int status, @Field("_method") String _method);

    @FormUrlEncoded
    @Headers({"api-token: API-TEST-TOKEN"})
    @PUT("/api/v1/coupons/{id}/update_status")
    Call<UpdateCouponStatus> UpdateStatusCoupon(@Path("id") int id, @Field("status") int status, @Field("_method") String _method);

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
