package com.example.emall_ec.main.order.state

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_ec.main.classify.data.fuckOthers.ApiService
import com.example.emall_ec.main.classify.data.fuckOthers.NetUtils
import com.example.emall_ec.main.demand.PayMethodDelegate
import com.example.emall_ec.main.order.OrderDetailDelegate
import com.example.emall_ec.main.order.ProductDeliveryDelegate
import com.example.emall_ec.main.order.state.adapter.All2RecyclerViewAdapter
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.example.emall_ec.main.order.state.data.OrderListModel
import kotlinx.android.synthetic.main.delegate_check_pending.*
import kotlinx.android.synthetic.main.delegate_check_pending_2.*
import retrofit2.Retrofit
import java.util.*

class CheckPending2Delegate : BottomItemDelegate(), AdapterView.OnItemClickListener {
    private var orderDetail = OrderDetail()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var inited = false
    var adapter: All2RecyclerViewAdapter? = null
    var delegate: CheckPending2Delegate? = null
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    var check_pendingLinearLayoutManager : LinearLayoutManager?= null
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }


    override fun setLayout(): Any? {
        return R.layout.delegate_check_pending_2
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        setSwipeBackEnable(false)

        initRefreshLayout()
        check_pending2_srl.isRefreshing = true
        delegate = this
        check_pendingLinearLayoutManager = LinearLayoutManager(context)
        check_pendingLinearLayoutManager!!.isSmoothScrollbarEnabled = true
        check_pendingLinearLayoutManager!!.isAutoMeasureEnabled = true
        check_pending2_rv.layoutManager = check_pendingLinearLayoutManager
        check_pending2_rv.setHasFixedSize(true)
        check_pending2_rv.addItemDecoration(GridSpacingItemDecoration(1, 30, true))

        check_pending2_rv.isNestedScrollingEnabled = false
        data()

        check_pending2_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            adapter = null
            check_pending2_srl.isRefreshing = true
            Handler().postDelayed({
                data()
                check_pending2_srl.isRefreshing = false
            }, 1200)
        })

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun data() {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.findOrderListByUserId(DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, "0", "")
        call.enqueue(object : retrofit2.Callback<OrderDetail> {
            override fun onResponse(call: retrofit2.Call<OrderDetail>, response: retrofit2.Response<OrderDetail>) {
                if (response.body() != null) {
                    orderDetail = response.body()!!
                    EmallLogger.d(response)
                    inited = true
                    val data: MutableList<OrderListModel>? = mutableListOf()
                    if (check_pending2_rv != null && check_pending2_rl != null) {
                        if (orderDetail.data.isEmpty()) {
                            check_pending2_rv.visibility = View.INVISIBLE
                            check_pending2_rl.visibility = View.VISIBLE
                            check_pending2_srl.isRefreshing = false

                        } else {
                            check_pending2_rv.visibility = View.VISIBLE
                            check_pending2_rl.visibility = View.GONE
                            val size = orderDetail.data.size
                            for (i in 0 until size) {
                                var orderListModel = OrderListModel()
                                orderListModel.orderId = orderDetail.data[i].orderId
                                orderListModel.type = orderDetail.data[i].type
                                orderListModel.startTime = orderDetail.data[i].details.startTime
                                orderListModel.centerTime = orderDetail.data[i].details.centerTime
                                orderListModel.payment = orderDetail.data[i].payment
                                orderListModel.state = orderDetail.data[i].state
                                orderListModel.imageDetailUrl = orderDetail.data[i].details.imageDetailUrl
                                orderListModel.productType = orderDetail.data[i].details.productType
//                                orderListModel.type = orderDetail.data[i].type
//                                orderListModel.type = orderDetail.data[i].type
//                                orderListModel.type = orderDetail.data[i].type
//                                orderListModel.type = orderDetail.data[i].type
//                                orderListModel.type = orderDetail.data[i].type
//                                orderListModel.type = orderDetail.data[i].type
//                                orderListModel.type = orderDetail.data[i].type
//                                orderListModel.type = orderDetail.data[i].type
//                                orderListModel.type = orderDetail.data[i].type

                                data!!.add(orderListModel)

                            }
                            adapter = All2RecyclerViewAdapter(R.layout.item_order, data)
                            check_pending2_rv.adapter = adapter
                            check_pending2_srl.isRefreshing = false
                            adapter!!.setOnItemChildClickListener { adapter, view, position ->
                                EmallLogger.d(position)
                                EmallLogger.d(orderDetail.data[position].getState())
                                if (orderDetail.data[position].getState() == 2) {
                                    val payMethodDelegate = PayMethodDelegate().create()
                                    val bundle = Bundle()
                                    bundle.putString("PARENT_ORDER_ID", orderDetail.data[position].getOrderId())
                                    bundle.putString("TYPE", "2")
                                    bundle.putString("PAGE_FROM", "ORDER_LIST")

                                    payMethodDelegate!!.arguments = bundle
                                    delegate!!.getParentDelegate<EmallDelegate>().start(payMethodDelegate)

                                } else if (orderDetail.data[position].getState() == 4) {
                                    val productDeliveryDelegate = ProductDeliveryDelegate().create()
                                    delegate!!.getParentDelegate<EmallDelegate>().start(productDeliveryDelegate)
                                }
                            }

                            adapter!!.setOnItemClickListener { adapter, view, position ->
                                val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
                                val bundle: Bundle? = Bundle()
                                bundle!!.putString("KEY", "ID")
                                bundle.putParcelable("KEy", orderDetail)
                                bundle.putInt("INDEX", position)
                                delegate.arguments = bundle
                                (parentFragment as BottomItemDelegate).start(delegate)
                            }

                        }
                    }
                } else {
                    EmallLogger.d("error")
                    check_pending2_srl.isRefreshing = false

                }
            }

            override fun onFailure(call: retrofit2.Call<OrderDetail>, throwable: Throwable) {}
        })
    }

    fun initRefreshLayout() {
        check_pending2_srl.setColorSchemeColors(Color.parseColor("#b80017"))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSupportVisible() {
        super.onSupportVisible()
        data()
    }


}