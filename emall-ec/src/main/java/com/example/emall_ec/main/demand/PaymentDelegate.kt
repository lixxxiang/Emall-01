package com.example.emall_ec.main.demand

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.demand.data.FindDetailByParentOrderIdBean
import com.example.emall_ec.main.demand.data.FindOrderDetailByOrderIdBean
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_payment.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

class PaymentDelegate : EmallDelegate() {

    var findDetailByParentOrderIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var findOrderDetailByOrderIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var findDetailByParentOrderIdBean = FindDetailByParentOrderIdBean()
    var findOrderDetailByOrderIdBean = FindOrderDetailByOrderIdBean()

    var payMethodArray = arrayOf("支付宝", "微信支付", "银行汇款", "线下支付")

    fun create(): PaymentDelegate? {
        return PaymentDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_payment
    }

    override fun initial() {
        payment_toolbar.title = getString(R.string.payment)
        (activity as AppCompatActivity).setSupportActionBar(payment_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        payment_toolbar.setNavigationOnClickListener {
            pop()
        }

        findDetailByParentOrderId()

        payment_success_check_order_list_btn.setOnClickListener {
            findOrderDetailByOrderId()
        }
    }

    private fun findOrderDetailByOrderId() {
        findOrderDetailByOrderIdParams!!["orderId"] = findDetailByParentOrderIdBean.data[0].orderId
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findOrderDetailByOrderId")
                .params(findOrderDetailByOrderIdParams!!)
                .success(object : ISuccess {
                    @SuppressLint("ApplySharedPref")
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        findOrderDetailByOrderIdBean = Gson().fromJson(response, FindOrderDetailByOrderIdBean::class.java)
                        if (findOrderDetailByOrderIdBean.message == "success") {
                            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
                            val bundle: Bundle? = Bundle()
                            bundle!!.putString("KEY", "ID")
                            bundle.putParcelable("KEy", findOrderDetailByOrderIdBean)
                            bundle.putInt("INDEX", 0)
                            bundle.putString("FROM", "PAYMENT")
                            delegate.arguments = bundle
                            start(delegate)
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

    private fun findDetailByParentOrderId() {
        findDetailByParentOrderIdParams!!["parentOrderId"] = arguments.getString("DEMAND_ID")
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findDetailByParentOrderId")
                .params(findDetailByParentOrderIdParams!!)
                .success(object : ISuccess {
                    @SuppressLint("ApplySharedPref")
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        findDetailByParentOrderIdBean = Gson().fromJson(response, FindDetailByParentOrderIdBean::class.java)
                        if (findDetailByParentOrderIdBean.message == "success") {
                            initViews(findDetailByParentOrderIdBean.data)
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

    private fun initViews(data: MutableList<FindDetailByParentOrderIdBean.DataBean>) {
        if (payment_failure_rl.visibility == View.VISIBLE)
            payment_failure_rl.visibility = View.GONE
        if (payment_success_rl.visibility == View.GONE)
            payment_success_rl.visibility = View.VISIBLE
        payment_commit_time.text = String.format("预计 %s 交付", arguments.getString("COMMIT_TIME").split(" ")[0])
        Glide.with(context)
                .load(data[0].details.imageDetailUrl)
                .into(payment_iv)
        payment_title_tv.text = data[0].productId
        payment_time_tv.text = timeFormat(data[0].details.centerTime)
        payment_price_tv.text =  String.format("¥%s", data[0].details.salePrice)
        payment_detail_id_tv.text = data[0].orderId
        payment_detail_order_time_tv.text = data[0].commitTime
        payment_detail_pay_method_tv.text = payMethodArray[data[0].payMethod - 1]
        payment_detail_origional_price_tv.text = String.format("¥%s", data[0].details.originalPrice)
        payment_detail_current_price_tv.text =String.format("¥%s", data[0].details.salePrice)
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
        return String.format("-¥%s",originalPrice.toDouble() - payment)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}