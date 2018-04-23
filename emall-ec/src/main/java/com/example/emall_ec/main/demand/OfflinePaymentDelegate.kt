package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegat_offline_payment.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class OfflinePaymentDelegate : BottomItemDelegate() {

    fun create(): OfflinePaymentDelegate?{
        return OfflinePaymentDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegat_offline_payment
    }

    override fun initial() {
        offline_toolbar.title = "线下支付"
        (activity as AppCompatActivity).setSupportActionBar(offline_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        offline_toolbar.setNavigationOnClickListener {
            pop()
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}