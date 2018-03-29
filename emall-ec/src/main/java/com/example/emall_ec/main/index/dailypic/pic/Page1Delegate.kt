package com.example.emall_ec.main.index.dailypic.pic

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/3/29.
 */
class Page1Delegate : BottomItemDelegate() {
    fun create(): Page1Delegate? {
        return Page1Delegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_page_1
    }

    override fun initial() {

    }
}