package com.example.emall_ec.main.classify.data.fuckOthers;


import com.example.emall_ec.main.classify.data.SceneDetail;
import com.example.emall_ec.main.classify.data.SceneSearch;
import com.example.emall_ec.main.classify.data.VideoHomeBean;
import com.example.emall_ec.main.classify.data.VideoSearch;
import com.example.emall_ec.main.detail.data.SceneDetailBean;
import com.example.emall_ec.main.detail.data.VideoDetailBean;

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
    Call<VideoSearch> videoSearch(@Field("geo") String targetSentence,
                                  @Field("type") String targetSentence2);

    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/videoHome")
    Call<VideoHomeBean> videoHome(@Query("type") String targetSentence);
}
