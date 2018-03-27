package com.example.emall_ec.main.demand

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.demand.data.ViewDemandBean
import com.example.emall_ec.main.detail.PayMethodDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_fill_order.*
import kotlinx.android.synthetic.main.me_function_item.*
import java.util.*

/**
 * Created by lixiang on 2018/3/27.
 */
class FillOrderDelegate : BottomItemDelegate() {

    var viewDemandParams: WeakHashMap<String, Any>? = WeakHashMap()
    var viewDemandBean = ViewDemandBean()
    var isChecked = false
    fun create(): FillOrderDelegate? {
        return FillOrderDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_fill_order
    }

    override fun initial() {
        fill_order_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(fill_order_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        EmallLogger.d(arguments.getString("title"))

        viewDemandParams!!["demandId"] = arguments.getString("demandId")
        viewDemandParams!!["type"] = "1"
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/viewDemand")
                .params(viewDemandParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        viewDemandBean = Gson().fromJson(response, ViewDemandBean::class.java)
                        initViews(viewDemandBean)
//                        commoditySubmitDemandBean = Gson().fromJson(response, CommoditySubmitDemandBean::class.java)
//                        val delegate: FillOrderDelegate = FillOrderDelegate().create()!!
//                        val bundle: Bundle? = Bundle()
//                        bundle!!.putString("demandId", commoditySubmitDemandBean.data)
//                        bundle.putString("imageUrl",sceneData.imageDetailUrl)
//                        bundle.putString("title", sceneData.productId)
//                        bundle.putString("time",sceneData.centerTime)
//                        delegate.arguments = bundle
//                        start(delegate)
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
            if (!isChecked){
                cb.isChecked = true
                isChecked = true
            }else{
                cb.isChecked = false
                isChecked = false
            }
        }

        fill_order_to_pay.setOnClickListener {
            val delegate: PayMethodDelegate = PayMethodDelegate().create()!!
            val bundle: Bundle? = Bundle()
            delegate.arguments = bundle
            start(delegate)
        }
    }

    fun initViews(viewDemandBean: ViewDemandBean) {
        Glide.with(context)
                .load(arguments.getString("imageUrl"))
                .into(fill_order_iv)
        fill_order_title_tv.text = arguments.getString("title")
        fill_order_time_tv.text = String.format("拍摄于 %s（北京时间）", arguments.getString("time"))
        fill_order_op_tv.text = viewDemandBean.data.demands[0].originalPrice
        fill_order_cp_tv.text = viewDemandBean.data.demands[0].salePrice
        fill_order_dp_tv.text = "0"
        fill_order_out_tv.text = viewDemandBean.data.demands[0].salePrice
    }
}