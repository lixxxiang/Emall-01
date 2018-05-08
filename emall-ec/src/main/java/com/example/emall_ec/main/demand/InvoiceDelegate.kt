package com.example.emall_ec.main.demand

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_invoice.*
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.BottomDialog
import com.smarttop.library.widget.OnAddressSelectedListener
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.support.design.widget.Snackbar
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.demand.data.QueryInvoiceBean
import com.example.emall_ec.main.demand.data.UpdateInvoiceBean
import com.google.gson.Gson
import me.yokeyword.fragmentation.ISupportFragment
import java.util.*


/**
 * Created by lixiang on 2018/3/28.
 */
class InvoiceDelegate : BottomItemDelegate(), View.OnClickListener, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener {
    private var dialog: BottomDialog? = null
    private var provinceCode: String? = null
    private var cityCode: String? = null
    private var countyCode: String? = null
    private var streetCode: String? = null
    private var provincePosition: Int = 0
    private var cityPosition: Int = 0
    private var countyPosition: Int = 0
    private var streetPosition: Int = 0
    private var updateInvoiceParams: WeakHashMap<String, Any>? = WeakHashMap()
    private var queryInvoiceParams: WeakHashMap<String, Any>? = WeakHashMap()
    private var queryInvoiceBean = QueryInvoiceBean()
    private var updateInvoiceBean = UpdateInvoiceBean()
    private var flag = "false"
    override fun onClick(p0: View?) {

    }

