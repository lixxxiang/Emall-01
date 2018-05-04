package com.example.emall_ec.main.me.feedback

import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_my_opinion.*

class MyOpinionDelegate : EmallDelegate() {

    fun create(): MyOpinionDelegate? {
        return MyOpinionDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_my_opinion
    }

    override fun initial() {
        my_opinion_edt.isFocusable = true
        my_opinion_edt.isFocusableInTouchMode = true
        my_opinion_edt.requestFocus()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        KeyboardUtils.showSoftInput(activity)
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }
}