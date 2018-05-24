package com.example.emall_ec.main.detail.example

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.DefaultHandler
import kotlinx.android.synthetic.main.delegate_noctilucence_example.*
import kotlinx.android.synthetic.main.delegate_optics1_example.*
import kotlinx.android.synthetic.main.delegate_program_example.*
import kotlinx.android.synthetic.main.delegate_video_example.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class ProgramExampleDelegate : EmallDelegate() {

    var mUploadMessage: ValueCallback<Uri>? = null

    
    fun create(): ProgramExampleDelegate?{
        return ProgramExampleDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_program_example
    }

    override fun initial() {
        setSwipeBackEnable(false)

        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        program_example_toolbar.title = getString(R.string.noctilucence)
        program_example_toolbar.title = getString(R.string.programming)
        (activity as AppCompatActivity).setSupportActionBar(program_example_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        program_example_toolbar.setNavigationIcon(R.drawable.ic_back_small)

        program_example_toolbar.setNavigationOnClickListener {
            pop()
        }

        initWebView()

    }

    private fun initWebView(){
        program_example_webView.setDefaultHandler(DefaultHandler())
        program_example_webView.webChromeClient = object : WebChromeClient() {

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
        program_example_webView.loadUrl("http://59.110.164.214:8082/orderDemo.html")
        //必须和js同名函数，注册具体执行函数，类似java实现类。
        program_example_webView.registerHandler("submitFromWeb", BridgeHandler { data, function ->
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
    
    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}