package com.example.emall_ec.main.index.dailypic.pic

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/3/29.
 */
class Page1Delegate : BottomItemDelegate() {
    var contentId = ""
    fun create(contentId: String): Page1Delegate? {
        this.contentId = contentId
        return Page1Delegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_page_1
    }

    override fun initial() {
        EmallLogger.d(contentId)
    }
}