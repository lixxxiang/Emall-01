package com.example.emall_core.delegates.web.chromeclient

import android.webkit.JsResult
import android.webkit.WebView
import android.webkit.WebChromeClient



/**
 * Created by lixiang on 2018/2/27.
 */
class WebChromeClientImpl : WebChromeClient() {

    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        return super.onJsAlert(view, url, message, result)
    }
}