package com.example.emall_core.delegates.web.client

import android.webkit.WebViewClient
import android.webkit.WebView
import android.graphics.Bitmap
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.web.IPageLoadListener
import com.example.emall_core.delegates.web.WebDelegate
import com.example.emall_core.delegates.web.route.Router
import com.example.emall_core.ui.progressbar.EmallProgressbar
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 2018/2/27.
 */
class WebViewClientImpl(private val DELEGATE: WebDelegate) : WebViewClient() {
    private var mIPageLoadListener: IPageLoadListener? = null

    fun setPageLoadListener(listener: IPageLoadListener?) {
        this.mIPageLoadListener = listener
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        EmallLogger.d("shouldOverrideUrlLoading", url)
        return Router.instance.handleWebUrl(DELEGATE, url)
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
        super.onPageStarted(view, url, favicon)
        mIPageLoadListener = IPageLoadListener
        if (mIPageLoadListener != null) {
            println("ininininini")
            mIPageLoadListener!!.onLoadStart()
        }
        EmallProgressbar().showProgressbar(view.context)
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        if (mIPageLoadListener != null) {
            mIPageLoadListener!!.onLoadEnd()
        }
        HANDLER!!.postDelayed({ EmallProgressbar().hideProgressbar(view.context) }, 1000)
    }

    companion object {
        private val HANDLER = Emall().getHandler()
    }
}
