package com.example.emall_ec.main.demand

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.demand.data.AppPayBean
import kotlinx.android.synthetic.main.delegate_pay_method.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.mm.opensdk.modelpay.PayReq
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.support.annotation.RequiresApi
import android.view.View
import com.example.emall_core.ui.progressbar.EmallProgressBar
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.api.ApiService
import com.example.emall_ec.api.NetUtils
import com.example.emall_ec.main.demand.data.QueryOrderBean
import com.example.emall_ec.main.demand.data.QueryOrderFailureBean
import com.example.emall_ec.main.order.OrderListDelegate
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by lixiang on 2018/3/27.
 */
class PayMethodDelegate : BottomItemDelegate() {
    var flag = 1
    var appPayParams: WeakHashMap<String, Any>? = WeakHashMap()
    var appPayBean = AppPayBean()
    var queryOrderParams: WeakHashMap<String, Any>? = WeakHashMap()
    var queryOrderBean = QueryOrderBean()
    var queryOrderFailureBean = QueryOrderFailureBean()
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    private var api: IWXAPI? = null
    var mSharedPreferences: SharedPreferences? = null

    var count = 0
    fun create(): PayMethodDelegate? {
        return PayMethodDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_pay_method
    }

    override fun initial() {
        pay_method_toolbar.title = getString(R.string.choose_pay_method)
        mSharedPreferences = activity.getSharedPreferences("BACK_FROM", Context.MODE_PRIVATE)

        (activity as AppCompatActivity).setSupportActionBar(pay_method_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pay_method_toolbar.setNavigationOnClickListener {
            val editor = mSharedPreferences!!.edit()
            editor.putString("BACK_FROM", "PAY_METHOD")
            editor.commit()
            pop()
        }

        pay_method_pay_rl.setOnClickListener {
            if (flag == 1) {
                retrofit = NetUtils.getRetrofit()
                apiService = retrofit!!.create(ApiService::class.java)
                EmallLogger.d(arguments.getString("PARENT_ORDER_ID"))
                EmallLogger.d(arguments.getString("TYPE"))
                val call = apiService!!.appPay(arguments.getString("PARENT_ORDER_ID"), arguments.getString("TYPE"), "2")
                call.enqueue(object : retrofit2.Callback<AppPayBean> {
                    override fun onResponse(call: retrofit2.Call<AppPayBean>, response: retrofit2.Response<AppPayBean>) {
                        if (response.body() != null) {
                            EmallLogger.d(response.body()!!)
                            appPayBean = response.body()!!
                            EmallLogger.d(appPayBean.data.toString())
                            EmallLogger.d(appPayBean.toString())
                            api = WXAPIFactory.createWXAPI(activity, "wxd12cdf5edf0f42fd", true)
                            api!!.registerApp("wxd12cdf5edf0f42fd")
                            sendPayRequest(appPayBean)
                        } else {
                            EmallLogger.d("error")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<AppPayBean>, throwable: Throwable) {}
                })
            } else {
                retrofit = NetUtils.getRetrofit()
                apiService = retrofit!!.create(ApiService::class.java)
                EmallLogger.d(arguments.getString("PARENT_ORDER_ID"))
                EmallLogger.d(arguments.getString("TYPE"))
                val call = apiService!!.appPay(arguments.getString("PARENT_ORDER_ID"), arguments.getString("TYPE"), "3")
                call.enqueue(object : retrofit2.Callback<AppPayBean> {
                    override fun onResponse(call: retrofit2.Call<AppPayBean>, response: retrofit2.Response<AppPayBean>) {
                        if (response.body() != null) {
                            if (response.body()!!.code == 0){
                                val delegate: OfflinePaymentDelegate = OfflinePaymentDelegate().create()!!
                                val bundle = Bundle()
                                delegate.arguments = bundle
                                bundle.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))
                                start(delegate)
                            }
                        } else {
                            EmallLogger.d("error")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<AppPayBean>, throwable: Throwable) {}
                })
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ApplySharedPref")
    override fun onSupportVisible() {
        super.onSupportVisible()
        val sp = activity.getSharedPreferences("WX_RETURN", Context.MODE_PRIVATE)
        when (sp.getString("WX", "")) {
            "0" -> {
                queryOrderSuccess(sp)
            }
            "-1" -> {

            }
            "-2" -> {
                queryOrderFailure(sp)
            }
        }

        var sp2 = activity.getSharedPreferences("PAGE_BACK", Context.MODE_PRIVATE)

        if (sp2.getString("BACK_FROM", "") == "PAYMENT") {
            sp2.edit().clear().commit()
            EmallProgressBar.showProgressbar(context)
            pay_method_pay_rl.visibility = View.GONE
            pay_method_sv.visibility = View.GONE
            back_rv.visibility = View.VISIBLE
            pay_method_toolbar.visibility = View.GONE
            ll_bar.visibility = View.GONE
            val delegate: OrderListDelegate = OrderListDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("USER_ID", DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
            bundle.putInt("INDEX", 0)
            bundle.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))
            println("!!!!!!!!!" + arguments.getString("PAGE_FROM"))
            delegate.arguments = bundle
            Handler().postDelayed({
                start(delegate)
            }, 1000)
//            EmallProgressBar.hideProgressbar()
        }
    }

    fun queryOrderSuccess(sp: SharedPreferences) {
        retrofit = Retrofit.Builder()
                .baseUrl("http://59.110.164.214:8024/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiService = retrofit!!.create(ApiService::class.java)
        EmallLogger.d(arguments.getString("PARENT_ORDER_ID"))
        EmallLogger.d(arguments.getString("TYPE"))
        val call = apiService!!.queryOrder(arguments.getString("PARENT_ORDER_ID"), arguments.getString("TYPE"))
        call.enqueue(object : retrofit2.Callback<QueryOrderBean> {
            override fun onResponse(call: retrofit2.Call<QueryOrderBean>, response: retrofit2.Response<QueryOrderBean>) {
                if (response.body() != null) {
                    queryOrderBean = response.body()!!
                    EmallLogger.d(queryOrderBean.toString())

                    EmallLogger.d(queryOrderBean.data.toString())
                    if (queryOrderBean.message == "已支付成功" || queryOrderBean.message == "支付成功") {
                        val delegate: PaymentDelegate = PaymentDelegate().create()!!
                        val bundle = Bundle()
                        delegate.arguments = bundle
                        bundle.putString("PARENT_ORDER_ID", arguments.getString("PARENT_ORDER_ID"))
                        bundle.putString("COMMIT_TIME", queryOrderBean.data.planCommitTime)
                        bundle.putString("STATUS", "SUCCESS")
                        bundle.putString("TYPE", arguments.getString("TYPE"))
                        bundle.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))
                        bundle.putString("title",arguments.getString("title"))
                        start(delegate)


                        /**
                         * 这里有隐患
                         */
                        sp.edit().clear().commit()
                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<QueryOrderBean>, throwable: Throwable) {}
        })
    }

    fun queryOrderFailure(sp: SharedPreferences) {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        EmallLogger.d(arguments.getString("PARENT_ORDER_ID"))
        val call = apiService!!.queryOrderFailure(arguments.getString("PARENT_ORDER_ID"), arguments.getString("TYPE"))
        call.enqueue(object : retrofit2.Callback<QueryOrderFailureBean> {
            override fun onResponse(call: retrofit2.Call<QueryOrderFailureBean>, response: retrofit2.Response<QueryOrderFailureBean>) {
                if (response.body() != null) {
                    EmallLogger.d(response)
                    queryOrderFailureBean = response.body()!!
                    if (queryOrderFailureBean.message == "未付款成功") {
                        val delegate: PaymentDelegate = PaymentDelegate().create()!!
                        val bundle = Bundle()
                        delegate.arguments = bundle
                        bundle.putString("PARENT_ORDER_ID", arguments.getString("PARENT_ORDER_ID"))
                        bundle.putString("STATUS", "FAILURE")
                        bundle.putString("PAYMETHOD", "微信")
                        bundle.putString("TYPE", arguments.getString("TYPE"))
                        bundle.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))


                        start(delegate)
                        sp.edit().clear().commit()
                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<QueryOrderFailureBean>, throwable: Throwable) {}
        })
    }


}

