package com.example.emall_ec.main.order

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/3/5.
 */
class OrderListDelegate : BottomItemDelegate() {
    override fun initial() {

    }

    override fun setLayout(): Any? {
        return R.layout.delegate_order_list
    }
}