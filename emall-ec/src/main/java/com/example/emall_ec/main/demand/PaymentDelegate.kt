package com.example.emall_ec.main.demand

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.baidu.location.g.j.ar
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.R.string.discount
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.demand.data.FindDetailByParentOrderIdBean
import com.example.emall_ec.main.demand.data.FindOrderDetailByOrderIdBean
import com.example.emall_ec.main.me.setting.AccountPrivacySettingsDelegate
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.example.emall_ec.main.order.OrderListDelegate
import com.example.emall_ec.main.order.state.adapter.AllListAdapter
import com.example.emall_ec.main.order.state.adapter.AllListAdapter.programArray
import com.example.emall_ec.main.order.state.adapter.AllListAdapter.typeArray
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_order_detail.*
import kotlinx.android.synthetic.main.delegate_payment.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

class PaymentDelegate : EmallDelegate() {

    var findDetailByParentOrderIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var orderDetail = OrderDetail()
    var mSharedPreferences: SharedPreferences? = null
    var payMethodArray = arrayOf("支付宝", "微信支付", "银行汇款", "线下支付")
    var findOrderDetailByOrderIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var findDetailByParentOrderIdBean = FindDetailByParentOrderIdBean()
    var findOrderDetailByOrderIdBean = FindOrderDetailByOrderIdBean()
    fun create(): PaymentDelegate? {
        return PaymentDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_payment
    }

