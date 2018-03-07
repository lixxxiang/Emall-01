package com.example.emall_ec.main.order

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.order.state.data.OrderDetail
import kotlinx.android.synthetic.main.delegate_order_detail.*
import kotlinx.android.synthetic.main.delegate_order_list.*

/**
 * Created by lixiang on 2018/3/6.
 */
class OrderDetailDelegate :  BottomItemDelegate() {

    fun create(): OrderDetailDelegate? {
        return OrderDetailDelegate()
    }

    override fun initial() {
        order_detail_tel_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/tel.ttf")
        order_detail_qq_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/qq.ttf")
        order_detail_list_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(order_detail_list_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var orderData = arguments.getParcelable<OrderDetail>("KEy")
        Log.d("fdf", orderData.data[0].details.imageDetailUrl)


    }

    override fun setLayout(): Any? {
        return R.layout.delegate_order_detail
    }

}