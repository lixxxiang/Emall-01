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
import kotlinx.android.synthetic.main.delegate_all.*
import kotlinx.android.synthetic.main.delegate_me.*
import kotlinx.android.synthetic.main.delegate_obligation.*
import java.util.*

/**
 * Created by lixiang on 2018/3/5.
 */
class ObligationDelegate : EmallDelegate(){
    private var orderDetail = OrderDetail()
    private var data: MutableList<OrderDetail>? = mutableListOf()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()

    override fun setLayout(): Any? {
        return R.layout.delegate_obligation
    }

    override fun initial() {
        data()
        obligation_lv.setOnItemClickListener { adapterView, view, i, l ->

            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            bundle!!.putParcelable("KEy", orderDetail)
            delegate.arguments = bundle
//            start(delegate)
//            showHideFragment(OrderDetailDelegate(), getParentDelegate())
//            getParentDelegate.start(OrderDetailDelegate())
            (parentFragment as BottomItemDelegate).start(delegate)

            theFourClick()
        }
    }

    private fun theFourClick() {
        me_obligation_rl.setOnClickListener {
            EmallLogger.d("Dfdfsdfs")
        }
    }


    fun data() {

        findOrderListByUserIdParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        findOrderListByUserIdParams!!["state"] = "2"
        findOrderListByUserIdParams!!["type"] = ""
        EmallLogger.d(findOrderListByUserIdParams!!["userId"]!!)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findOrderListByUserId")
                .params(findOrderListByUserIdParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
                        if (orderDetail.data.isEmpty()){
                            obligation_lv.visibility = View.INVISIBLE
                            obligation_rl.visibility = View.VISIBLE
                        }else {
                            data!!.add(orderDetail)
                            initRefreshLayout()
                            val head = View.inflate(activity, R.layout.orderlist_head_view, null)
                            obligation_lv.addHeaderView(head)
                            obligation_lv.adapter = OrderListAdapter(activity, data, R.layout.item_order)
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