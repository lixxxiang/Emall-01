package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_fill_order.*
import kotlinx.android.synthetic.main.delegate_order_list.*

/**
 * Created by lixiang on 2018/3/27.
 */
class FillOrderDelegate : BottomItemDelegate(){
    fun create(): FillOrderDelegate?{
        return FillOrderDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_fill_order
    }

    override fun initial() {
        fill_order_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(fill_order_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}