    override fun onAddressSelected(province: com.smarttop.library.bean.Province?, city: com.smarttop.library.bean.City?, county: com.smarttop.library.bean.County?, street: com.smarttop.library.bean.Street?) {
        provinceCode = if (province == null) "" else province.code
        cityCode = if (city == null) "" else city.code
        countyCode = if (county == null) "" else county.code
        streetCode = if (street == null) "" else street.code
        val s = (if (province == null) "" else province.name) + (if (city == null) "" else city.name) + (if (county == null) "" else county.name) +
                if (street == null) "" else street.name
        invoice_cities_picker_tv.text = s
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    fun create(): InvoiceDelegate? {
        return InvoiceDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_invoice
    }


    override fun dialogclose() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun selectorAreaPosition(provincePosition: Int, cityPosition: Int, countyPosition: Int, streetPosition: Int) {
        this.provincePosition = provincePosition
        this.cityPosition = cityPosition
        this.countyPosition = countyPosition
        this.streetPosition = streetPosition
    }

    override fun initial() {
        invoice_toolbar.title = getString(R.string.invoice_title)
        (activity as AppCompatActivity).setSupportActionBar(invoice_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        invoice_toolbar.setNavigationOnClickListener {
            if (!title.text.toString().isEmpty()
                    && !invoice_id.text.toString().isEmpty()
                    && !address.text.toString().isEmpty()
                    && !tel.text.toString().isEmpty()
                    && !bank.text.toString().isEmpty()
                    && !account.text.toString().isEmpty()
                    && !name.text.toString().isEmpty()
                    && !phone.text.toString().isEmpty()
                    && !address_detail.text.toString().isEmpty()
                    && !invoice_cities_picker_tv.text.isEmpty()) {
                flag = "true"
            }
            val bundle = Bundle()
            bundle.putString("flag", flag)
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }
//        invoice_back_iv.setOnClickListener {
//            pop()
//        }



        tel.inputType = EditorInfo.TYPE_CLASS_PHONE
        account.inputType = EditorInfo.TYPE_CLASS_PHONE
        phone.inputType = EditorInfo.TYPE_CLASS_PHONE

        invoice_price.text = String.format("¥%s", arguments.getString("INVOICE_PRICE"))

        getInvoice()
        invoice_cities_picker.setOnClickListener {
            if (dialog != null) {
                dialog!!.show()
            } else {
                dialog = BottomDialog(activity)
                dialog!!.setOnAddressSelectedListener(this)
                dialog!!.setDialogDismisListener(this)
                dialog!!.setTextSize(14F)
                dialog!!.setIndicatorBackgroundColor(R.color.dark_gray)
                dialog!!.setTextSelectedColor(R.color.dark_red)
                dialog!!.setTextUnSelectedColor(R.color.dark_gray)
                dialog!!.setSelectorAreaPositionListener(this)
                dialog!!.show()
            }
        }

        invoice_save_btn.setOnClickListener {
            KeyboardUtils.hideSoftInput(activity)
            if (!title.text.toString().isEmpty()
                    && !invoice_id.text.toString().isEmpty()
                    && !address.text.toString().isEmpty()
                    && !tel.text.toString().isEmpty()
                    && !bank.text.toString().isEmpty()
                    && !account.text.toString().isEmpty()
                    && !name.text.toString().isEmpty()
                    && !phone.text.toString().isEmpty()
                    && !address_detail.text.toString().isEmpty()
                    && !invoice_cities_picker_tv.text.isEmpty()) {
                flag = "true"
                updateInvoice()

            } else {
                val snackBar = Snackbar.make(view!!, getString(R.string.entire_invoice_hint), Snackbar.LENGTH_INDEFINITE)
                snackBar.setAction(getString(R.string.confirm_2), { snackBar.dismiss() })
                snackBar.show()
            }
        }

    }

    private fun getInvoice() {
        queryInvoiceParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/query/invoice")
                .params(queryInvoiceParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        queryInvoiceBean = Gson().fromJson(response, QueryInvoiceBean::class.java)
                        EmallLogger.d(response)
                        if (queryInvoiceBean.message == "success") {
                            /**
                             * success
                             */
                            title.setText(queryInvoiceBean.data.title.toCharArray(), 0, queryInvoiceBean.data.title.length)
                            invoice_id.setText(queryInvoiceBean.data.payerNumber.toCharArray(), 0, queryInvoiceBean.data.payerNumber.length)
                            address.setText(queryInvoiceBean.data.registerAddress.toCharArray(), 0, queryInvoiceBean.data.registerAddress.length)
                            tel.setText(queryInvoiceBean.data.registerTel.toCharArray(), 0, queryInvoiceBean.data.registerTel.length)
                            bank.setText(queryInvoiceBean.data.depositBank.toCharArray(), 0, queryInvoiceBean.data.depositBank.length)
                            account.setText(queryInvoiceBean.data.bankAccount.toCharArray(), 0, queryInvoiceBean.data.bankAccount.length)
                            name.setText(queryInvoiceBean.data.receiver.toCharArray(), 0, queryInvoiceBean.data.receiver.length)
                            phone.setText(queryInvoiceBean.data.mobilePhone.toCharArray(), 0, queryInvoiceBean.data.mobilePhone.length)
                            address_detail.setText(queryInvoiceBean.data.fullAddress.toCharArray(), 0, queryInvoiceBean.data.fullAddress.length)
                            invoice_cities_picker_tv.setText(queryInvoiceBean.data.location.toCharArray(), 0, queryInvoiceBean.data.location.length)
                        }
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .build()
                .post()
    }

    private fun updateInvoice() {
        updateInvoiceParams!!["title"] = title.text.toString()
        updateInvoiceParams!!["payerNumber"] = invoice_id.text.toString()
        updateInvoiceParams!!["registerAddress"] = address.text.toString()
        updateInvoiceParams!!["registerTel"] = tel.text.toString()
        updateInvoiceParams!!["depositBank"] = bank.text.toString()
        updateInvoiceParams!!["bankAccount"] = account.text.toString()
        updateInvoiceParams!!["receiver"] = name.text.toString()
        updateInvoiceParams!!["mobilePhone"] = phone.text.toString()
        updateInvoiceParams!!["fullAddress"] = address_detail.text.toString()
        updateInvoiceParams!!["location"] = invoice_cities_picker_tv.text.toString()
        updateInvoiceParams!!["invoiceAmount"] = arguments.getString("INVOICE_PRICE")
        updateInvoiceParams!!["invoiceType"] = "1"
        updateInvoiceParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/update/invoice")
                .params(updateInvoiceParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        updateInvoiceBean = Gson().fromJson(response, UpdateInvoiceBean::class.java)
                        EmallLogger.d(response)
                        if (updateInvoiceBean.message == "success") {
                            /**
                             * success
                             */
                            Snackbar.make(view!!, "保存成功", Snackbar.LENGTH_SHORT).show()
                            flag = "true"
                        }
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .build()
                .post()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        if (!title.text.toString().isEmpty()
                && !invoice_id.text.toString().isEmpty()
                && !address.text.toString().isEmpty()
                && !tel.text.toString().isEmpty()
                && !bank.text.toString().isEmpty()
                && !account.text.toString().isEmpty()
                && !name.text.toString().isEmpty()
                && !phone.text.toString().isEmpty()
                && !address_detail.text.toString().isEmpty()
                && !invoice_cities_picker_tv.text.isEmpty()) {
            flag = "true"
        }
        val bundle = Bundle()
        bundle.putString("flag", flag)
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
    }
}