package com.example.emall_core.net

import android.content.Context
import android.widget.ProgressBar
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.IRequest
import com.example.emall_core.net.callback.ISuccess
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.util.*

/**
 * Created by lixiang on 2018/1/29.
 */

class RestClientBuilder {
    var mUrl: String? = ""
    var PARAMS: WeakHashMap<String, Any>? = RestCreator.params
    var mIRequest: IRequest? = null
    var mISuccess: ISuccess? = null
    var mIFailure: IFailure? = null
    var mIError: IError? = null
    var mBody: RequestBody? = null
    var mContext: Context? = null
    var mProgressbar: ProgressBar? = null
    var mFile: File? = null
    var mDownloadDir: String? = null
    var mExtension: String? = null
    var mName: String? = null

    fun url(url: String): RestClientBuilder {
        this.mUrl = url
        return this
    }

    fun params(params: WeakHashMap<String, Any>): RestClientBuilder {
        PARAMS = params
        return this
    }

    fun params(key: String, value: Any): RestClientBuilder {
        PARAMS = WeakHashMap<String, Any>()
        PARAMS!![key] = value
        return this
    }

    fun file(file: String): RestClientBuilder {
        this.mFile = File(file)
        return this
    }

    fun name(name: String): RestClientBuilder {
        this.mName = name
        return this
    }

    fun dir(dir: String): RestClientBuilder {
        this.mDownloadDir = dir
        return this
    }

    fun extension(extension: String): RestClientBuilder {
        this.mExtension = extension
        return this
    }

    fun raw(raw: String): RestClientBuilder {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw)
        return this
    }

    fun onRequest(iRequest: IRequest): RestClientBuilder {
        this.mIRequest = iRequest
        return this
    }

    fun success(iSuccess: ISuccess): RestClientBuilder {
        this.mISuccess = iSuccess
        return this
    }

    fun failure(iFailure: IFailure): RestClientBuilder {
        this.mIFailure = iFailure
        return this
    }

    fun error(iError: IError): RestClientBuilder {
        this.mIError = iError
        return this
    }

    fun progressbar(context: Context): RestClientBuilder {
//        this.mProgressbar = ProgressBar()
        this.mContext = context
        return this
    }

//    fun loader(context: Context, style: LoaderStyle): RestClientBuilder {
//        this.mLoaderStyle = style
//        this.mContext = context
//        return this
//    }
//
//    fun loader(context: Context): RestClientBuilder {
//        this.mContext = context
//        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator
//        return this
//    }

    fun build(): RestClient {
        return RestClient(mUrl, PARAMS, mDownloadDir, mExtension, mName,
                mIRequest, mISuccess, mIFailure,
                mIError, mBody,
                mContext, mProgressbar, mFile)
    }

}