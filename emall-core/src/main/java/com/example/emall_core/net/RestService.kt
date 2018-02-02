package com.example.emall_core.net

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*





/**
 * Created by lixiang on 2018/1/29.
 */

interface RestService {
    @GET
    operator fun get(@Url url: String, @QueryMap params: WeakHashMap<String, Any>): Call<String>

    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap params: WeakHashMap<String, Any>): Call<String>

    @POST
    fun postRaw(@Url url: String, @Body body: RequestBody): Call<String>

    @FormUrlEncoded
    @PUT
    fun put(@Url url: String, @FieldMap params: WeakHashMap<String, Any>): Call<String>

    @PUT
    fun putRaw(@Url url: String, @Body body: RequestBody): Call<String>

    @DELETE
    fun delete(@Url url: String, @QueryMap params: WeakHashMap<String, Any>): Call<String>

    @Streaming
    @GET
    fun download(@Url url: String, @QueryMap params: WeakHashMap<String, Any>): Call<ResponseBody>

    @Multipart
    @POST
    fun upload(@Url url: String, @Part file: MultipartBody.Part): Call<String>
}
