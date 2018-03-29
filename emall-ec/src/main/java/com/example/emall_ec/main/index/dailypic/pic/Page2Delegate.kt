package com.example.emall_ec.main.index.dailypic.pic

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/3/29.
 */
class Page2Delegate : BottomItemDelegate() {
    fun create(): Page2Delegate? {
        return Page2Delegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_page_2
    }

    override fun initial() {
    }
}