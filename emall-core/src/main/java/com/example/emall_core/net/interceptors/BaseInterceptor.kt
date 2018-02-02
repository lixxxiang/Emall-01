package com.example.emall_core.net.interceptors

import okhttp3.FormBody
import okhttp3.Interceptor



/**
 * Created by lixiang on 2018/1/31.
 */
abstract class BaseInterceptor : Interceptor {

    protected fun getUrlParameters(chain: Interceptor.Chain): LinkedHashMap<String, String> {
        val url = chain.request().url()
        val size = url.querySize()
        val params = LinkedHashMap<String, String>()
        for (i in 0 until size) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i))
        }
        return params
    }

    protected fun getUrlParameters(chain: Interceptor.Chain, key: String): String {
        val request = chain.request()
        return request.url().queryParameter(key).toString()
    }

    protected fun getBodyParameters(chain: Interceptor.Chain): LinkedHashMap<String, String> {
        val formBody = chain.request().body() as FormBody?
        val params = LinkedHashMap<String, String>()
        var size = 0
        if (formBody != null) {
            size = formBody.size()
        }
        for (i in 0 until size) {
            params.put(formBody!!.name(i), formBody.value(i))
        }
        return params
    }

    protected fun getBodyParameters(chain: Interceptor.Chain, key: String): String {
        return getBodyParameters(chain)[key].toString()
    }
}
