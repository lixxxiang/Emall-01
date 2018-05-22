package com.example.emall_ec.main.demand

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
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
import com.example.emall_ec.api.ApiService
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.coupon.FillOrderCouponDelegate
import com.example.emall_ec.main.coupon.GoodsDetailCouponDelegate
import com.example.emall_ec.main.coupon.data.GetCouponTypeByProductIdBean
import com.example.emall_ec.main.coupon.data.GetCouponTypeByUserAndDemandBean
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat
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
    private var queryInvoiceBean = QueryInvoiceBean()
    var invoiceState = "0"
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    var userId = String()
    var couponId = "-1"
    var hasCoupon = false
    private var queryInvoiceParams: WeakHashMap<String, Any>? = WeakHashMap()
    val couponList: MutableList<String> = mutableListOf()

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

        fill_order_loading_rl.visibility = View.VISIBLE

        viewDemandParams!!["demandId"] = arguments.getString("demandId")
        viewDemandParams!!["type"] = arguments.getString("type")// 1 3 5
        viewDemandParams!!["client"] = "android"

        userId = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId

        Handler().postDelayed({
            RestClient().builder()
                    .url("http://59.110.164.214:8024/global/viewDemand")
                    .params(viewDemandParams!!)
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            viewDemandBean = Gson().fromJson(response, ViewDemandBean::class.java)
                            productId = viewDemandBean.data.demands[0].productId
                            getCoupon()
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
        }, 500)
        fill_order_to_pay.setOnClickListener {
            if (!ButtonUtils.isFastDoubleClick(R.id.fill_order_to_pay)) {
                EmallProgressBar.showProgressbar(context)
                insertOrderData()
            }
        }

        bill_rl.setOnClickListener {
            if (!isChecked) {
                cb.setBackgroundResource(R.drawable.invoice_checked)
                isChecked = true
                val delegate: InvoiceDelegate = InvoiceDelegate().create()!!
                val bundle: Bundle? = Bundle()
                bundle!!.putString("INVOICE_PRICE", viewDemandBean.data.demands[0].salePrice)
                delegate.arguments = bundle
                startForResult(delegate, 1)
                invoiceState = "1"
            } else {
                cb.setBackgroundResource(R.drawable.invoice_unchecked)
                isChecked = false
                invoiceState = "0"
            }
        }

        fill_order_coupon_rl.setOnClickListener {
            if (!hasCoupon) {

            } else {
                val delegate: FillOrderCouponDelegate = FillOrderCouponDelegate().create()!!
                val bundle: Bundle? = Bundle()
                bundle!!.putString("demandId", arguments.getString("demandId"))
                bundle.putString("salePrice", viewDemandBean.data.demands[0].salePrice)
                bundle.putString("type", arguments.getString("type"))
                bundle.putString("couponId", couponId)
                delegate.arguments = bundle
                startForResult(delegate, 2)
            }
        }
    }

    private fun getCoupon() {
        retrofit = Retrofit.Builder()
                .baseUrl("http://59.110.164.214:8024")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.getCouponTypeByUserAndDemand(arguments.getString("demandId"), userId, "android")
        call.enqueue(object : retrofit2.Callback<GetCouponTypeByUserAndDemandBean> {
            override fun onResponse(call: retrofit2.Call<GetCouponTypeByUserAndDemandBean>, response: retrofit2.Response<GetCouponTypeByUserAndDemandBean>) {
                val getCouponTypeByProductIdBean: GetCouponTypeByUserAndDemandBean
                if (response.body() != null) {
                    getCouponTypeByProductIdBean = response.body()!!
                    if (getCouponTypeByProductIdBean.message != "方法返回为空") {
                        hasCoupon = true
                        val size = getCouponTypeByProductIdBean.data.coupon.size

                        if (size in 1..3) {
                            coupon_title.text = "优惠券：  请选择"
//                        coupon_title.visibility = View.GONE
//                        for (i in 0 until size) {
//                            couponList.add(getCouponTypeByProductIdBean.data.coupon[i].toString())
//                        }
//                        when (size) {
//                            1 -> {
//                                coupon1.text = couponList[0]
//                                coupon1.visibility = View.VISIBLE
//                            }
//                            2 -> {
//                                coupon1.text = couponList[0]
//                                coupon1.visibility = View.VISIBLE
//                                coupon2.text = couponList[1]
//                                coupon2.visibility = View.VISIBLE
//                            }
//                            3 -> {
//                                coupon1.text = couponList[0]
//                                coupon1.visibility = View.VISIBLE
//                                coupon2.text = couponList[1]
//                                coupon2.visibility = View.VISIBLE
//                                coupon3.text = couponList[2]
//                                coupon3.visibility = View.VISIBLE
//                            }
//                        }
                        } else {

                        }
                    } else {
                        hasCoupon = false
                        coupon_title.text = "优惠券：  暂无可用"
                    }

                } else {
                    hasCoupon = false
                    coupon_title.text = "优惠券：  暂无可用"
                }
            }

            override fun onFailure(call: retrofit2.Call<GetCouponTypeByUserAndDemandBean>, throwable: Throwable) {}
        })
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK) {
            val index = data.getString("flag")
            EmallLogger.d(index)
            if (index == "false") {
                cb.setBackgroundResource(R.drawable.invoice_unchecked)
                isChecked = false
                invoiceState = "0"
            } else if (index == "true") {
                cb.setBackgroundResource(R.drawable.invoice_checked)
                isChecked = true
                invoiceState = "1"
            }
        } else if (requestCode == 2 && resultCode == ISupportFragment.RESULT_OK) {
            val sp = activity.getSharedPreferences("COUPON_ID", Context.MODE_PRIVATE)
            couponId = sp.getString("couponId", "")
            val index = data.getString("COUPON")
            var price = data.getString("PRICE")
            EmallLogger.d(index)
            EmallLogger.d(couponId)
            if (index != "") {
                val size = index.split(",").size
                if (size > 2) {
                    Toast.makeText(context, "coupon error", Toast.LENGTH_SHORT).show()
                } else {
                    coupon_title.visibility = View.GONE
                    coupon1.visibility = View.VISIBLE
                    coupon1.text = index.split(",")[0]
                    fill_order_dp_tv.text = String.format("-¥%s", DecimalFormat("######0.00").format(viewDemandBean.data.demands[0].originalPrice.toDouble() - price.split(",")[0].toDouble()))
                    fill_order_sale_price_tv.text = String.format("应付：¥%s",  price.split(",")[0])
                    fill_order_out_tv.text = String.format("¥%s", price.split(",")[0])

                }
            } else {
                coupon_title.visibility = View.VISIBLE
                coupon1.visibility = View.GONE
                fill_order_dp_tv.text = String.format("-¥%s", DecimalFormat("######0.00").format(viewDemandBean.data.demands[0].originalPrice.toDouble() - viewDemandBean.data.demands[0].salePrice.toDouble()))
                fill_order_out_tv.text = String.format("¥%s", viewDemandBean.data.demands[0].salePrice)
                fill_order_sale_price_tv.text = String.format("应付：¥%s", viewDemandBean.data.demands[0].salePrice)
            }
        }
    }

    private fun insertOrderData() {
        if (couponId != "-1"){
            orderParams!!["userCouponId"] = couponId
        }
        orderParams!!["type"] = arguments.getString("type")
        orderParams!!["invoiceState"] = invoiceState
        orderParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        orderParams!!["productId"] = productId
        orderParams!!["parentOrderId"] = arguments.getString("demandId")
        orderParams!!["userName"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].username
        orderParams!!["userTelephone"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userTelephone
        orderParams!!["client"] = "android"
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
                            bundle.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))
                            bundle.putString("title", arguments.getString("title"))
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
        println("INITVIEWS")
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
        fill_order_dp_tv.text = String.format("-¥%s", DecimalFormat("######0.00").format(viewDemandBean.data.demands[0].originalPrice.toDouble() - viewDemandBean.data.demands[0].salePrice.toDouble()))
        fill_order_out_tv.text = String.format("¥%s", viewDemandBean.data.demands[0].salePrice)
        fill_order_sale_price_tv.text = String.format("应付：¥%s", viewDemandBean.data.demands[0].salePrice)
        fill_order_loading_rl.visibility = View.GONE
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}