package com.example.emall_ec.sign

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*
import kotlinx.android.synthetic.main.delegate_sign_up.*

/**
 * Created by lixiang on 2018/2/5.
 */
class SignInByTelDelegate : EmallDelegate(){
    override fun setLayout(): Any? {
        return R.layout.delegate_sign_in_by_tel
    }

    override fun initial() {
//        StatusBarCompat.setStatusBarColor(activity, Color.WHITE)
        sign_in_by_tel_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        sign_in_by_tel_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")

        sign_in_by_tel_get_vcode_btn.setOnClickListener {
            sign_in_by_tel_vcode_tv.visibility = View.VISIBLE
        }

        btn_sign_in_by_tel_submit.setOnClickListener{
            startWithPop(SignInByAccountDelegate())
        }

    }
}