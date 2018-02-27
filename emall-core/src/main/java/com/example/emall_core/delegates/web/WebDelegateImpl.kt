package com.example.emall_core.delegates.web

import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.webkit.WebView
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.example.emall_core.delegates.web.chromeclient.WebChromeClientImpl
import com.example.emall_core.delegates.web.client.WebViewClientImpl
import com.example.emall_core.delegates.web.route.RouteKeys
import com.example.emall_core.delegates.web.route.Router
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 2018/2/26.
 */
class WebDelegateImpl : WebDelegate() {

    override fun initial() {
        EmallLogger.d(url!!)
        if (url != null) {
            //用原生的方式模拟Web跳转并进行页面加载
            Router.instance.loadPage(this, url!!)
        }
    }

//    private var mIPageLoadListener: IPageLoadListener? = null

    override fun setLayout(): Any? {
        return webView
    }

//    fun setPageLoadListener(listener: IPageLoadListener) {
//        this.mIPageLoadListener = listener
//    }

//    fun onBindView(@Nullable savedInstanceState: Bundle, rootView: View) {
//        if (getUrl() != null) {
//            //用原生的方式模拟Web跳转并进行页面加载
//            Router.getInstance().loadPage(this, getUrl())
//        }
//    }

    override fun setInitializer(): IWebViewInitializer? {
        return this
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun initWebView(webView: WebView): WebView {
        return WebViewInitializer().createWebView(webView)
    }

    override fun initWebViewClient(): WebViewClient {
        val client = WebViewClientImpl(this)
//        client.setPageLoadListener(mIPageLoadListener)
        return client
    }

    override fun initWebChromeClient(): WebChromeClient {
        return WebChromeClientImpl()
    }

    companion object {

        fun create(url: String): WebDelegateImpl {
            val args = Bundle()
            args.putString(RouteKeys.URL.toString(), url)
            val delegate = WebDelegateImpl()
            delegate.arguments = args
            return delegate
        }
    }
}
