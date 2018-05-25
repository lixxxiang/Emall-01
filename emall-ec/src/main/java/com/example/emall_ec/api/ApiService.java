package com.example.emall_ec.api;


import com.example.emall_ec.main.launcher.EntryPageBean;
import com.example.emall_ec.main.classify.data.GetRecommendCitiesBean;
import com.example.emall_ec.main.classify.data.SceneSearch;
import com.example.emall_ec.main.classify.data.VideoHomeBean;
import com.example.emall_ec.main.coupon.data.GetCouponTypeByProductIdBean;
import com.example.emall_ec.main.coupon.data.GetCouponTypeByUserAndDemandBean;
import com.example.emall_ec.main.demand.data.AppPayBean;
import com.example.emall_ec.main.demand.data.CommoditySubmitDemandBean;
import com.example.emall_ec.main.demand.data.QueryOrderBean;
import com.example.emall_ec.main.demand.data.QueryOrderFailureBean;
import com.example.emall_ec.main.detail.data.GetCollectionMarkBean;
import com.example.emall_ec.main.detail.data.SceneDetailBean;
import com.example.emall_ec.main.detail.data.VideoDetailBean;
import com.example.emall_ec.main.index.dailypic.data.CommonBean;
import com.example.emall_ec.main.index.dailypic.data.HomePageBean;
import com.example.emall_ec.main.me.collect.data.MyAllCollectionBean;
import com.example.emall_ec.main.order.state.data.OrderDetail;
import com.example.emall_ec.main.scanner.data.ScanCodeLoginBean;
import com.example.emall_ec.main.search.data.PoiBean;
import com.example.emall_ec.main.search.data.VideoSearchBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lixiang on 2017/10/20.
 */

public interface ApiService {


    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/mobile/EntryPage")
    Call<EntryPageBean> entryPage(@Query("client") String client);


    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/sceneDetail")
    Call<SceneDetailBean> sceneDetail(@Query("productId") String targetSentence,
                                      @Query("type") String targetSentence2, @Query("client") String client);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/videoDetail")
    Call<VideoDetailBean> videoDetail(@Query("productId") String targetSentence, @Query("client") String client);

    @POST("/global/mobile/sceneSearch?client=android")
    @FormUrlEncoded
    Call<SceneSearch> sceneSearch(@Field("scopeGeo") String targetSentence,
                                  @Field("productType") String targetSentence2,
                                  @Field("resolution") String targetSentenc3,
                                  @Field("satelliteId") String targetSentence4,
                                  @Field("startTime") String targetSentence5,
                                  @Field("endTime") String targetSentence6,
                                  @Field("cloud") String targetSentenc7,
                                  @Field("type") String targetSentence8,
                                  @Field("pageSize") String targetSentence9,
                                  @Field("pageNum") String targetSentence10, @Field("client") String targetSentence11);

    @POST("/global/videoSearch?client=android")
    @FormUrlEncoded
    Call<VideoSearchBean> videoSearch(@Field("geo") String targetSentence,
                                      @Field("type") String targetSentence2,
                                      @Field("pageSize") String targetSentence3,
                                      @Field("pageNum") String targetSentence4,
                                      @Field("orderBy") String targetSentence5);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/videoHome")
    Call<VideoHomeBean> videoHome(@Query("type") String targetSentence, @Query("client") String targetSentence2);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/order/findOrderListByUserId")
    Call<OrderDetail> findOrderListByUserId(@Query("userId") String targetSentence,
                                            @Query("state") String targetSentence2,
                                            @Query("type") String targetSentence3,@Query("client") String argetSentence3);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("/global/order/deleteOrder")
    Call<CommonBean> deleteOrder(@Query("orderId") String targetSentence);


    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("/global/mobile/getRecommendCities")
    Call<GetRecommendCitiesBean> getRecommandCities(@Query("client") String targetSentence);


    @POST("/global/wxpay/appPay")
    @FormUrlEncoded
    Call<AppPayBean> appPay(@Field("orderId") String targetSentence,
                            @Field("type") String targetSentence2,
                            @Field("payMethod") String targetSentence3,@Field("client") String targetSentence5);

