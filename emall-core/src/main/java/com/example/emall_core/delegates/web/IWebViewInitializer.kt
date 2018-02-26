package com.example.emall_core.delegates.web

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.webkit.WebView



/**
 * Created by lixiang on 2018/2/26.
 */
interface IWebViewInitializer {

    fun initWebView(webView: WebView): WebView

    fun initWebViewClient(): WebViewClient

    fun initWebChromeClient(): WebChromeClient
}