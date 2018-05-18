package com.example.emall_ec.main.detail.example.optics

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.DefaultHandler
import kotlinx.android.synthetic.main.delegate_multispectral.*
import kotlinx.android.synthetic.main.delegate_noctilucence_example.*

class MultispectralDelegate : EmallDelegate() {

    var mUploadMessage: ValueCallback<Uri>? = null


    fun create(): MultispectralDelegate?{
        return MultispectralDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_multispectral
    }

    override fun initial() {
        setSwipeBackEnable(false)
        m_example_webView.setDefaultHandler(DefaultHandler())
        m_example_webView.webChromeClient = object : WebChromeClient() {

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
        m_example_webView.loadUrl("http://10.10.90.3:8092/orderDemo.html")
        //必须和js同名函数，注册具体执行函数，类似java实现类。
        m_example_webView.registerHandler("submitFromWeb", BridgeHandler { data, function ->
            // 例如你可以对原始数据进行处理
            Toast.makeText(activity, data, Toast.LENGTH_SHORT).show()
//            calculatePriceByCouponIdBean = Gson().fromJson(data, CalculatePriceByCouponIdBean::class.java)
//            EmallLogger.d(data)
//            EmallLogger.d(calculatePriceByCouponIdBean.data.productPrice[0].coupon_type)
//            var size = calculatePriceByCouponIdBean.data.productPrice.size
//
//            for (i in 0 until size){
//                coupons += calculatePriceByCouponIdBean.data.productPrice[i].coupon_type
//                coupons += ","
//            }
//
//
//            val bundle = Bundle()
//            bundle.putString("COUPON", coupons)
//            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//            pop()
        })
    }
}