package com.example.emall_ec.main.coupon.type

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class UsedCouponDelegate  : EmallDelegate() {

    fun create(): UsedCouponDelegate? {
        return UsedCouponDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_used_coupon
    }

    override fun initial() {
    }
}