package com.example.emall_ec.main.order.state

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AbsListView
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
import com.example.emall_ec.main.order.state.adapter.AllListAdapter
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.example.emall_ec.main.order.state.data.OrderListModel
import kotlinx.android.synthetic.main.delegate_all.*
import kotlinx.android.synthetic.main.delegate_all_2.*
import kotlinx.android.synthetic.main.delegate_fill_order.*
import kotlinx.android.synthetic.main.delegate_optics1.*
import me.yokeyword.fragmentation.ISupportFragment
import retrofit2.Retrofit
import java.util.*

class All2Delegate : BottomItemDelegate(), AdapterView.OnItemClickListener {
    private var orderDetail = OrderDetail()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()
    var inited = false
    var adapter: All2RecyclerViewAdapter? = null
    var delegate: All2Delegate? = null
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    var allLinearLayoutManager: LinearLayoutManager? = null
    private var lastPosition = 0//位置
    private var lastOffset = 0//偏移量
    var mSharedPreferences: SharedPreferences? = null
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }


    override fun setLayout(): Any? {
        return R.layout.delegate_all_2
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        setSwipeBackEnable(false)
        mSharedPreferences = activity.getSharedPreferences("BACK_FROM", Context.MODE_PRIVATE)

        initRefreshLayout()
        all2_srl.isRefreshing = true
        delegate = this
        allLinearLayoutManager = LinearLayoutManager(context)
        allLinearLayoutManager!!.isSmoothScrollbarEnabled = true
        allLinearLayoutManager!!.isAutoMeasureEnabled = true
        all2_rv.layoutManager = allLinearLayoutManager
        all2_rv.setHasFixedSize(true)
        all2_rv.addItemDecoration(GridSpacingItemDecoration(1, 30, true))

        all2_rv.isNestedScrollingEnabled = false
        data()

        all2_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            adapter = null
            all2_srl.isRefreshing = true
            Handler().postDelayed({
                data()
                all2_srl.isRefreshing = false
            }, 1200)
        })


        all2_rv.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
