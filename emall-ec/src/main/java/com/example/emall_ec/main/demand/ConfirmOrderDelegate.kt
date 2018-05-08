package com.example.emall_ec.main.demand

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.demand.data.OrderBean
import com.example.emall_ec.main.demand.data.ViewDemandBean
import com.example.emall_ec.main.order.OrderListDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_fill_order.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.text.DecimalFormat
import java.util.*

/**
 * Created by lixiang on 2018/3/27.
 */
class ConfirmOrderDelegate : BottomItemDelegate() {

    var viewDemandParams: WeakHashMap<String, Any>? = WeakHashMap()
    var viewDemandBean = ViewDemandBean()
    var isChecked = false
    var orderParams: WeakHashMap<String, Any>? = WeakHashMap()
    var productId = String()
    var orderBean = OrderBean()
    fun create(): ConfirmOrderDelegate? {
        return ConfirmOrderDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_confirm_order
    }

    override fun initial() {
        fill_order_toolbar.title = getString(R.string.confirm_order)
        (activity as AppCompatActivity).setSupportActionBar(fill_order_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fill_order_toolbar.setNavigationOnClickListener {
            pop()
        }

        viewDemandParams!!["demandId"] = arguments.getString("demandId")
        viewDemandParams!!["type"] = arguments.getString("type")// 1 3 5

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/viewDemand")
                .params(viewDemandParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        viewDemandBean = Gson().fromJson(response, ViewDemandBean::class.java)
                        productId = viewDemandBean.data.demands[0].productId
                        initViews(viewDemandBean)
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

        bill_rl.setOnClickListener {
            if (!isChecked) {
                cb.setBackgroundResource(R.drawable.invoice_checked)
                isChecked = true
                val delegate: InvoiceDelegate = InvoiceDelegate().create()!!
                val bundle: Bundle? = Bundle()
                bundle!!.putString("INVOICE_PRICE", viewDemandBean.data.demands[0].salePrice)
                delegate.arguments = bundle
                start(delegate)
            } else {
                cb.setBackgroundResource(R.drawable.invoice_unchecked)
                isChecked = false
            }
        }

        fill_order_to_pay.setOnClickListener {
            insertOrderData()
        }
    }

    private fun insertOrderData() {
        orderParams!!["type"] = arguments.getString("type")
        orderParams!!["invoiceState"] = "0"
        orderParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        orderParams!!["productId"] = productId
        orderParams!!["parentOrderId"] = arguments.getString("demandId")
        orderParams!!["userName"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].username
        orderParams!!["userTelephone"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userTelephone

        EmallLogger.d(orderParams!!)
        RestClient().builder()
                .url("http://59.110.164.214:8025/global/order/app/create/order")
                .params(orderParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        orderBean = Gson().fromJson(response, OrderBean::class.java)
                        EmallLogger.d(orderBean)
                        if (orderBean.msg == "成功") {
//                            val delegate: PayMethodDelegate = PayMethodDelegate().create()!!
//                            val bundle: Bundle? = Bundle()
//                            bundle!!.putString("ORDER_ID",orderBean.data.parentOrderId)
//                            bundle.putString("DEMAND_ID", arguments.getString("demandId"))
//                            bundle.putString("TYPE", "1")
//                            delegate.arguments = bundle
//                            start(delegate)

                            val delegate: OrderListDelegate = OrderListDelegate().create()!!
                            val bundle: Bundle? = Bundle()
                            bundle!!.putString("ORDER_ID",orderBean.data.parentOrderId)
                            bundle.putString("DEMAND_ID", arguments.getString("demandId"))
                            bundle.putString("TYPE", "1")
                            bundle.putString("PAGE_FROM", "PROGRAM")
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

    fun initViews(viewDemandBean: ViewDemandBean) {
        if (arguments.getString("imageUrl") == "program") {
            fill_order_iv.setBackgroundResource(R.drawable.program)
        } else
            Glide.with(context)
                    .load(arguments.getString("imageUrl"))
                    .into(fill_order_iv)
        fill_order_title_tv.text = arguments.getString("title")
        fill_order_time_tv.text = arguments.getString("time")
        fill_order_op_tv.text = String.format("¥%s", viewDemandBean.data.demands[0].originalPrice)
        fill_order_cp_tv.text = String.format("¥%s", viewDemandBean.data.demands[0].salePrice)
        fill_order_dp_tv.text = String.format("-¥%s", DecimalFormat("######0.00").format(viewDemandBean.data.demands[0].originalPrice.toDouble() - viewDemandBean.data.demands[0].salePrice.toDouble()))
        fill_order_out_tv.text = String.format("¥%s", viewDemandBean.data.demands[0].salePrice)
        fill_order_sale_price_tv.text = String.format("应付：¥%s", viewDemandBean.data.demands[0].salePrice)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}