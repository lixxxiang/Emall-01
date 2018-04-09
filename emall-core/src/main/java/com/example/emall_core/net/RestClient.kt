package com.example.emall_core.net

import android.content.Context
import android.widget.ProgressBar
import com.example.emall_core.net.HttpMethod.*
import com.example.emall_core.net.callback.*
import com.example.emall_core.ui.progressbar.ProgressbarCreator
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.util.*


/**
 * Created by lixiang on 2018/1/29.
 */
class RestClient() {
    var URL: String? = ""
    val PARAMS: WeakHashMap<String, Any>? = RestCreator.params
    var REQUEST: IRequest? = null
    var DOWNLOAD_DIR: String? = null
    var EXTENSION: String? = null
    var NAME: String? = null
    var SUCCESS: ISuccess? = null
    var FAILURE: IFailure? = null
    var ERROR: IError? = null
    var BODY: RequestBody? = null
    var CONTEXT: Context? = null
    var PROGRESSBAR: ProgressBar? = null
    var FILE: File? = null


    constructor(url: String?,
                params: Map<String, Any>?,
                downloadDir: String?,
                extension: String?,
                name : String?,
                request: IRequest?,
                success: ISuccess?,
                failure: IFailure?,
                error: IError?,
                body: RequestBody?,
                context: Context?,
                progressbar: ProgressBar?,
                file:File?
    ) : this() {
        this.URL = url!!
        if (params != null) {
            PARAMS!!.putAll(params)
        }
        this.REQUEST = request
        this.SUCCESS = success
        this.FAILURE = failure
        this.ERROR = error
        this.BODY = body
        this.CONTEXT = context
        this.PROGRESSBAR = progressbar
        this.FILE = file
        this.DOWNLOAD_DIR = downloadDir
        this.EXTENSION = extension
        this.NAME = name
    }

    fun builder(): RestClientBuilder {
        return RestClientBuilder()
    }

    fun request(method: HttpMethod) {
        val service = RestCreator.restService
        var call: Call<String>? = null

        if (REQUEST != null) {
            REQUEST!!.onRequestStart()
        }

//        EmallProgressbar().showProgressbar(CONTEXT!!)

        when (method) {
            GET -> call = service.get(URL!!, PARAMS!!)
            POST -> call = service.post(URL!!, PARAMS!!)
            POST_RAW -> call = service.postRaw(URL!!, BODY!!)
            PUT -> call = service.put(URL!!, PARAMS!!)
            PUT_RAW -> call = service.putRaw(URL!!, BODY!!)
            DELETE -> call = service.delete(URL!!, PARAMS!!)
            UPLOAD -> {
                val requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE!!)
                val body = MultipartBody.Part.createFormData("file", FILE!!.name, requestBody)
                call = service.upload(URL!!, body)
            }


            else -> {

            }
        }

        if (call != null) {
            call.enqueue(getRequestCallback())
        }
    }

    private fun getRequestCallback(): Callback<String> {
        return RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR
        )
    }

    fun get() {
        request(HttpMethod.GET)
    }

    fun post() {
//        request(HttpMethod.POST)
        if (BODY == null) {
            request(HttpMethod.POST)
        } else {
            if (!PARAMS!!.isEmpty()) {
                throw RuntimeException("params must be null!")
            }
            request(HttpMethod.POST_RAW)
        }
    }

    fun put() {
        request(HttpMethod.PUT)
    }

    fun delete() {
        request(HttpMethod.DELETE)
    }

    fun upload(){
        request(UPLOAD)
    }

    fun download(){

    }

}