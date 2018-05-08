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
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.classify.data.fuckOthers.ApiService
import com.example.emall_ec.main.classify.data.fuckOthers.NetUtils
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.example.emall_ec.main.order.state.adapter.CheckPendingListAdapter
import com.example.emall_ec.main.order.state.adapter.ObligationListAdapter
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_check_pending.*
import kotlinx.android.synthetic.main.delegate_obligation.*
import retrofit2.Retrofit
import java.util.*

/**
 * Created by lixiang on 2018/3/5.
 */
class ObligationDelegate : EmallDelegate() {
    private var orderDetail = OrderDetail()
    private var data: MutableList<OrderDetail>? = mutableListOf()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var inited = false
    var adapter: ObligationListAdapter? = null
    var delegate: ObligationDelegate? = null
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_obligation
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        setSwipeBackEnable(false)
        delegate = this
        data()
        obligation_lv.addHeaderView(View.inflate(activity, R.layout.orderlist_head_view, null))

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
        }

        obligation_lv.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val firstView = view.getChildAt(firstVisibleItem)
                obligation_srl.isEnabled = firstVisibleItem == 0 && (firstView == null || firstView.top == 0)
            }
        })

        obligation_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            adapter = null
            obligation_srl.isRefreshing = true
            Handler().postDelayed({
                data()
                obligation_srl.isRefreshing = false
            }, 1200)
        })
    }


    fun data() {

//        findOrderListByUserIdParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
//        findOrderListByUserIdParams!!["state"] = "2"
//        findOrderListByUserIdParams!!["type"] = ""
//        RestClient().builder()
//                .url("http://59.110.164.214:8024/global/order/findOrderListByUserId")
//                .params(findOrderListByUserIdParams!!)
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
//                        if (orderDetail.data.isEmpty()) {
//                            obligation_lv.visibility = View.INVISIBLE
//                            obligation_rl.visibility = View.VISIBLE
//
//                        } else {
//                            data!!.add(orderDetail)
//                            initRefreshLayout()
//                            val head = View.inflate(activity, R.layout.orderlist_head_view, null)
//                            obligation_lv.addHeaderView(head)
//                            obligation_lv.adapter = ObligationListAdapter(delegate, data, R.layout.item_order, activity)
//
//                        }
//                    }
//                })
//                .build()
//                .get()

        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.findOrderListByUserId(DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, "2", "")
        call.enqueue(object : retrofit2.Callback<OrderDetail> {
            override fun onResponse(call: retrofit2.Call<OrderDetail>, response: retrofit2.Response<OrderDetail>) {
                if (response.body() != null && obligation_lv !=null && obligation_rl != null) {
                    orderDetail = response.body()!!
                    if (orderDetail.data.isEmpty()) {
                        obligation_lv.visibility = View.INVISIBLE
                        obligation_rl.visibility = View.VISIBLE
                    } else {
                        obligation_lv.visibility = View.VISIBLE
                        obligation_rl.visibility = View.INVISIBLE
                        data!!.add(orderDetail)
                        initRefreshLayout()
                        obligation_lv.adapter = ObligationListAdapter(delegate, data, R.layout.item_order, activity)

                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderDetail>, throwable: Throwable) {}
        })
    }

    fun initRefreshLayout() {
        if(obligation_srl != null){
            obligation_srl.setColorSchemeColors(Color.parseColor("#b80017"))
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSupportVisible() {
        super.onSupportVisible()
//        all_lv.visibility = View.INVISIBLE
        data()
    }

}