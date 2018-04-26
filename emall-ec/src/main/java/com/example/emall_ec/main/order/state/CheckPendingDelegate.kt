package com.example.emall_ec.main.order.state

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AbsListView
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.example.emall_ec.main.order.state.adapter.AllListAdapter
import com.example.emall_ec.main.order.state.adapter.CheckPendingListAdapter
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_check_pending.*
import kotlinx.android.synthetic.main.delegate_obligation.*
import java.util.*

/**
 * Created by lixiang on 2018/3/5.
 */
class CheckPendingDelegate : EmallDelegate() {
    private var orderDetail = OrderDetail()
    private var data: MutableList<OrderDetail>? = mutableListOf()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var adapter: CheckPendingListAdapter? = null
    var delegate: CheckPendingDelegate? = null

    override fun setLayout(): Any? {
        return R.layout.delegate_check_pending
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        delegate = this
        val head = View.inflate(activity, R.layout.orderlist_head_view, null)
        check_pending_lv.addHeaderView(head)
        data()
        check_pending_lv.setOnItemClickListener { adapterView, view, i, l ->

            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            bundle.putParcelable("KEy", orderDetail)
            delegate.arguments = bundle
            (parentFragment as BottomItemDelegate).start(delegate)

        }

        check_pending_lv.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val firstView = view.getChildAt(firstVisibleItem)
                check_pending_srl.isEnabled = firstVisibleItem == 0 && (firstView == null || firstView.top == 0)
            }
        })

        check_pending_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            adapter = null
            check_pending_srl.isRefreshing = true
            Handler().postDelayed({
                data()
                check_pending_srl.isRefreshing = false
            }, 1200)
        })
    }

    fun data() {

        findOrderListByUserIdParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        findOrderListByUserIdParams!!["state"] = "0"
        findOrderListByUserIdParams!!["type"] = ""
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findOrderListByUserId")
                .params(findOrderListByUserIdParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
                        if (orderDetail.data.isEmpty()) {
                            check_pending_lv.visibility = View.INVISIBLE
                            check_pending_rl.visibility = View.VISIBLE
                        } else {
                            data!!.add(orderDetail)
                            initRefreshLayout()
                            adapter = CheckPendingListAdapter(delegate, data, R.layout.item_order, context)
                            check_pending_lv.adapter = adapter
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