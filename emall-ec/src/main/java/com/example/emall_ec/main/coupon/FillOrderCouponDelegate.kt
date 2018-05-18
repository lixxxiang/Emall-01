package com.example.emall_ec.main.coupon

import android.location.Location
import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.coupon.type.EnableCouponDelegate
import com.example.emall_ec.main.coupon.type.InvalidCouponDelegate
import com.example.emall_ec.main.coupon.type.UsedCouponDelegate
import com.example.emall_ec.main.order.Find_tab_Adapter
import kotlinx.android.synthetic.main.delegate_coupon.*
import kotlinx.android.synthetic.main.delegate_fill_order_coupon.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import com.example.emall_core.app.Emall
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.coupon.data.CalculatePriceByCouponIdBean
import com.example.emall_ec.main.index.dailypic.data.CommonBean
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.CallBackFunction
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.google.gson.Gson
import me.yokeyword.fragmentation.ISupportFragment


class FillOrderCouponDelegate : EmallDelegate() {

    var mUploadMessage: ValueCallback<Uri>? = null
    private var demandId = String()
    private var type = String()
    private var salePrice = String()
    private var calculatePriceByCouponIdBean = CalculatePriceByCouponIdBean()
    private var coupons = String()
    fun create(): FillOrderCouponDelegate? {
        return FillOrderCouponDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_fill_order_coupon
    }

    override fun initial() {
        setSwipeBackEnable(false)
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        demandId = arguments.getString("demandId")
        type = arguments.getString("type")
        salePrice = arguments.getString("salePrice")

        fill_order_coupon_toolbar.title = "使用优惠券"
        (activity as AppCompatActivity).setSupportActionBar(fill_order_coupon_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        fill_order_coupon_toolbar.setNavigationOnClickListener {
            val bundle = Bundle()
            bundle.putString("COUPON", "")
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }

        initWebView()
    }
    private fun initWebView(){
        fill_order_coupon_webView.setDefaultHandler(DefaultHandler())
        fill_order_coupon_webView.webChromeClient = object : WebChromeClient() {

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
        var url = String.format("http://10.10.90.3:8092/use-quan.html?demandId=%s&salePrice=%s&type=%s&userId=%s", demandId, salePrice, type, DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
        EmallLogger.d(url)
        fill_order_coupon_webView.loadUrl(url)
        //必须和js同名函数，注册具体执行函数，类似java实现类。
        fill_order_coupon_webView.registerHandler("submitFromWeb", BridgeHandler { data, function ->
            // 例如你可以对原始数据进行处理
            Toast.makeText(activity, data, Toast.LENGTH_SHORT).show()
            calculatePriceByCouponIdBean = Gson().fromJson(data, CalculatePriceByCouponIdBean::class.java)
            EmallLogger.d(data)
            EmallLogger.d(calculatePriceByCouponIdBean.data.productPrice[0].coupon_type)
            var size = calculatePriceByCouponIdBean.data.productPrice.size

            for (i in 0 until size){
                coupons += calculatePriceByCouponIdBean.data.productPrice[i].coupon_type
                coupons += ","
            }


            val bundle = Bundle()
            bundle.putString("COUPON", coupons)
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        })
    }


    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}