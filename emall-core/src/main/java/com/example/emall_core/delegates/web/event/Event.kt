package com.example.emall_core.delegates.web.event

import android.content.Context
import com.example.emall_core.delegates.web.WebDelegate
import android.webkit.WebView
import com.example.emall_core.delegates.EmallDelegate


/**
 * Created by lixiang on 2018/2/27.
 */
abstract class Event : IEvent {
    var context: Context? = null
    var action: String? = null
    private var mDelegate: WebDelegate? = null
    var url: String? = null
    private val mWebView: WebView? = null

    val webView: WebView?
        get() = mDelegate!!.webView

    val delegate: EmallDelegate?
        get() = mDelegate

    fun setDelegate(mDelegate: WebDelegate) {
        this.mDelegate = mDelegate
    }
}