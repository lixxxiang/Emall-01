package com.example.emall_core.delegates.web.route

import android.content.Context
import android.support.v4.content.ContextCompat
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import com.example.emall_core.delegates.web.WebDelegate
import android.webkit.URLUtil.isAssetUrl
import android.webkit.URLUtil.isNetworkUrl
import android.webkit.WebView
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.web.WebDelegateImpl



/**
 * Created by lixiang on 2018/2/27.
 */
class Router private constructor() {

    private object Holder {
        val INSTANCE = Router()
    }

    fun handleWebUrl(delegate: WebDelegate, url: String): Boolean {

        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.context, url)
            return true
        }

        val topDelegate = delegate.getTopDelegate()
//        val topDelegate = delegate.getParentDelegate<EmallDelegate>()


        val webDelegate = WebDelegateImpl.create(url)
//        if (topDelegate == null){
//            delegate.start(webDelegate)
//        }else{
//            topDelegate.start(webDelegate)
//        }
        topDelegate!!.start(webDelegate)

        return true
    }

    private fun loadWebPage(webView: WebView?, url: String) {
        if (webView != null) {
            webView.loadUrl(url)
        } else {
            throw NullPointerException("WebView is null!")
        }
    }

    private fun loadLocalPage(webView: WebView?, url: String) {
        loadWebPage(webView, "file:///android_asset/" + url)
    }

    private fun loadPage(webView: WebView?, url: String) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url)
        } else {
            loadLocalPage(webView, url)
        }
    }

    fun loadPage(delegate: WebDelegate, url: String) {
        loadPage(delegate.webView, url)
    }

    private fun callPhone(context: Context, uri: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse(uri)
        intent.data = data
        ContextCompat.startActivity(context, intent, null)
    }

    companion object {

        val instance: Router
            get() = Holder.INSTANCE
    }
}
