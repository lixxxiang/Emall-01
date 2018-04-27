package com.example.emall_core.app;


import com.example.emall_core.ui.HomePageUnitsBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by lixiang on 2017/10/20.
 */

public interface ApiService {


    @Headers({"Content-Type:text/html;charset=utf-8", "Accept:application/json;"})
    @GET("global/homePageUnits")
    Call<HomePageUnitsBean> homePageUnits();
}
