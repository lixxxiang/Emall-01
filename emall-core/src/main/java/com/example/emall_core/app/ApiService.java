package com.example.emall_core.app;


import com.example.emall_core.ui.HomePageUnitsBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by lixiang on 2017/10/20.
 */

public interface ApiService {

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/homePageUnits")
    Call<String> homePageUnits();

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/mobile/homePageSlide")
    Call<String> homePageSlide();

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/mobile/topic")
    Call<String> topic();

    @POST("mobile/homePage")
    @FormUrlEncoded
    Call<String> homePage(@Field("pageSize") String targetSentence9,
                               @Field("pageNum") String targetSentence10);
}
