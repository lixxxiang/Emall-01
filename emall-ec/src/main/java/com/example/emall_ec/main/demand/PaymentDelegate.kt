package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_pay_method.*
import kotlinx.android.synthetic.main.delegate_payment.*

class PaymentDelegate : EmallDelegate() {
    fun create(): PaymentDelegate? {
        return PaymentDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_payment
    }

    override fun initial() {
        payment_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(payment_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        payment_toolbar.setNavigationOnClickListener {
            pop()
        }
    }
}