package com.example.emall_ec.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_set_password.*

/**
 * Created by lixiang on 14/02/2018.
 */
class SetPasswordDelegate : EmallDelegate(){
    override fun setLayout(): Any? {
        return R.layout.delegate_set_password
    }

    override fun initial() {
        btn_sign_password_delegate_submit.setOnClickListener{
            startWithPop(SetUserNameDelegate())
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}