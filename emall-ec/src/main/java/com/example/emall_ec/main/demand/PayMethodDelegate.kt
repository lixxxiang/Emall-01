package com.example.emall_ec.main.demand

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
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
import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.emall_ec.main.demand.data.QueryOrderBean


/**
 * Created by lixiang on 2018/3/27.
 */
class PayMethodDelegate : BottomItemDelegate() {
    var flag = 1
    var appPayParams: WeakHashMap<String, Any>? = WeakHashMap()
    var appPayBean = AppPayBean()
    var queryOrderParams: WeakHashMap<String, Any>? = WeakHashMap()
    var queryOrderBean = QueryOrderBean()
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


        pay_method_pay_rl.setOnClickListener {
            if (flag == 1) {
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
                                api = WXAPIFactory.createWXAPI(activity, "wxd12cdf5edf0f42fd", true)
                                api!!.registerApp("wxd12cdf5edf0f42fd")
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
            } else {
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
        Toast.makeText(activity, appPayBean.data.packageX, Toast.LENGTH_SHORT).show()
        req.appId = appPayBean.data.appid
        req.partnerId = appPayBean.data.mch_id
        req.prepayId = appPayBean.data.prepayid
        req.nonceStr = appPayBean.data.noncestr
        req.timeStamp = appPayBean.data.timestamp
        req.packageValue = appPayBean.data.packageX

        val parameters = TreeMap<Any, Any>()
        parameters["appid"] = req.appId
        parameters["noncestr"] = req.nonceStr
        parameters["package"] = req.packageValue
        parameters["partnerid"] = req.partnerId
        parameters["prepayid"] = req.prepayId
        parameters["timestamp"] = req.timeStamp
        req.sign = Sign.createSign("UTF-8", parameters)
        api!!.sendReq(req)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    @SuppressLint("ApplySharedPref")
    override fun onSupportVisible() {
        super.onSupportVisible()
        val sp = activity.getSharedPreferences("WX_RETURN", Context.MODE_PRIVATE)
        when (sp.getString("WX", "")) {
            "0" -> {
                queryOrder(sp)
            }
            "-1" -> {

            }
            "-2" -> {

            }
        }
    }

    fun queryOrder(sp: SharedPreferences) {
        queryOrderParams!!["parentOrderId"] = arguments.getString("ORDER_ID")
        queryOrderParams!!["type"] = "1"

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/wxpay/queryOrder")
                .params(queryOrderParams!!)
                .success(object : ISuccess {
                    @SuppressLint("ApplySharedPref")
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        queryOrderBean = Gson().fromJson(response, QueryOrderBean::class.java)
                        if (queryOrderBean.message == "已支付成功" || queryOrderBean.message == "支付成功") {
                            val delegate: PaymentDelegate = PaymentDelegate().create()!!
                            val bundle = Bundle()
                            delegate.arguments = bundle
                            bundle.putString("COMMIT_TIME", queryOrderBean.data.planCommitTime)
                            bundle.putString("DEMAND_ID", arguments.getString("DEMAND_ID"))
                            start(delegate)
                            /**
                             * 这里有隐患
                             */
                            sp.edit().clear().commit()
                        }
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
    }
}

//{
//    "data": {
//    "sumPrice": 0.01,
//    "planCommitTime": "2018-05-16 16:27:37"
//},
//    "message": "已支付成功",
//    "status": 200
//}