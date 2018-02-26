package com.example.emall_ec.main.detail

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/2/26.
 */
class GoodsDetailDelegate : BottomItemDelegate() {

    fun create(): GoodsDetailDelegate? {
        return GoodsDetailDelegate()
    }
    override fun initial() {

    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods_detail
    }
}