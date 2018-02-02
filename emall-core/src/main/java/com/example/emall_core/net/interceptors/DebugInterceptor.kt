package com.example.emall_core.net.interceptors

import android.support.annotation.RawRes
import com.example.emall_core.util.file.FileUtil
import okhttp3.*
import java.io.IOException


/**
 * Created by lixiang on 2018/1/31.
 */
class DebugInterceptor(private val DEBUG_URL: String, private val DEBUG_RAW_ID: Int) : BaseInterceptor() {

    private fun getResponse(chain: Interceptor.Chain, json: String): Response {
        return Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build()
    }

    private fun debugResponse(chain: Interceptor.Chain, @RawRes rawId: Int): Response {
        val json = FileUtil.getRawFile(rawId)
        return getResponse(chain, json)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().toString()
        return if (url.contains(DEBUG_URL)) {
            debugResponse(chain, DEBUG_RAW_ID)
        } else chain.proceed(chain.request())
    }
}
