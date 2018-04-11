package com.example.emall_ec.main.order.state

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_in_production.*
import kotlinx.android.synthetic.main.delegate_obligation.*
import java.util.*

/**
 * Created by lixiang on 2018/3/5.
 */
class InProductionDelegate  : EmallDelegate(){

    private var orderDetail = OrderDetail()
    private var data: MutableList<OrderDetail>? = mutableListOf()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()


    override fun setLayout(): Any? {
        return R.layout.delegate_in_production
    }

    override fun initial() {
        data()
        in_production_lv.setOnItemClickListener { adapterView, view, i, l ->

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
        findOrderListByUserIdParams!!["state"] = "3"
        findOrderListByUserIdParams!!["type"] = ""
        EmallLogger.d(findOrderListByUserIdParams!!["userId"]!!)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findOrderListByUserId")
                .params(findOrderListByUserIdParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
                        if (orderDetail.data.isEmpty()){
                            in_production_lv.visibility = View.INVISIBLE
                            in_production_rl.visibility = View.VISIBLE
                        }else {
                            data!!.add(orderDetail)
                            initRefreshLayout()
                            val head = View.inflate(activity, R.layout.orderlist_head_view, null)
                            in_production_lv.addHeaderView(head)
                            in_production_lv.adapter = OrderListAdapter(activity, data, R.layout.item_order)
                        }
                    }
                })
                .build()
                .get()
    }

    fun initRefreshLayout() {
        obligation_srl.setColorSchemeColors(Color.parseColor("#b80017"))
    }

}