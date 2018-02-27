package com.example.emall_core.delegates.web

import android.webkit.WebSettings
import android.view.View.OnLongClickListener
import android.webkit.WebView
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View


/**
 * Created by lixiang on 2018/2/27.
 */
class WebViewInitializer {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetJavaScriptEnabled")
    fun createWebView(webView: WebView): WebView {

        WebView.setWebContentsDebuggingEnabled(true)
        //不能横向滚动
        webView.isHorizontalScrollBarEnabled = false
        //不能纵向滚动
        webView.isVerticalScrollBarEnabled = false
        //允许截图
        webView.isDrawingCacheEnabled = true
        //屏蔽长按事件
        webView.setOnLongClickListener { true }
        //初始化WebSettings
        val settings = webView.settings
        settings.javaScriptEnabled = true
        val ua = settings.userAgentString
        settings.userAgentString = ua + "Emall"
        //隐藏缩放控件
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        //禁止缩放
        settings.setSupportZoom(false)
        //文件权限
        settings.allowFileAccess = true
        settings.allowFileAccessFromFileURLs = true
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowContentAccess = true
        //缓存相关
        settings.setAppCacheEnabled(true)
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT

        return webView
    }
}