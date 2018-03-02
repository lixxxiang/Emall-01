package com.example.emall_ec.sign

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_set_password.*
import kotlinx.android.synthetic.main.delegate_sign_up.*
import java.util.*

/**
 * Created by lixiang on 14/02/2018.
 */
class SetPasswordDelegate : EmallDelegate(){
    override fun setLayout(): Any? {
        return R.layout.delegate_set_password
    }

    override fun initial() {
        set_password_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        set_password_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")

        val params : WeakHashMap<String, Any>?= RestClient().PARAMS
        btn_set_password_submit.setOnClickListener{
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