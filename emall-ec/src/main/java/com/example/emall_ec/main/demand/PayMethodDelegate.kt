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
    var flag = 1
    fun create(): PayMethodDelegate? {
        return PayMethodDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_pay_method
    }

    override fun initial() {
        pay_method_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(pay_method_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pay_method_toolbar.setNavigationOnClickListener {
            pop()
        }

        pay_method_pay_rl.setOnClickListener {
            start(PaymentDelegate().create())
        }

        wechat_btn.setOnClickListener {
            if (flag != 1) {
                wechat_iv.setBackgroundResource(R.drawable.checked)
                public_iv.setBackgroundResource(R.drawable.unchecked)
                flag = 1
            }
        }

        public_btn.setOnClickListener {
            if (flag == 1) {
                wechat_iv.setBackgroundResource(R.drawable.unchecked)
                public_iv.setBackgroundResource(R.drawable.checked)
                flag = 2
            }
        }

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}