package com.example.emall_ec.main.detail.example.optics

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.DefaultHandler
import kotlinx.android.synthetic.main.delegate_multispectral.*
import kotlinx.android.synthetic.main.delegate_panchromatic.*

class PanchromaticDelegate : EmallDelegate() {
    var mUploadMessage: ValueCallback<Uri>? = null

    fun create(): PanchromaticDelegate?{
        return PanchromaticDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_panchromatic
    }

    override fun initial() {

        EmallLogger.d(".......")
        setSwipeBackEnable(false)
        p_example_webView.setDefaultHandler(DefaultHandler())
        p_example_webView.webChromeClient = object : WebChromeClient() {

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String, capture: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                mUploadMessage = uploadMsg
            }
        }
        p_example_webView.loadUrl("http://59.110.164.214:8082/allColor.html")
        p_example_webView.registerHandler("submitFromWeb", BridgeHandler { data, function ->
            Toast.makeText(activity, data, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        EmallLogger.d(".......")
        setSwipeBackEnable(false)
        p_example_webView.setDefaultHandler(DefaultHandler())
        p_example_webView.webChromeClient = object : WebChromeClient() {

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String, capture: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                mUploadMessage = uploadMsg
            }
        }
        p_example_webView.loadUrl("http://59.110.164.214:8082/allColor.html")
        p_example_webView.registerHandler("submitFromWeb", BridgeHandler { data, function ->
            Toast.makeText(activity, data, Toast.LENGTH_SHORT).show()
        })
    }
}