//package com.example.emall_ec.main.order.state
//
//import android.graphics.Color
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.support.annotation.RequiresApi
//import android.support.v4.widget.SwipeRefreshLayout
//import android.view.View
//import android.widget.AbsListView
//import android.widget.AdapterView
//import com.example.emall_core.util.log.EmallLogger
//import com.example.emall_ec.R
//import com.example.emall_ec.database.DatabaseManager
//import com.example.emall_ec.main.bottom.BottomItemDelegate
//import com.example.emall_ec.main.classify.data.fuckOthers.ApiService
//import com.example.emall_ec.main.classify.data.fuckOthers.NetUtils
//import com.example.emall_ec.main.order.OrderDetailDelegate
//import com.example.emall_ec.main.order.state.adapter.All2ListAdapter
//import com.example.emall_ec.main.order.state.adapter.AllListAdapter
//import com.example.emall_ec.main.order.state.data.OrderDetail
//import kotlinx.android.synthetic.main.delegate_all_2.*
//import retrofit2.Retrofit
//import java.util.*
//
//class All2Delegate : BottomItemDelegate(), AdapterView.OnItemClickListener {
//    private var orderDetail = OrderDetail()
//    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()
//    var inited = false
//    var adapter: AllListAdapter? = null
//    var delegate: All2Delegate? = null
//    internal var retrofit: Retrofit? = null
//    internal var apiService: ApiService? = null
//    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//
//    }
//
//
//    override fun setLayout(): Any? {
//        return R.layout.delegate_all_2
//    }
//
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    override fun initial() {
//        setSwipeBackEnable(false)
//        initRefreshLayout()
//        all_srl.isRefreshing = true
////        all_rv.addHeaderView(View.inflate(activity, R.layout.orderlist_head_view, null))
//        delegate = this
//        data()
//
////        all_rv.setOnItemClickListener { adapterView, view, i, l ->
////            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
////            val bundle: Bundle? = Bundle()
////            bundle!!.putString("KEY", "ID")
////            bundle.putParcelable("KEy", orderDetail)
////            bundle.putInt("INDEX", i - 1)
////            delegate.arguments = bundle
////            (parentFragment as BottomItemDelegate).start(delegate)
////        }
//
////        all_rv.setOnScrollListener(object : AbsListView.OnScrollListener {
////            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
////
////            }
////
////            override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
////                val firstView = view.getChildAt(firstVisibleItem)
////                all_srl.isEnabled = firstVisibleItem == 0 && (firstView == null || firstView.top == 0)
////            }
////        })
//
////        all_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
////            adapter = null
////            all_srl.isRefreshing = true
////            Handler().postDelayed({
////                data()
////                all_srl.isRefreshing = false
////            }, 1200)
////        })
//    }
//
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    fun data() {
//        retrofit = NetUtils.getRetrofit()
//        apiService = retrofit!!.create(ApiService::class.java)
//        val call = apiService!!.findOrderListByUserId(DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, "", "")
//        call.enqueue(object : retrofit2.Callback<OrderDetail> {
//            override fun onResponse(call: retrofit2.Call<OrderDetail>, response: retrofit2.Response<OrderDetail>) {
//                if (response.body() != null) {
//                    orderDetail = response.body()!!
//                    EmallLogger.d(response)
//                    inited = true
//                    val data: MutableList<OrderDetail>? = mutableListOf()
//                    if (all_rv != null && all_rl != null) {
//                        if (orderDetail.data.isEmpty()) {
//                            all_rv.visibility = View.INVISIBLE
//                            all_rl.visibility = View.VISIBLE
//                            all_srl.isRefreshing = false
//
//                        } else {
//                            all_rv.visibility = View.VISIBLE
//                            all_rl.visibility = View.GONE
//
//                            data!!.add(orderDetail)
//                            adapter = All2ListAdapter(delegate, data, R.layout.item_order, context)
//                            all_rv.adapter = adapter
//                            all_srl.isRefreshing = false
//                        }
//                    }
//                } else {
//                    EmallLogger.d("error")
//                    all_srl.isRefreshing = false
//
//                }
//            }
//
//            override fun onFailure(call: retrofit2.Call<OrderDetail>, throwable: Throwable) {}
//        })
//    }
//
//    fun initRefreshLayout() {
//        all_srl.setColorSchemeColors(Color.parseColor("#b80017"))
//    }
//
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    override fun onSupportVisible() {
//        super.onSupportVisible()
////        all_rv.visibility = View.INVISIBLE
//        data()
//    }
//
//}