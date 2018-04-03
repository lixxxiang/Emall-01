package com.example.emall_ec.sign

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_up.*
import com.example.emall_core.net.RestClient
import com.example.emall_core.util.log.EmallLogger
import java.util.*
import android.graphics.Typeface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_ec.main.EcBottomDelegate
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*


/**
 * Created by lixiang on 2018/2/5.
 */
class SignUpDelegate : EmallDelegate() {

    var tel = String()
    var vCode = String()
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

//        val params : WeakHashMap<String, Any> ?= RestClient().PARAMS
//        params!!["name"] = "n"
//        params["userId"] = 123
//        params["avatar"] = "avatar"
//        params["address"] = "address"
//        params["gender"] = "gender"
//
//        btn_sign_up_submit.setOnClickListener{
//            if (tel.isEmpty()){
//                Toast.makeText(activity, getString(R.string.empty_tel), Toast.LENGTH_SHORT).show()
//            }else{
//                sendMessageParams!!["telephone"] = tel
//                RestClient().builder()
//                        .url("http://10.10.90.11:8099/global/mall/sendMessage.do")
//                        .success(object : ISuccess {
//                            override fun onSuccess(response: String) {
//
//                            }
//                        })
//                        .error(object : IError {
//                            override fun onError(code: Int, msg: String) {}
//                        })
//                        .failure(object : IFailure {
//                            override fun onFailure() {}
//                        })
//                        .build()
//                        .get()
//            }
//
//        }

        sign_up_vcode_et.addTextChangedListener(mTextWatcher)
        sign_up_count_down.setCountDownMillis(60000)
        sign_up_count_down.setOnClickListener {
            tel = sign_up_tel_et.text.toString()
            vCode = sign_up_vcode_et.text.toString()
            if (tel.isEmpty()) {
                /**
                 * empty tel
                 */
                Toast.makeText(activity, getString(R.string.empty_tel) + tel, Toast.LENGTH_SHORT).show()
            } else {
                /**
                 * tel ok
                 */
                if (RegexUtils.isMobileExact(tel)) {
                    /**
                     * tel is valid
                     */
                    sign_up_count_down.start()
                    getVCode()
                } else {
                    /**
                     * tel is invalid
                     */
                    Toast.makeText(activity, getString(R.string.wrong_tel) + tel, Toast.LENGTH_SHORT).show()
                }
            }
//            sign_in_by_tel_vcode_tv.visibility = View.VISIBLE
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

        btn_sign_up_submit.setOnClickListener {
            tel = sign_up_tel_et.text.toString()
            vCode = sign_up_vcode_et.text.toString()
            EmallLogger.d(tel)
            if (tel.isEmpty()) {
                /**
                 * empty tel
                 */
                Toast.makeText(activity, getString(R.string.empty_tel), Toast.LENGTH_SHORT).show()
            } else {
                /**
                 * tel ok
                 */
                if (RegexUtils.isMobileExact(tel)) {
                    /**
                     * tel is valid
                     */

                    if (vCode.isEmpty()) {
                        /**
                         * empty vcode
                         */
                        Toast.makeText(activity, getString(R.string.empty_vcode), Toast.LENGTH_SHORT).show()
                    } else {
                        /**
                         * tel ok & vcode ok
                         */
                        checkMessage()

                    }
                } else {
                    /**
                     * tel is invalid
                     */
                    Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkMessage() {
//        RestClient().builder()
//                .url("http://59.110.161.48:8023/global/mall/checkMessage.do")
//                .params(sendMessageParams!!)
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        checkMessageBean = Gson().fromJson(response, CheckMessageBean::class.java)
//                        if (checkMessageBean.meta == "success"){
//                            /**
//                             * success
//                             */
//                        }else{
//                            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                })
//                .error(object : IError {
//                    override fun onError(code: Int, msg: String) {}
//                })
//                .failure(object : IFailure {
//                    override fun onFailure() {}
//                })
//                .build()
//                .post()
        var i = "success"
        if (i == "success") {
            /**
             * success
             */
            EmallLogger.d("success")
            val delegate :SetPasswordDelegate = SetPasswordDelegate().create()!!
            val bundle = Bundle()
            bundle.putString("MODIFY_PASSWORD_TELEPHONE", tel)
            delegate.arguments = bundle
            start(delegate)
        } else {
            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
        }
    }


    private var mTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            EmallLogger.d("change")
            btn_sign_up_submit.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
            if (sign_up_vcode_et.text.toString() == "") {
                btn_sign_up_submit.setBackgroundResource(R.drawable.sign_up_btn_shape)
            }
        }
    }

    private fun getVCode() {
//        RestClient().builder()
//                .url("http://10.10.90.11:8099/global/mall/sendMessage.do")
//                .params(sendMessageParams!!)
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        sendMessageBean = Gson().fromJson(response, SendMessageBean::class.java)
//                        if (sendMessageBean.register == "0") {
//                            /**
//                             * unregister
//                             */
//                            Toast.makeText(activity, getString(R.string.not_register), Toast.LENGTH_SHORT).show()
//                        } else {
//                            /**
//                             * registered
//                             */
//
//                        }
//                    }
//                })
//                .error(object : IError {
//                    override fun onError(code: Int, msg: String) {}
//                })
//                .failure(object : IFailure {
//                    override fun onFailure() {}
//                })
//                .build()
//                .post()

        var i = "1"
        if (i == "0") {
            /**
             * unregister
             */
            Toast.makeText(activity, getString(R.string.not_register), Toast.LENGTH_SHORT).show()
        } else {
            /**
             * registered
             */
            showHint()

        }
    }

    private fun showHint() {
        sign_up_vcode_tv.text = String.format("已向手机%s发送验证码",hideTel())
        sign_up_vcode_tv.visibility = View.VISIBLE
    }

    private fun hideTel(): String {
        return String.format("%s****%s", tel.substring(0, 4), tel.substring(7, 11))
    }
}