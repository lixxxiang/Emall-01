package com.example.emall_ec.main.coupon.type

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class EnableCouponDelegate : EmallDelegate() {

    fun create(): EnableCouponDelegate? {
        return EnableCouponDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_enable_coupon
    }

    override fun initial() {
    }
}