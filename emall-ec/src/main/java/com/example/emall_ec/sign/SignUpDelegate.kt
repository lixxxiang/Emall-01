package com.example.emall_ec.sign

import android.app.Activity
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_up.*
import com.orhanobut.logger.Logger.json
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator.params
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.util.log.EmallLogger
import java.util.*
import java.util.logging.LogManager


/**
 * Created by lixiang on 2018/2/5.
 */
class SignUpDelegate : EmallDelegate() {

    override fun setLayout(): Any? {
        return R.layout.delegate_sign_up
    }


    override fun initial() {
        val params : WeakHashMap<String, Any> ?= RestClient().PARAMS
        params!!["name"] = "n"
        params["userId"] = 123
        params["avatar"] = "avatar"
        params["address"] = "address"
        params["gender"] = "gender"

        btn_sign_up_getVCode.setOnClickListener{
            /**
             * 获取验证码
             */
             EmallLogger.d("get verify code")
        }

        btn_sign_up_submit.setOnClickListener {
            startWithPop(SetPasswordDelegate())
        }
    }
}