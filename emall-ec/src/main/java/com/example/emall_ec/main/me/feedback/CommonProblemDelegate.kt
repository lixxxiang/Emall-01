package com.example.emall_ec.main.me.feedback

import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class CommonProblemDelegate : EmallDelegate() {

    fun create(): CommonProblemDelegate? {
        return CommonProblemDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_common_problems
    }

    override fun initial() {

    }
}