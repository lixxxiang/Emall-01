package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.R.id.pay_method_toolbar
import kotlinx.android.synthetic.main.delegate_pay_method.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * Created by lixiang on 2018/3/27.
 */
class PayMethodDelegate : BottomItemDelegate() {
    fun create(): PayMethodDelegate?{
        return PayMethodDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_pay_method
    }

    override fun initial() {
        pay_method_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(pay_method_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        rl.setOnClickListener {
            radioButton.isChecked = true
        }

        rl2.setOnClickListener {
            radioButton2.isChecked = true
        }

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}