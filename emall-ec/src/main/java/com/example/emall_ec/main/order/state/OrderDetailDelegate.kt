package com.example.emall_ec.main.order.state

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/3/6.
 */
class OrderDetailDelegate : EmallDelegate() {

    fun create(): OrderDetailDelegate? {
        return OrderDetailDelegate()
    }

    override fun initial() {

    }

    override fun setLayout(): Any? {
        return R.layout.delegate_order_detail
    }
}