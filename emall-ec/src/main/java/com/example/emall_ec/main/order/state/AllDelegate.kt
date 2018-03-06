package com.example.emall_ec.main.order.state

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.file.FileUtil
import com.example.emall_ec.R
import com.example.emall_ec.main.order.state.data.Model
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.example.emall_ec.main.order.state.data.OrderDetailResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_all.*
import java.util.*

/**
 * Created by lixiang on 2018/3/5.
 */
class AllDelegate : EmallDelegate() {

    private var adapter: OrderAdapter? = null
    private var orderDetail = OrderDetail()
    private var datas: MutableList<Model>? = mutableListOf()

    private var data: MutableList<OrderDetail>? = mutableListOf()
    private var dataDetail: MutableList<OrderDetailResult>? = mutableListOf()


    override fun setLayout(): Any? {
        return R.layout.delegate_all
    }

    override fun initial() {
//        data("http://10.10.90.11:8086/global/order/findOrderListByUserId")
        val url: String = if (FileUtil.checkEmulator()) {
//            "http://10.0.2.2:3035/data"
            "http://10.10.90.11:8086/global/order/findOrderListByUserId"
        } else {
//            "http://192.168.1.36:3035/data"
            "http://10.10.90.11:8086/global/order/findOrderListByUserId"
        }
        data(url)

    }

    fun data(url: String) {

        var findOrderListByUserIdParams: WeakHashMap<String, Any>? = WeakHashMap()
        findOrderListByUserIdParams!!["userId"] = "92209410004772"

        RestClient().builder()
                .url(url)
                .params(findOrderListByUserIdParams)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {

                        orderDetail = Gson().fromJson(response, OrderDetail::class.java)
//                        var size = orderDetail.data.size
//                        for (i in 0 until size){
//                            val orderDetailResult = OrderDetailResult()
//                            orderDetailResult.data.imageDetailUrl = orderDetail.data[i].details.imageDetailUrl
//                            orderDetailResult.data.orderId = orderDetail.data[i].orderId
//                            orderDetailResult.data.payment = orderDetail.data[i].payment
//                            orderDetailResult.data.state = orderDetail.data[i].state
//                            orderDetailResult.data.type = orderDetail.data[i].type
//                            orderDetailResult.data.userId = orderDetail.data[i].userId
//                            dataDetail!!.add(orderDetailResult)
//                        }
                        data!!.add(orderDetail)

//                        var model = Model()
//
//                        model.imgUrl = "http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20161113092742_000015634_101_0009_001_L1_MSS.jpg"
//                        datas!!.add(model)
//
//                        var model2 = Model()
//                        model2.imgUrl = "http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20161221215447_000017023_105_0011_001_L1_MSS.jpg"
//                        datas!!.add(model2)
//
//                        var model3 = Model()
//                        model3.imgUrl = "http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20170823173205_100002070_102_001_L1B_MSS.jpg"
//                        datas!!.add(model3)

                        initRefreshLayout()
//                        initRecyclerView()
                        all_lv.adapter = OrderListAdapter(activity, data, R.layout.item_order)
                    }
                })
                .build()
                .get()
    }

    fun initRefreshLayout() {
        all_srl.setColorSchemeColors(Color.parseColor("#b80017"))
//        all_srl.setProgressViewOffset(true, 120, 300)
    }

//    private fun initRecyclerView() {
//        val manager = LinearLayoutManager(context)
//        manager.orientation = LinearLayoutManager.VERTICAL
//        all_rv.layoutManager = manager
//
////        adapter = OrderAdapter(R.layout.item_order, data
//        adapter = OrderAdapter(R.layout.item_order, dataDetail)
//
//        all_rv.adapter = adapter
//    }
}