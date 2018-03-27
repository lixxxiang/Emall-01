package com.example.emall_ec.main.detail

import com.example.emall_core.delegates.bottom.BaseBottomDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/3/27.
 */
class PayMethodDelegate : BottomItemDelegate() {
    fun create(): PayMethodDelegate?{
        return PayMethodDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_pay_method
    }

    override fun initial() {
    }
}