package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_invoice.*
import kotlinx.android.synthetic.main.delegate_pay_method.*

/**
 * Created by lixiang on 2018/3/28.
 */
class InvoiceDelegate : BottomItemDelegate() {
    fun create(): InvoiceDelegate? {
        return InvoiceDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_invoice
    }

    override fun initial() {
        invoice_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(invoice_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tel.inputType = EditorInfo.TYPE_CLASS_PHONE
        account.inputType = EditorInfo.TYPE_CLASS_PHONE
        phone.inputType = EditorInfo.TYPE_CLASS_PHONE
    }
}