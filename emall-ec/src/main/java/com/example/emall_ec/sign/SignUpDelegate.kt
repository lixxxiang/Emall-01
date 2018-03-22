package com.example.emall_ec.sign

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_up.*
import com.example.emall_core.net.RestClient
import com.example.emall_core.util.log.EmallLogger
import java.util.*
import android.graphics.Typeface
import android.graphics.Color


/**
 * Created by lixiang on 2018/2/5.
 */
class SignUpDelegate : EmallDelegate() {


    fun create(): SignUpDelegate?{
        return SignUpDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_sign_up
    }


    override fun initial() {
//        StatusBarCompat.setStatusBarColor(activity, Color.WHITE)
        sign_up_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        sign_up_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        val params : WeakHashMap<String, Any> ?= RestClient().PARAMS
        params!!["name"] = "n"
        params["userId"] = 123
        params["avatar"] = "avatar"
        params["address"] = "address"
        params["gender"] = "gender"

        btn_sign_up_submit.setOnClickListener{
            /**
             * 获取验证码
             */
             EmallLogger.d("get verify code")
        }

        btn_sign_up_submit.setOnClickListener {
            startWithPop(SetPasswordDelegate())
        }

        sign_up_login_tv.setOnClickListener {
            println("dd")
            startWithPop(SignInByTelDelegate())
        }


    }
}