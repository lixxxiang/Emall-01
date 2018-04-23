package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.demand.data.AppPayBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_pay_method.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.mm.opensdk.modelpay.PayReq




/**
 * Created by lixiang on 2018/3/27.
 */
class PayMethodDelegate : BottomItemDelegate() {
    var flag = 1
    var appPayParams : WeakHashMap<String, Any> ?= WeakHashMap()
    var appPayBean = AppPayBean()
    private var api: IWXAPI? = null
    fun create(): PayMethodDelegate? {
        return PayMethodDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_pay_method
    }

    override fun initial() {
        pay_method_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(pay_method_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pay_method_toolbar.setNavigationOnClickListener {
            pop()
        }
        api = WXAPIFactory.createWXAPI(context, "wxd12cdf5edf0f42fd")
        api!!.registerApp("wxd12cdf5edf0f42fd")

        pay_method_pay_rl.setOnClickListener {
            if(flag == 1){
                appPayParams!!["orderId"] = arguments.getString("ORDER_ID")
                appPayParams!!["type"] = "1"
                EmallLogger.d(appPayParams!!)
                RestClient().builder()
                        .url("http://59.110.164.214:8024/global/wxpay/appPay")
                        .params(appPayParams!!)
                        .success(object : ISuccess {
                            override fun onSuccess(response: String) {
                                EmallLogger.d(response)
                                appPayBean = Gson().fromJson(response, AppPayBean::class.java)
                                sendPayRequest(appPayBean)
                            }
                        })
                        .failure(object : IFailure {
                            override fun onFailure() {

                            }
                        })
                        .error(object : IError {
                            override fun onError(code: Int, msg: String) {

                            }
                        })
                        .build()
                        .post()
            }else{
                start(OfflinePaymentDelegate().create())
            }
        }

        wechat_btn.setOnClickListener {
            if (flag != 1) {
                wechat_iv.setBackgroundResource(R.drawable.checked)
                public_iv.setBackgroundResource(R.drawable.unchecked)
                flag = 1
            }
        }

        public_btn.setOnClickListener {
            if (flag == 1) {
                wechat_iv.setBackgroundResource(R.drawable.unchecked)
                public_iv.setBackgroundResource(R.drawable.checked)
                flag = 2
            }
        }

    }

    fun sendPayRequest(appPayBean: AppPayBean) {
        EmallLogger.d("in")
        val req = PayReq()
        req.appId = appPayBean.data.appid
        req.partnerId = appPayBean.data.mch_id
        req.prepayId = appPayBean.data.prepayid
        req.nonceStr = appPayBean.data.noncestr
        req.timeStamp = appPayBean.data.timestamp
        req.packageValue = appPayBean.data.packageX
        req.sign = appPayBean.data.sign
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        //3.调用微信支付sdk支付方法
        api!!.sendReq(req)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}