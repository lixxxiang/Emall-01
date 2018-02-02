package com.example.emall_core.net

import com.example.emall_core.app.ConfigKeys
import com.example.emall_core.app.Emall
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by lixiang on 2018/1/29.
 */

object RestCreator {

    val params: WeakHashMap<String, Any>
        get() = ParamsHolder.PARAMS

    val restService: RestService
        get() = RestServiceHolder.REST_SERVICE

    private object ParamsHolder {
        val PARAMS = WeakHashMap<String, Any>()
    }

    private object OKHttpHolder {
        val TIME_OUT = 60

        val INTERCEPTORS: ArrayList<Interceptor>? = Emall().getConfiguration(ConfigKeys.INTERCEPTOR)
        private val BUILDER: OkHttpClient.Builder = OkHttpClient.Builder()

        fun addInterceptor(): OkHttpClient.Builder {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (interceptor in INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor)
                }
            }
            return BUILDER
        }

        val OK_HTTP_CLIENT: OkHttpClient = addInterceptor()
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .build()


//        val OK_HTTP_CLIENT = OkHttpClient.Builder()
//                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
//                .build()
    }

    private object RetrofitHolder {

        val BASE_URL = Emall().getConfigurations()[ConfigKeys.API_HOST.toString()] as String
        val RETROFIT_CLIENT = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }

    private object RestServiceHolder {
        val REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService::class.java)
    }
}
