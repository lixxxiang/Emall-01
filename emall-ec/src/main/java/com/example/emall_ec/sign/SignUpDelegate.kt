package com.example.emall_ec.sign

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_up.*

/**
 * Created by lixiang on 2018/2/5.
 */
class SignUpDelegate : EmallDelegate() {
    override fun setLayout(): Any? {
        return R.layout.delegate_sign_up
    }

    override fun initial() {
        if (edit_sign_up_name.text.toString() != "dd")
    }
}