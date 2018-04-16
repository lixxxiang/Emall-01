package com.example.emall_ec.main.me.collect.type

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class ContentDelegate :EmallDelegate() {

    fun create(): ContentDelegate?{
        return ContentDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_content
    }

    override fun initial() {
    }
}