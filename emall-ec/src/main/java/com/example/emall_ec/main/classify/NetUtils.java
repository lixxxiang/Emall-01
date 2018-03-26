package com.example.emall_ec.main.classify;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yyfwptz on 2017/8/3.
 */

public class NetUtils {
    private static Retrofit retrofit = null;
    private NetUtils(){}
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://59.110.164.214:8024/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
