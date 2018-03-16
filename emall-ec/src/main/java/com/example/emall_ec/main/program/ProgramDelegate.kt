package com.example.emall_ec.main.program

import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_order_list.*
import kotlinx.android.synthetic.main.delegate_program.*

/**
 * Created by lixiang on 2018/3/16.
 */
class ProgramDelegate : BottomItemDelegate() {
    override fun setLayout(): Any? {
        return R.layout.delegate_program
    }

    override fun initial() {
//        program_toolbar.title = ""
//        (activity as AppCompatActivity).setSupportActionBar(program_toolbar)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}