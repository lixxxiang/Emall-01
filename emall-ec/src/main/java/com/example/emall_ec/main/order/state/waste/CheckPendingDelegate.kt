package com.example.emall_ec.main.order.state.waste

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
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.api.ApiService
import com.example.emall_ec.api.NetUtils
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.example.emall_ec.main.order.state.adapter.CheckPendingListAdapter
import com.example.emall_ec.main.order.state.data.OrderDetail
import kotlinx.android.synthetic.main.delegate_check_pending.*
import retrofit2.Retrofit
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
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_check_pending
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        setSwipeBackEnable(false)
        delegate = this
        val head = View.inflate(activity, R.layout.orderlist_head_view, null)
        check_pending_lv.addHeaderView(head)
        data()
        check_pending_lv.setOnItemClickListener { adapterView, view, i, l ->
            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            bundle.putParcelable("KEy", orderDetail)
            bundle.putInt("INDEX", i - 1)
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
//        EmallLogger.d("daishenhe")
//        findOrderListByUserIdParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
//        findOrderListByUserIdParams!!["state"] = "0"
//        findOrderListByUserIdParams!!["type"] = ""
//        RestClient().builder()
//                .url("http://59.110.164.214:8024/global/order/findOrderListByUserId")
//                .params(findOrderListByUserIdParams!!)
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        EmallLogger.d(response)
//                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
//                        if (orderDetail.data.isEmpty()) {
//                            check_pending_lv.visibility = View.INVISIBLE
//                            check_pending_rl.visibility = View.VISIBLE
//                        } else {
//                            check_pending_lv.visibility = View.VISIBLE
//                            check_pending_rl.visibility = View.INVISIBLE
//                            data!!.add(orderDetail)
//                            initRefreshLayout()
//                            adapter = CheckPendingListAdapter(delegate, data, R.layout.item_order, context)
//                            check_pending_lv.adapter = adapter
//                        }
//                    }
//                })
//                .error(object : IError {
//                    override fun onError(code: Int, msg: String) {
//                        EmallLogger.d(msg)
//                    }
//                })
//                .failure(object : IFailure {
//                    override fun onFailure() {
//                        EmallLogger.d("fail")
//                    }
//                })
//                .build()
//                .get()

        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.findOrderListByUserId(DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, "0", "")
        call.enqueue(object : retrofit2.Callback<OrderDetail> {
            override fun onResponse(call: retrofit2.Call<OrderDetail>, response: retrofit2.Response<OrderDetail>) {
                if (response.body() != null) {
                    orderDetail = response.body()!!
                    val data: MutableList<OrderDetail>? = mutableListOf()
                    if (orderDetail.data.isEmpty()) {
                        check_pending_lv.visibility = View.INVISIBLE
                        check_pending_rl.visibility = View.VISIBLE
                    } else {
                        check_pending_lv.visibility = View.VISIBLE
                        check_pending_rl.visibility = View.INVISIBLE
                        data!!.add(orderDetail)
                        initRefreshLayout()
                        adapter = CheckPendingListAdapter(delegate, data, R.layout.item_order, context)
                        check_pending_lv.adapter = adapter
                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderDetail>, throwable: Throwable) {}
        })
    }

    fun initRefreshLayout() {
        check_pending_srl.setColorSchemeColors(Color.parseColor("#b80017"))
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        data()
    }

}