    override fun initial() {
        setSwipeBackEnable(false)

        payment_toolbar.title = getString(R.string.payment)
        (activity as AppCompatActivity).setSupportActionBar(payment_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        payment_toolbar.setNavigationOnClickListener {
            //            popTo(findFragment(OrderListDelegate().javaClass).javaClass, false)
            val editor = mSharedPreferences!!.edit()
            editor.putString("BACK_FROM", "PAYMENT")
            editor.commit()
            pop()
        }

        EmallLogger.d(arguments.getString("PAGE_FROM"))
        if (arguments.getString("PAGE_FROM") == "GOODS_DETAIL") {
            findDetailByParentOrderId()
        } else {
            findOrderDetailByOrderId()
        }
        mSharedPreferences = activity.getSharedPreferences("PAGE_BACK", Context.MODE_PRIVATE)
        payment_success_check_order_list_btn.setOnClickListener {
            val delegate: OrderListDelegate = OrderListDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("USER_ID", DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
            bundle.putInt("INDEX", 0)
            bundle.putString("FROM", "PAYMENT")
            bundle.putString("PAGE_FROM", "PAYMENT")
            delegate.arguments = bundle
            start(delegate)
        }
        payment_failure_check_order_list_btn.setOnClickListener {
            val delegate: OrderListDelegate = OrderListDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("USER_ID", DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
            bundle.putInt("INDEX", 0)
            bundle.putString("FROM", "PAYMENT")
            bundle.putString("PAGE_FROM", "PAYMENT")
            delegate.arguments = bundle
            start(delegate)
        }

        payment_failure_repay_btn.setOnClickListener {
            val delegate: PayMethodDelegate = PayMethodDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("PARENT_ORDER_ID", arguments.getString("PARENT_ORDER_ID"))
            bundle.putString("TYPE", arguments.getString("TYPE"))
            bundle.putString("PAGE_FROM", "PAYMENT")
            delegate.arguments = bundle
            startWithPop(delegate)
        }
    }

    private fun findDetailByParentOrderId() {
        findDetailByParentOrderIdParams!!["parentOrderId"] = arguments.getString("PARENT_ORDER_ID")
        EmallLogger.d(arguments.getString("PARENT_ORDER_ID"))
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findDetailByParentOrderId")
                .params(findDetailByParentOrderIdParams!!)
                .success(object : ISuccess {
                    @SuppressLint("ApplySharedPref")
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
                        if (orderDetail.message == "success") {
                            initViews(orderDetail.data)
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

    private fun findOrderDetailByOrderId() {
        findOrderDetailByOrderIdParams!!["orderId"] = arguments.getString("PARENT_ORDER_ID")
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findOrderDetailByOrderId")
                .params(findOrderDetailByOrderIdParams!!)
                .success(object : ISuccess {
                    @SuppressLint("ApplySharedPref")
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        findOrderDetailByOrderIdBean = Gson().fromJson(response, FindOrderDetailByOrderIdBean::class.java)
                        if (findOrderDetailByOrderIdBean.message == "success") {
                            initViews(findOrderDetailByOrderIdBean.data)
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

    private fun initViews(data: FindOrderDetailByOrderIdBean.DataBean?) {
        if (arguments.getString("STATUS") == "SUCCESS") {
            if (payment_failure_rl != null && payment_success_rl != null && payment_detail_pay_method_tv != null) {
                payment_failure_rl.visibility = View.GONE
                payment_success_rl.visibility = View.VISIBLE
                payment_commit_time.text = String.format("预计 %s 交付", arguments.getString("COMMIT_TIME").split(" ")[0])
                payment_detail_pay_method_tv.text = payMethodArray[data!!.payMethod - 1]
            }
        } else if (arguments.getString("STATUS") == "FAILURE") {
            if (payment_failure_rl != null && payment_success_rl != null && payment_detail_pay_method_tv != null) {
                payment_failure_rl.visibility = View.VISIBLE
                payment_success_rl.visibility = View.GONE
                payment_detail_pay_method_tv.text = arguments.getString("PAYMETHOD")
            }
        }

        if (data!!.details.imageDetailUrl == null) {
            payment_iv.setBackgroundResource(R.drawable.program)
        } else
            Glide.with(context)
                    .load(data.details.imageDetailUrl)
                    .into(payment_iv)
        payment_title_tv.text = typeArray[data.type]

        if (data.type == 2) {
            payment_time_tv.text = "类型：" + programArray[Integer.parseInt(data.details.productType)]
        } else {
            if (data.details.centerTime == null)
                payment_time_tv.text = data.details.startTime
            else
                payment_time_tv.text = timeFormat(data.details.centerTime)
        }


        payment_price_tv.text = String.format("¥%s", data.details.salePrice)
        payment_detail_id_tv.text = data.orderId
        payment_detail_order_time_tv.text = data.commitTime
        payment_detail_origional_price_tv.text = String.format("¥%s", data.details.originalPrice)
        payment_detail_current_price_tv.text = String.format("¥%s", data.details.salePrice)
        payment_detail_final_price_tv.text = String.format("¥%s", data.payment)
        payment_detail_discount_tv.text = discount(data.details.originalPrice, data.payment)
    }


    private fun initViews(data: MutableList<OrderDetail.DataBean>) {
        if (arguments.getString("STATUS") == "SUCCESS") {
            if (payment_failure_rl != null && payment_success_rl != null && payment_detail_pay_method_tv != null) {
                payment_failure_rl.visibility = View.GONE
                payment_success_rl.visibility = View.VISIBLE
                payment_commit_time.text = String.format("预计 %s 交付", arguments.getString("COMMIT_TIME").split(" ")[0])
                payment_detail_pay_method_tv.text = payMethodArray[data[0].payMethod - 1]
            }
        } else if (arguments.getString("STATUS") == "FAILURE") {
            if (payment_failure_rl != null && payment_success_rl != null && payment_detail_pay_method_tv != null) {
                payment_failure_rl.visibility = View.VISIBLE
                payment_success_rl.visibility = View.GONE
                payment_detail_pay_method_tv.text = arguments.getString("PAYMETHOD")
            }
        }

        if (data[0].details.imageDetailUrl == null) {
            payment_iv.setBackgroundResource(R.drawable.program)
        } else
            Glide.with(context)
                    .load(data[0].details.imageDetailUrl)
                    .into(payment_iv)
        payment_title_tv.text = typeArray[data[0].type]
        if (data[0].details.centerTime == null) {
            payment_time_tv.text = timeFormat(data[0].details.startTime)

        } else
            payment_time_tv.text = timeFormat(data[0].details.centerTime)

        payment_price_tv.text = String.format("¥%s", data[0].details.salePrice)
        payment_detail_id_tv.text = data[0].orderId
        payment_detail_order_time_tv.text = data[0].commitTime
        payment_detail_origional_price_tv.text = String.format("¥%s", data[0].details.originalPrice)
        payment_detail_current_price_tv.text = String.format("¥%s", data[0].details.salePrice)
        payment_detail_final_price_tv.text = String.format("¥%s", data[0].payment)
        payment_detail_discount_tv.text = discount(data[0].details.originalPrice, data[0].payment)
    }


    fun timeFormat(centerTime: String): String {
        return String.format("拍摄于 %s（北京时间）", centerTime.replace(" ", "，"))
    }

    private fun discount(originalPrice: String, payment: Double): String {
        /**
         * 这里的算价有问题！！！
         */
        EmallLogger.d(originalPrice.toDouble())
        EmallLogger.d(payment)
        return String.format("-¥%s", originalPrice.toDouble() - payment)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onBackPressedSupport(): Boolean {
        val editor = mSharedPreferences!!.edit()
        editor.putString("BACK_FROM", "PAYMENT")
        editor.commit()
        pop()
        return true
    }

}