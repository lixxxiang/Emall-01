package com.example.emall_ec.main.coupon.type

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.DefaultHandler
import kotlinx.android.synthetic.main.delegate_enable_coupon.*
import kotlinx.android.synthetic.main.delegate_used_coupon.*

class EnableCouponDelegate : EmallDelegate() {
    var mUploadMessage: ValueCallback<Uri>? = null

    fun create(): EnableCouponDelegate? {
        return EnableCouponDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_enable_coupon
    }

    override fun initial() {
        setSwipeBackEnable(false)
        me_enable_coupon_webView.setDefaultHandler(DefaultHandler())
        me_enable_coupon_webView.webChromeClient = object : WebChromeClient() {

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
        //加载服务器网页
        var url = String.format("http://10.10.90.3:8092/userTicket.html?userId=%s&status=%s",  DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, 0)
        EmallLogger.d(url)
        me_enable_coupon_webView.loadUrl(url)
        //必须和js同名函数，注册具体执行函数，类似java实现类。
        me_enable_coupon_webView.registerHandler("submitFromWeb", BridgeHandler { data, function ->
            // 例如你可以对原始数据进行处理
            Toast.makeText(activity, data, Toast.LENGTH_SHORT).show()
//            calculatePriceByCouponIdBean = Gson().fromJson(data, CalculatePriceByCouponIdBean::class.java)
//            EmallLogger.d(data)
//            EmallLogger.d(calculatePriceByCouponIdBean.data.productPrice[0].coupon_type)
//            val bundle = Bundle()
//            bundle.putString("COUPON", "C")
//            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//            pop()
        })
    }
}