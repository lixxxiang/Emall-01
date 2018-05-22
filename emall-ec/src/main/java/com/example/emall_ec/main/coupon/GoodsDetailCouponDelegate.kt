package com.example.emall_ec.main.coupon

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.R.string.goods
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.coupon.data.CalculatePriceByCouponIdBean
import com.example.emall_ec.main.coupon.type.EnableCouponDelegate
import com.example.emall_ec.main.coupon.type.InvalidCouponDelegate
import com.example.emall_ec.main.coupon.type.UsedCouponDelegate
import com.example.emall_ec.main.index.dailypic.pic.ShowImageNewActivity.PlaceholderFragment.Companion.url
import com.example.emall_ec.main.order.Find_tab_Adapter
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.CallBackFunction
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_coupon.*
import kotlinx.android.synthetic.main.delegate_fill_order_coupon.*
import kotlinx.android.synthetic.main.delegate_goods_detail_coupon.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class GoodsDetailCouponDelegate : EmallDelegate() {

    var mUploadMessage: ValueCallback<Uri>? = null

    fun create(): GoodsDetailCouponDelegate? {
        return GoodsDetailCouponDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods_detail_coupon
    }

    override fun initial() {
        setSwipeBackEnable(false)

        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        goods_detail_coupon_toolbar.title = "可使用的优惠券"
        (activity as AppCompatActivity).setSupportActionBar(goods_detail_coupon_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        goods_detail_coupon_toolbar.setNavigationOnClickListener {
            pop()
        }
        initWebView()


    }


    private fun initWebView() {
        goods_detail_coupon_webView.setDefaultHandler(DefaultHandler())
        goods_detail_coupon_webView.webChromeClient = object : WebChromeClient() {

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
        var url: String
//        if (arguments.getString("couponId") == "-1" || arguments.getString("couponId") == null) {
        url = String.format("http://10.10.90.3:8092/productTicket.html?productId=%s&userId=%s",
                arguments.getString("productId"), DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
        EmallLogger.d(url)
        goods_detail_coupon_webView.loadUrl(url)
        //必须和js同名函数，注册具体执行函数，类似java实现类。
        goods_detail_coupon_webView.registerHandler("submitMessage", BridgeHandler { data, function ->
            EmallLogger.d(data)
            Toast.makeText(activity, data, Toast.LENGTH_SHORT).show()
            pop()
        })

        goods_detail_coupon_webView.registerHandler("submitCheck", BridgeHandler { data, function ->
            // 例如你可以对原始数据进行处理
        })
        goods_detail_coupon_webView.callHandler("functionInJs", "", CallBackFunction { data -> Log.e("AAAAA", "来自web的回传数据：$data") })
    }


    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}