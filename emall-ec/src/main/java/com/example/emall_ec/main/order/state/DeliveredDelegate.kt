package com.example.emall_ec.main.order.state

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.progressbar.EmallProgressBar
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_all.*
import kotlinx.android.synthetic.main.delegate_delivered.*
import java.util.*

/**
 * Created by lixiang on 2018/3/5.
 */
class DeliveredDelegate : EmallDelegate() {
    private var orderDetail = OrderDetail()
    private var data: MutableList<OrderDetail>? = mutableListOf()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()

    override fun setLayout(): Any? {
        return R.layout.delegate_delivered
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        EmallProgressBar.showProgressbar(context)

        data()
        delivered_lv.setOnItemClickListener { adapterView, view, i, l ->

            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            bundle!!.putParcelable("KEy", orderDetail)
            delegate.arguments = bundle
//            start(delegate)
//            showHideFragment(OrderDetailDelegate(), getParentDelegate())
//            getParentDelegate.start(OrderDetailDelegate())
            (parentFragment as BottomItemDelegate).start(delegate)

        }
    }

    fun data() {

        findOrderListByUserIdParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        findOrderListByUserIdParams!!["state"] = "4"
        findOrderListByUserIdParams!!["type"] = ""
        EmallLogger.d(findOrderListByUserIdParams!!["userId"]!!)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findOrderListByUserId")
                .params(findOrderListByUserIdParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
                        if (orderDetail.data.isEmpty()) {
                            delivered_lv.visibility = View.INVISIBLE
                            delivered_rl.visibility = View.VISIBLE
                        } else {
                            data!!.add(orderDetail)
                            initRefreshLayout()
                            val head = View.inflate(activity, R.layout.orderlist_head_view, null)
                            delivered_lv.addHeaderView(head)
                            delivered_lv.adapter = OrderListAdapter(activity, data, R.layout.item_order)
                        }
                    }
                })
                .build()
                .get()
    }

    fun initRefreshLayout() {
        delivered_srl.setColorSchemeColors(Color.parseColor("#b80017"))
    }

}