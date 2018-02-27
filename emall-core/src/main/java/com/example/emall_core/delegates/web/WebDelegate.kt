package com.example.emall_core.delegates.web

import android.webkit.WebView
import com.example.emall_core.app.ConfigKeys
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.Nullable
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.web.route.RouteKeys
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference


/**
 * Created by lixiang on 2018/2/26.
 */


abstract class WebDelegate : EmallDelegate(), IWebViewInitializer {

    private var mWebView: WebView? = null
    private val WEB_VIEW_QUEUE = ReferenceQueue<WebView>()
    private var mUrl: String? = null
    private var mIsWebViewAvailable = false
    private var mTopDelegate: EmallDelegate? = null
    abstract fun setInitializer(): IWebViewInitializer?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        mUrl = args.getString(RouteKeys.URL.name)
        initWebView()
    }

    @SuppressLint("JavascriptInterface", "AddJavascriptInterface")
    private fun initWebView() {
        if (mWebView != null) {
            mWebView!!.removeAllViews()
            mWebView!!.destroy()
        } else {
            val initializer = setInitializer()
            if (initializer != null) {
                val webViewWeakReference = WeakReference(WebView(context), WEB_VIEW_QUEUE)
                mWebView = webViewWeakReference.get()
                mWebView = initializer.initWebView(mWebView!!)
                mWebView!!.webViewClient = initializer.initWebViewClient()
                mWebView!!.webChromeClient = initializer.initWebChromeClient()
                val name = Emall().getConfiguration<String>(ConfigKeys.JAVASCRIPT_INTERFACE.name)
                mWebView!!.addJavascriptInterface(EmallWebInterface.create(this), name)
                mIsWebViewAvailable = true
            } else {
                throw NullPointerException("Initializer is null!")
            }
        }
    }

    val webView: WebView?
        get() {
            if (mWebView == null) {
                throw NullPointerException("WebView IS NULL!")
            }
            return if (mIsWebViewAvailable) mWebView else null
        }

    val url: String?
        get() {
            if (mUrl == null) {
                throw NullPointerException("WebView IS NULL!")
            }
            return mUrl
        }

    fun getTopDelegate(): EmallDelegate?{
        if (mTopDelegate == null) {
            mTopDelegate = this
        }
        return mTopDelegate
    }

    fun setTopDelegate(delegate: EmallDelegate) {
        mTopDelegate = delegate
    }

    override fun onPause() {
        super.onPause()
        if (mWebView != null) {
            mWebView!!.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mWebView != null) {
            mWebView!!.onResume()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsWebViewAvailable = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mWebView != null) {
            mWebView!!.removeAllViews()
            mWebView!!.destroy()
            mWebView = null
        }
    }
}
