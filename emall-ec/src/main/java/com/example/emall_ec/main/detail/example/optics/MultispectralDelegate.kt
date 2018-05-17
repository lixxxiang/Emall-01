package com.example.emall_ec.main.detail.example.optics

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class MultispectralDelegate : EmallDelegate() {

    fun create(): MultispectralDelegate?{
        return MultispectralDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_multispectral
    }

    override fun initial() {
        setSwipeBackEnable(false)
    }
}