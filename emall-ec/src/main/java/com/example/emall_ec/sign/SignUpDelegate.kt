package com.example.emall_ec.sign

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_up.*
import com.example.emall_core.net.RestClient
import com.example.emall_core.util.log.EmallLogger
import java.util.*
import android.graphics.Typeface
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_ec.main.EcBottomDelegate


/**
 * Created by lixiang on 2018/2/5.
 */
class SignUpDelegate : EmallDelegate() {

    var tel = String()
    var sendMessageParams : WeakHashMap<String, Any>?= WeakHashMap()
    fun create(): SignUpDelegate?{
        return SignUpDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_sign_up
    }


    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        sign_up_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        sign_up_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")

        tel = sign_up_tel_et.text.toString()

        val params : WeakHashMap<String, Any> ?= RestClient().PARAMS
        params!!["name"] = "n"
        params["userId"] = 123
        params["avatar"] = "avatar"
        params["address"] = "address"
        params["gender"] = "gender"

        btn_sign_up_submit.setOnClickListener{
            if (tel.isEmpty()){
                Toast.makeText(activity, getString(R.string.empty_tel), Toast.LENGTH_SHORT).show()
            }else{
                sendMessageParams!!["telephone"] = tel
                RestClient().builder()
                        .url("http://10.10.90.11:8099/global/mall/sendMessage.do")
                        .success(object : ISuccess {
                            override fun onSuccess(response: String) {

                            }
                        })
                        .error(object : IError {
                            override fun onError(code: Int, msg: String) {}
                        })
                        .failure(object : IFailure {
                            override fun onFailure() {}
                        })
                        .build()
                        .get()
            }

        }

        sign_up_login_tv.setOnClickListener {
            if (tel.isEmpty()){
                Toast.makeText(activity, getString(R.string.empty_tel), Toast.LENGTH_SHORT).show()
            }else{

            }
            startWithPop(SignInByTelDelegate())
        }

        sign_up_close.setOnClickListener {
            startWithPop(EcBottomDelegate())
        }


    }
}