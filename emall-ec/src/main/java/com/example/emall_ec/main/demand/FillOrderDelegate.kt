package com.example.emall_ec.main.demand

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.progressbar.EmallProgressBar
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.ButtonUtils
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.demand.data.OrderBean
import com.example.emall_ec.main.demand.data.QueryInvoiceBean
import com.example.emall_ec.main.demand.data.ViewDemandBean
import com.example.emall_ec.main.order.state.adapter.AllListAdapter.programArray
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_fill_order.*
import kotlinx.android.synthetic.main.delegate_invoice.*
import kotlinx.android.synthetic.main.me_function_item.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

/**
 * Created by lixiang on 2018/3/27.
 */
class FillOrderDelegate : BottomItemDelegate() {

    var viewDemandParams: WeakHashMap<String, Any>? = WeakHashMap()
    var viewDemandBean = ViewDemandBean()
    var isChecked = false
    var orderParams: WeakHashMap<String, Any>? = WeakHashMap()
    var productId = String()
    var orderBean = OrderBean()
    var hasInvoice = false
    private var queryInvoiceBean = QueryInvoiceBean()

    private var queryInvoiceParams: WeakHashMap<String, Any>? = WeakHashMap()

    fun create(): FillOrderDelegate? {
        return FillOrderDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_fill_order
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        fill_order_toolbar.title = getString(R.string.fill_order)
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
                        getInvoice()

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

        bill_btn.setOnClickListener {
            val delegate: InvoiceDelegate = InvoiceDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("INVOICE_PRICE", viewDemandBean.data.demands[0].salePrice)
            delegate.arguments = bundle
            startForResult(delegate, 1)
        }

        fill_order_to_pay.setOnClickListener {
            if (!ButtonUtils.isFastDoubleClick(R.id.fill_order_to_pay)) {
                EmallProgressBar.showProgressbar(context)
                insertOrderData()
            }
        }

        bill_icon_btn.setOnClickListener {
            EmallLogger.d(isChecked)
            if (!isChecked) {
                if (hasInvoice) {//没勾选 有发票
                    cb.setBackgroundResource(R.drawable.invoice_checked)
                    isChecked = true
                } else {//没勾选 没发票
                    val delegate: InvoiceDelegate = InvoiceDelegate().create()!!
                    val bundle: Bundle? = Bundle()
                    bundle!!.putString("INVOICE_PRICE", viewDemandBean.data.demands[0].salePrice)
                    delegate.arguments = bundle
                    startForResult(delegate, 1)
                }
            } else {
                cb.setBackgroundResource(R.drawable.invoice_unchecked)
                isChecked = false
            }
        }
    }

    private fun getInvoice() {
        queryInvoiceParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/query/invoice")
                .params(queryInvoiceParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        queryInvoiceBean = Gson().fromJson(response, QueryInvoiceBean::class.java)
                        if (queryInvoiceBean.message == "success") {
                            hasInvoice = true
                        }
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .build()
                .post()
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK) {
            val index = data.getString("flag")
            EmallLogger.d(index)
            if (index == "false") {
                cb.setBackgroundResource(R.drawable.invoice_unchecked)
                isChecked = false
                hasInvoice = false
            } else if (index == "true") {
                cb.setBackgroundResource(R.drawable.invoice_checked)
                isChecked = true
                hasInvoice = true
            }
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
                            EmallProgressBar.hideProgressbar()
                            val delegate: PayMethodDelegate = PayMethodDelegate().create()!!
                            val bundle: Bundle? = Bundle()
                            bundle!!.putString("PARENT_ORDER_ID", orderBean.data.parentOrderId)
                            bundle.putString("TYPE", "1")
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

    @SuppressLint("SetTextI18n")
    fun initViews(viewDemandBean: ViewDemandBean) {
        if (arguments.getString("imageUrl") == "program") {
            fill_order_iv.setBackgroundResource(R.drawable.program)
        } else
            Glide.with(context)
                    .load(arguments.getString("imageUrl"))
                    .into(fill_order_iv)

        fill_order_title_tv.text = arguments.getString("title")
        EmallLogger.d(arguments.getString("type"))
        if (arguments.getString("type") == "2") {
            fill_order_time_tv.text = arguments.getString("time")
        } else
            fill_order_time_tv.text = String.format("拍摄于 %s（北京时间）", arguments.getString("time"))
        fill_order_op_tv.text = String.format("¥%s", viewDemandBean.data.demands[0].originalPrice)
        fill_order_cp_tv.text = String.format("¥%s", viewDemandBean.data.demands[0].salePrice)
        fill_order_dp_tv.text = String.format("-¥%s", viewDemandBean.data.demands[0].originalPrice.toDouble() - viewDemandBean.data.demands[0].salePrice.toDouble())
        fill_order_out_tv.text = String.format("¥%s", viewDemandBean.data.demands[0].salePrice)
        fill_order_sale_price_tv.text = String.format("应付：¥%s", viewDemandBean.data.demands[0].salePrice)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}