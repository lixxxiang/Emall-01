package com.example.emall_ec.main.order.state

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.AdapterView
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

/**
 * Created by lixiang on 2018/3/5.
 */
class AllDelegate : BottomItemDelegate(), AdapterView.OnItemClickListener {
    private var orderDetail = OrderDetail()
    private var data: MutableList<OrderDetail>? = mutableListOf()
    var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }


    override fun setLayout(): Any? {
        return R.layout.delegate_all
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        EmallProgressBar.showProgressbar(context)
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
    }

    fun data() {

        findOrderListByUserIdParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        findOrderListByUserIdParams!!["state"] = ""
        findOrderListByUserIdParams!!["type"] = ""
        EmallLogger.d(findOrderListByUserIdParams!!["userId"]!!)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/order/findOrderListByUserId")
                .params(findOrderListByUserIdParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
                        if (orderDetail.data.isEmpty()){
                            all_lv.visibility = View.INVISIBLE
                            all_rl.visibility = View.VISIBLE
                            EmallProgressBar.hideProgressbar()

                        }else{
                            data!!.add(orderDetail)
                            initRefreshLayout()
                            all_lv.addHeaderView(View.inflate(activity, R.layout.orderlist_head_view, null))
                            all_lv.adapter = OrderListAdapter(activity, data, R.layout.item_order)
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