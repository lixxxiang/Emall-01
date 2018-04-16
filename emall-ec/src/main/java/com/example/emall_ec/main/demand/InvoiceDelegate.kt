package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_invoice.*
import kotlinx.android.synthetic.main.delegate_pay_method.*
import chihane.jdaddressselector.BottomDialog
import chihane.jdaddressselector.OnAddressSelectedListener
import chihane.jdaddressselector.model.City
import chihane.jdaddressselector.model.County
import chihane.jdaddressselector.model.Province
import chihane.jdaddressselector.model.Street


/**
 * Created by lixiang on 2018/3/28.
 */
class InvoiceDelegate : BottomItemDelegate(), OnAddressSelectedListener {
    override fun onAddressSelected(p0: Province?, p1: City?, p2: County?, p3: Street?) {
        val s = (if (p0 == null) "" else p0.name) +
                (if (p1 == null) "" else "\n" + p1.name) +
                (if (p2 == null) "" else "\n" + p2.name) +
                if (p3 == null) "" else "\n" + p3.name
        EmallLogger.d(s)
    }

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

        invoice_toolbar.setNavigationOnClickListener {
            pop()
        }
        tel.inputType = EditorInfo.TYPE_CLASS_PHONE
        account.inputType = EditorInfo.TYPE_CLASS_PHONE
        phone.inputType = EditorInfo.TYPE_CLASS_PHONE

        invoice_price.text = String.format("¥%s", arguments.getString("INVOICE_PRICE"))

        invoice_cities_picker.setOnClickListener {
            val dialog = BottomDialog(activity)
            dialog.setOnAddressSelectedListener(this)
            dialog.show()
        }
    }
}