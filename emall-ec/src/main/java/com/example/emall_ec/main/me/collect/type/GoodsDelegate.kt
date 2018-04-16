package com.example.emall_ec.main.me.collect.type

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class GoodsDelegate :EmallDelegate() {

    fun create(): GoodsDelegate?{
        return GoodsDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_goods
    }

    override fun initial() {
    }
}