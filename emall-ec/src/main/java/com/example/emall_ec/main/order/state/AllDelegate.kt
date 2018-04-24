package com.example.emall_ec.main.order.state

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AdapterView
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.bottom.BaseBottomDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.progressbar.EmallProgressBar
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.example.emall_ec.main.order.OrderListDelegate
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_all.*
import me.yokeyword.fragmentation.SupportFragment
import java.util.*
import android.widget.AbsListView
import com.example.emall_ec.main.classify.data.SceneClassifyAdapter
import kotlinx.android.synthetic.main.delegate_optics1.*


/**
 * Created by lixiang on 2018/3/5.
 */
class AllDelegate : BottomItemDelegate(), AdapterView.OnItemClickListener {
    private var orderDetail = OrderDetail()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var adapter: OrderListAdapter? = null
    var inited = false
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }


    override fun setLayout(): Any? {
        return R.layout.delegate_all
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        all_lv.addHeaderView(View.inflate(activity, R.layout.orderlist_head_view, null))
        data()
        all_lv.setOnItemClickListener { adapterView, view, i, l ->
            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            bundle.putParcelable("KEy", orderDetail)
            bundle.putInt("INDEX", i - 1)
            delegate.arguments = bundle
            (parentFragment as BottomItemDelegate).start(delegate)
        }

        all_lv.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val firstView = view.getChildAt(firstVisibleItem)
                all_srl.isEnabled = firstVisibleItem == 0 && (firstView == null || firstView.top == 0)
            }
        })

        all_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            adapter = null
            all_srl.isRefreshing = true
            Handler().postDelayed({
                data()
                all_srl.isRefreshing = false
            }, 1200)
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun data() {
        if (!inited)
            EmallProgressBar.showProgressbar(context)

        findOrderListByUserIdParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        findOrderListByUserIdParams!!["state"] = ""
        findOrderListByUserIdParams!!["type"] = ""
        EmallLogger.d(findOrderListByUserIdParams!!)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findOrderListByUserId")
                .params(findOrderListByUserIdParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        inited = true
                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
                        val data: MutableList<OrderDetail>? = mutableListOf()

                        if (orderDetail.data.isEmpty()) {
                            all_lv.visibility = View.INVISIBLE
                            all_rl.visibility = View.VISIBLE
                            EmallProgressBar.hideProgressbar()
                        } else {
                            data!!.add(orderDetail)
                            initRefreshLayout()
                            adapter = OrderListAdapter(activity, data, R.layout.item_order)
                            all_lv.adapter = adapter
                            EmallProgressBar.hideProgressbar()

                        }
                    }
                })
                .build()
                .get()
    }

    fun initRefreshLayout() {
        all_srl.setColorSchemeColors(Color.parseColor("#b80017"))
    }


}