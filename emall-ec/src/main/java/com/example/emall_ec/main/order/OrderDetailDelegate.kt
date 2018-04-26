package com.example.emall_ec.main.order

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.order.state.adapter.AllListAdapter
import com.example.emall_ec.main.order.state.data.OrderDetail
import kotlinx.android.synthetic.main.delegate_order_detail.*

/**
 * Created by lixiang on 2018/3/6.
 */
class OrderDetailDelegate : BottomItemDelegate() {

    fun create(): OrderDetailDelegate? {
        return OrderDetailDelegate()
    }

    override fun initial() {
        order_detail_tel_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/tel.ttf")
        order_detail_qq_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/qq.ttf")
        order_detail_list_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(order_detail_list_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val orderData = arguments.getParcelable<OrderDetail>("KEy")
        val index = arguments.getInt("INDEX")
        initViews(orderData, index)
        order_detail_list_toolbar.setNavigationOnClickListener {
            pop()
        }

    }

    private fun initViews(orderData: OrderDetail, index: Int) {
        Glide.with(context)
                .load(orderData.data[index].details.imageDetailUrl)
                .into(order_detail_image_iv)

//        order_detail.setText(String.format(context.getString(R.string.orderId), dataList.get(0).getData().get(i).getOrderId()))
        order_detail_title_tv.text = AllListAdapter.typeArray[orderData.data[index].type]
        order_detail_time_tv.text = AllListAdapter.timeFormat(orderData.data.get(index).details.centerTime)
        order_detail_price_tv.text = String.format("¥%s", orderData.data.get(index).payment)
        order_detail_state_tv.text = stateFormat(orderData.data.get(index).state, orderData.data[index].planCommitTime)
        order_detail_id_tv.text = orderData.data[index].orderId
        order_detail_order_time_tv.text = orderData.data[index].commitTime
        order_detail_pay_method_tv.text = AllListAdapter.payMethodArray[orderData.data[index].payMethod]
        order_detail_origional_price_tv.text = String.format("¥%s", orderData.data[index].details.originalPrice)
        order_detail_current_price_tv.text = String.format("¥%s", orderData.data[index].details.salePrice)
        order_detail_final_price_tv.text = String.format("¥%s", orderData.data[index].payment)
        order_detail_discount_tv.text = discount(orderData.data[index].details.salePrice, orderData.data[index].payment)
    }

    private fun discount(salePrice: String, payment: Double): String {
        return String.format("-¥%s",salePrice.toDouble() - payment)
    }

    private fun stateFormat(state: Int, planCommitTime: String): String {
        return if (state != 4) {
            String.format("%s：预计 %s 交付", AllListAdapter.stateArray[state], planCommitTime)
        } else {
            String.format("%s：已交付", AllListAdapter.stateArray[state])
        }
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_order_detail
    }

}