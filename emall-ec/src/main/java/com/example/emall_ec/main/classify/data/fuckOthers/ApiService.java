package com.example.emall_ec.main.classify.data.fuckOthers;


import com.example.emall_core.ui.HomePageUnitsBean;
import com.example.emall_ec.main.classify.data.SceneSearch;
import com.example.emall_ec.main.classify.data.VideoHomeBean;
import com.example.emall_ec.main.classify.data.VideoSearch;
import com.example.emall_ec.main.demand.data.AppPayBean;
import com.example.emall_ec.main.demand.data.QueryOrderBean;
import com.example.emall_ec.main.demand.data.QueryOrderFailureBean;
import com.example.emall_ec.main.detail.data.SceneDetailBean;
import com.example.emall_ec.main.detail.data.VideoDetailBean;
import com.example.emall_ec.main.index.dailypic.data.CommonBean;
import com.example.emall_ec.main.order.state.data.OrderDetail;
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
    @GET("global/sceneDetail")
    Call<HomePageUnitsBean> homePageUnits();


    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/sceneDetail")
    Call<SceneDetailBean> sceneDetail(@Query("productId") String targetSentence,
                                      @Query("type") String targetSentence2);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/videoDetail")
    Call<VideoDetailBean> videoDetail(@Query("productId") String targetSentence);

    @POST("/global/mobile/sceneSearch")
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
                                  @Field("pageNum") String targetSentence10);

    @POST("/global/videoSearch")
    @FormUrlEncoded
    Call<VideoSearchBean> videoSearch(@Field("geo") String targetSentence,
                                      @Field("type") String targetSentence2,
                                      @Field("pageSize") String targetSentence3, @Field("pageNum") String targetSentence4);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/videoHome")
    Call<VideoHomeBean> videoHome(@Query("type") String targetSentence);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/order/findOrderListByUserId")
    Call<OrderDetail> findOrderListByUserId(@Query("userId") String targetSentence,
                                            @Query("state") String targetSentence2,
                                            @Query("type") String targetSentence3);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("/global/order/deleteOrder")
    Call<CommonBean> deleteOrder(@Query("orderId") String targetSentence);

    @POST("/global/wxpay/appPay")
    @FormUrlEncoded
    Call<AppPayBean> appPay(@Field("orderId") String targetSentence,
                            @Field("type") String targetSentence2,
                            @Field("payMethod") String targetSentence3);

    @POST("/global/mobile/wxpay/queryOrder")
    @FormUrlEncoded
    Call<QueryOrderFailureBean> queryOrderFailure(@Field("parentOrderId") String targetSentence,
                                           @Field("type") String targetSentence2);

    @POST("/global/mobile/wxpay/queryOrder")
    @FormUrlEncoded
    Call<QueryOrderBean> queryOrder(@Field("parentOrderId") String targetSentence,
                                    @Field("type") String targetSentence2);
}
