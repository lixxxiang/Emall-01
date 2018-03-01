package com.example.emall_ec.main.order

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.detail.GoodsDetailDelegate

/**
 * Created by lixiang on 2018/3/1.
 */
class OrderDelegate : BottomItemDelegate(){

    fun create(): OrderDelegate? {
        return OrderDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_order
    }

    override fun initial() {

    }

}