    @POST("/global/mobile/wxpay/queryOrder")
    @FormUrlEncoded
    Call<QueryOrderFailureBean> queryOrderFailure(@Field("parentOrderId") String targetSentence,
                                                  @Field("type") String targetSentence2,@Field("client") String targetSentence4);

    @POST("/coupon/getCouponTypeByProductId")
    @FormUrlEncoded
    Call<GetCouponTypeByProductIdBean> getCouponTypeByProductId(@Field("productId") String targetSentence, @Field("client") String targetSentence2);

    @POST("/coupon/getCouponTypeByUserAndDemand")
    @FormUrlEncoded
    Call<GetCouponTypeByUserAndDemandBean> getCouponTypeByUserAndDemand(@Field("demandId") String targetSentence, @Field("userId") String targetSentence2, @Field("client") String targetSentence3);

    @POST("/global/mobile/wxpay/queryOrder")
    @FormUrlEncoded
    Call<QueryOrderBean> queryOrder(@Field("parentOrderId") String targetSentence,
                                    @Field("type") String targetSentence2,@Field("client") String c);

    @POST("/global/mobile/myAllCollection")
    @FormUrlEncoded
    Call<MyAllCollectionBean> myAllCollection(@Field("userId") String targetSentence,
                                              @Field("pageNum") String targetSentence2,
                                              @Field("pageSize") String targetSentence3);

    @POST("/mobile/homePage")
    @FormUrlEncoded
    Call<HomePageBean> homePage(
            @Field("pageNum") String targetSentence2,
            @Field("pageSize") String targetSentence3);


    /**
     * HOME
     */
    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/mobile/homePageUnits")
    Call<String> homePageUnits(@Query("client") String client);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/mobile/homePageSlide")
    Call<String> homePageSlide(@Query("client") String client);

    @POST("mobile/homePage")
    @FormUrlEncoded
    Call<String> homePage(@Field("pageSize") String targetSentence9,
                          @Field("pageNum") String targetSentence10, @Field("client") String client);

    /**
     * SCAN
     */
    @POST("scanCodeLogin.do?client=android")
    @FormUrlEncoded
    Call<ScanCodeLoginBean> scanCodeLogin(
            @Field("uuid") String targetSentence2,
            @Field("userTelephone") String targetSentence3);

    /**
     * SEARCH
     */
    @POST("GetPoiByGaode.do")
    @FormUrlEncoded
    Call<PoiBean> getPoiByGaodeWithCityName(
            @Field("poiName") String targetSentence2,
            @Field("cityName") String targetSentence3,
            @Field("client") String targetSentence4);

    @POST("GetPoiByGaode.do")
    @FormUrlEncoded
    Call<PoiBean> getPoiByGaode(
            @Field("poiName") String targetSentence2,
            @Field("client") String targetSentence4);

    /**
     * COUPON
     */
    @POST("GetPoiByGaode.do")
    @FormUrlEncoded
    Call<PoiBean> useCoupon(
            @Field("poiName") String targetSentence2,
            @Field("client") String targetSentence4);


    @POST("global/mobile/cancelCollection")
    @FormUrlEncoded
    Call<CommonBean> cancelCollection(
            @Field("productId") String targetSentence2,
            @Field("userId") String targetSentence4, @Field("client") String targetSentence5);

    @POST("global/mobile/addCollection")
    @FormUrlEncoded
    Call<CommonBean> addCollection(
            @Field("productId") String targetSentence2,
            @Field("userId") String targetSentence4, @Field("productType") String targetSentence6, @Field("client") String targetSentence5);

    @POST("global/commoditySubmitDemand")
    @FormUrlEncoded
    Call<CommoditySubmitDemandBean> commoditySubmitDemand(
            @Field("productId") String targetSentence2,
            @Field("geo") String targetSentence4, @Field("status") String targetSentence6, @Field("type") String targetSentence7, @Field("client") String targetSentence5);

    @POST("global/mobile/getCollectionMark")
    @FormUrlEncoded
    Call<GetCollectionMarkBean> getCollectionMark(
            @Field("productId") String targetSentence2,
            @Field("userId") String targetSentence4, @Field("client") String targetSentence5);
}
//http://59.110.164.214:8024/