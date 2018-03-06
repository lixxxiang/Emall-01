package com.example.emall_ec.main.order.state

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.order.OrderDelegate
import com.example.emall_ec.main.order.OrderListDelegate
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.example.emall_ec.sign.SignUpDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_all.*
import kotlinx.android.synthetic.main.delegate_classify.*
import java.util.*

/**
 * Created by lixiang on 2018/3/5.
 */
class AllDelegate : BottomItemDelegate(), AdapterView.OnItemClickListener {
    private var orderDetail = OrderDetail()
    private var data: MutableList<OrderDetail>? = mutableListOf()

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }


    override fun setLayout(): Any? {
        return R.layout.delegate_all
    }

    override fun initial() {
//        data("http://10.10.90.11:8086/global/order/findOrderListByUserId")
        val url: String = if (FileUtil.checkEmulator()) {
            "http://10.0.2.2:3035/data"
//            "http://10.10.90.11:8086/global/order/findOrderListByUserId"
        } else {
//            "http://192.168.1.36:3035/data"
            "http://10.10.90.11:8086/global/order/findOrderListByUserId"
        }
        data(url)

        all_lv.setOnItemClickListener { adapterView, view, i, l ->

            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
            val bundle : Bundle ?= Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            start(delegate)
        }
    }

    fun data(url: String) {

        var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()
        findOrderListByUserIdParams!!["userId"] = "92209410004772"

        RestClient().builder()
                .url(url)
                .params(findOrderListByUserIdParams)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {

                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
                        data!!.add(orderDetail)
                        initRefreshLayout()
                        all_lv.adapter = OrderListAdapter(activity, data, R.layout.item_order)
                    }
                })
                .build()
                .get()
    }

    fun initRefreshLayout() {
        all_srl.setColorSchemeColors(Color.parseColor("#b80017"))
    }
}