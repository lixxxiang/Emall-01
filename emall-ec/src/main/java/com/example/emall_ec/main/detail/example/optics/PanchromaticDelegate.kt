package com.example.emall_ec.main.detail.example.optics

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class PanchromaticDelegate : EmallDelegate() {

    fun create(): PanchromaticDelegate?{
        return PanchromaticDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_panchromatic
    }

    override fun initial() {
        setSwipeBackEnable(false)
    }
}