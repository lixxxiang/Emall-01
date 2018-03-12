package com.example.emall_ec.main.classify

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/3/12.
 */
class BaseClassifyDelegate : BottomItemDelegate() {
    override fun setLayout(): Any? {
        return R.layout.delegate_base_classify
    }

    override fun initial() {
    }
}