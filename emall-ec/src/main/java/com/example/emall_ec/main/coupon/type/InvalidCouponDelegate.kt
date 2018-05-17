package com.example.emall_ec.main.coupon.type

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class InvalidCouponDelegate  : EmallDelegate() {

    fun create(): InvalidCouponDelegate? {
        return InvalidCouponDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_invalid_coupon
    }

    override fun initial() {
    }
}