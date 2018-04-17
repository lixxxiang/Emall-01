package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_invoice.*
import com.smarttop.library.utils.LogUtil
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.BottomDialog
import com.smarttop.library.widget.OnAddressSelectedListener


/**
 * Created by lixiang on 2018/3/28.
 */
class InvoiceDelegate : BottomItemDelegate(), View.OnClickListener, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener{
    private var dialog: BottomDialog? = null
    private var provinceCode: String? = null
    private var cityCode: String? = null
    private var countyCode: String? = null
    private var streetCode: String? = null
    private var provincePosition: Int = 0
    private var cityPosition: Int = 0
    private var countyPosition: Int = 0
    private var streetPosition: Int = 0

    override fun onClick(p0: View?) {

    }

    override fun onAddressSelected(province: com.smarttop.library.bean.Province?, city: com.smarttop.library.bean.City?, county: com.smarttop.library.bean.County?, street: com.smarttop.library.bean.Street?) {
        provinceCode = if (province == null) "" else province.code
        cityCode = if (city == null) "" else city.code
        countyCode = if (county == null) "" else county.code
        streetCode = if (street == null) "" else street.code
        LogUtil.d("数据", "省份id=$provinceCode")
        LogUtil.d("数据", "城市id=$cityCode")
        LogUtil.d("数据", "乡镇id=$countyCode")
        LogUtil.d("数据", "街道id=$streetCode")
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
        if(dialog!=null){
            dialog!!.dismiss()
        }
    }

    override fun selectorAreaPosition(provincePosition: Int, cityPosition: Int, countyPosition: Int, streetPosition: Int) {
        this.provincePosition = provincePosition
        this.cityPosition = cityPosition
        this.countyPosition = countyPosition
        this.streetPosition = streetPosition
        LogUtil.d("数据", "省份位置=$provincePosition")
        LogUtil.d("数据", "城市位置=$cityPosition")
        LogUtil.d("数据", "乡镇位置=$countyPosition")
        LogUtil.d("数据", "街道位置=$streetPosition")
    }

    override fun initial() {
//        invoice_toolbar.title = ""
//        (activity as AppCompatActivity).setSupportActionBar(invoice_toolbar)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//        invoice_toolbar.setNavigationOnClickListener {
//            pop()
//        }
        invoice_back_iv.setOnClickListener {
            pop()
        }


        tel.inputType = EditorInfo.TYPE_CLASS_PHONE
        account.inputType = EditorInfo.TYPE_CLASS_PHONE
        phone.inputType = EditorInfo.TYPE_CLASS_PHONE

        invoice_price.text = String.format("¥%s", arguments.getString("INVOICE_PRICE"))

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


    }


}