//                getPositionAndOffset()

            }
        })


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun data() {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.findOrderListByUserId(DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, "", "")
        call.enqueue(object : retrofit2.Callback<OrderDetail> {
            override fun onResponse(call: retrofit2.Call<OrderDetail>, response: retrofit2.Response<OrderDetail>) {
                if (response.body() != null) {
                    orderDetail = response.body()!!
                    EmallLogger.d(response)
                    inited = true
                    val data: MutableList<OrderListModel>? = mutableListOf()
                    if (all2_rv != null && all2_rl != null) {
                        if (orderDetail.data.isEmpty()) {
                            all2_rv.visibility = View.INVISIBLE
                            all2_rl.visibility = View.VISIBLE
                            if (all_srl != null)
                                all_srl.isRefreshing = false

                        } else {
                            all2_rv.visibility = View.VISIBLE
                            all2_rl.visibility = View.GONE
                            val size = orderDetail.data.size
                            for (i in 0 until size) {
                                var orderListModel = OrderListModel()
                                orderListModel.planCommitTime = orderDetail.data[i].planCommitTime
                                orderListModel.orderId = orderDetail.data[i].orderId
                                orderListModel.type = orderDetail.data[i].type
                                orderListModel.startTime = orderDetail.data[i].details.startTime
                                orderListModel.centerTime = orderDetail.data[i].details.centerTime
                                orderListModel.payment = orderDetail.data[i].payment
                                orderListModel.state = orderDetail.data[i].state
                                orderListModel.imageDetailUrl = orderDetail.data[i].details.imageDetailUrl
                                orderListModel.productType = orderDetail.data[i].details.productType
                                data!!.add(orderListModel)

                            }
                            adapter = All2RecyclerViewAdapter(R.layout.item_order, data)
                            all2_rv.adapter = adapter
                            all2_srl.isRefreshing = false
                            adapter!!.setOnItemChildClickListener { adapter, view, position ->
                                EmallLogger.d(position)
                                EmallLogger.d(orderDetail.data[position].state)
                                if (orderDetail.data[position].state == 2) {
                                    val payMethodDelegate = PayMethodDelegate().create()
                                    val bundle = Bundle()
                                    bundle.putString("PARENT_ORDER_ID", orderDetail.data[position].orderId)
                                    bundle.putString("TYPE", "2")
                                    bundle.putString("PAGE_FROM", "ORDER_LIST")
                                    payMethodDelegate!!.arguments = bundle
                                    delegate!!.getParentDelegate<EmallDelegate>().start(payMethodDelegate)

                                } else if (orderDetail.data[position].state == 4) {
                                    val productDeliveryDelegate = ProductDeliveryDelegate().create()
                                    val bundle: Bundle? = Bundle()
                                    bundle!!.putString("PAGE_FROM", "ORDER_LIST")
                                    productDeliveryDelegate!!.arguments = bundle
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
                                (parentFragment as BottomItemDelegate).startForResult(delegate, 1)
                            }

                        }
                    }
                } else {
                    EmallLogger.d("error")
                    all2_srl.isRefreshing = false

                }
            }

            override fun onFailure(call: retrofit2.Call<OrderDetail>, throwable: Throwable) {}
        })
    }

//    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
//        super.onFragmentResult(requestCode, resultCode, data)
//        EmallLogger.d(data!!.getString("BACK_FROM"))
//        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK) {
//            val index = data!!.getString("BACK_FROM")
//            if (index == "ORDER_DETAIL"){
//                EmallLogger.d("fdfdfdf")
//            }
//        }
//    }

    fun initRefreshLayout() {
        all2_srl.setColorSchemeColors(Color.parseColor("#b80017"))
    }


//    override fun onPause() {
//        super.onPause()
//        getPositionAndOffset()
//    }

//    private fun getPositionAndOffset() {
//        var topView = allLinearLayoutManager!!.getChildAt(0)
//        lastOffset = topView.top
//        lastPosition = allLinearLayoutManager!!.getPosition(topView)
//        val editor = mSharedPreferences!!.edit()
//        editor.putInt("lastOffset", lastOffset)
//        editor.putInt("lastPosition", lastPosition)
//        editor.commit()
//    }

//    override fun onResume() {
//        super.onResume()
//        scrollToPosition()
//    }

//    private fun scrollToPosition() {
//        val sp = activity.getSharedPreferences("POSITION", Context.MODE_PRIVATE)
//        sp.getInt("lastOffset", 0)
//        sp.getInt("lastPosition", 0)
//        if (all2_rv.layoutManager != null && lastPosition >= 0) {
//            (all2_rv.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(lastPosition, lastOffset)
//        }
//        sp.edit().clear().commit()
//    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSupportVisible() {
        super.onSupportVisible()
        val sp = activity.getSharedPreferences("BACK_FROM", Context.MODE_PRIVATE)
        EmallLogger.d(sp.getString("BACK_FROM", ""))
        if (sp.getString("BACK_FROM", "") != "ORDER_DETAIL"
                && sp.getString("BACK_FROM", "") != "PAY_METHOD"
                && sp.getString("BACK_FROM", "") != "DELIVERY"
                && sp.getString("BACK_FROM", "") != "OBLIGATION"
                && sp.getString("BACK_FROM", "") != "CHECK_PENDING"
                && sp.getString("BACK_FROM", "") != "DELIVERED"
                && sp.getString("BACK_FROM", "") != "ALL"
                && sp.getString("BACK_FROM", "") != "IN_PRODUCTION"
        ) {
            all2_srl.isRefreshing = true
            data()
        }
        sp.edit().clear().commit()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        val editor = mSharedPreferences!!.edit()
        editor.putString("BACK_FROM", "ALL")
        editor.commit()
    }
}