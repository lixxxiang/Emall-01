package com.example.emall_ec.sign

import android.graphics.Typeface
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.sign.data.CheckMessageBean
import com.example.emall_ec.sign.data.SendMessageBean
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*
import java.util.*
import android.text.Editable
import android.text.TextWatcher


/**
 * Created by lixiang on 2018/2/5.
 */
class SignInByTelDelegate : EmallDelegate() {

    var tel = String()
    var vCode = String()
    var sendMessageBean = SendMessageBean()
    var checkMessageBean = CheckMessageBean()
    var sendMessageParams: WeakHashMap<String, Any>? = WeakHashMap()

    fun create(): SignInByTelDelegate? {
        return SignInByTelDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_sign_in_by_tel
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        sign_in_by_tel_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        sign_in_by_tel_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")

        sign_in_by_tel_tel_et.addTextChangedListener(mTextWatcher)

        sign_in_by_tel_get_vcode_btn.setOnClickListener {
            tel = sign_in_by_tel_tel_et.text.toString()
            vCode = sign_in_by_tel_vcode_et.text.toString()
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

        sign_in_by_tel_submit_btn.setOnClickListener {
            //            startWithPop(SignInByAccountDelegate())
            tel = sign_in_by_tel_tel_et.text.toString()
            vCode = sign_in_by_tel_vcode_et.text.toString()
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
            sign_in_by_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
            if (sign_in_by_tel_tel_et.text.toString() == "") {
                sign_in_by_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
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
        } else {
            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
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
        sign_in_by_tel_vcode_tv.text = String.format("已向手机%s发送验证码",hideTel())
        sign_in_by_tel_vcode_tv.visibility = View.VISIBLE
    }

    private fun hideTel(): String {
        return String.format("%s****%s", tel.substring(0, 4), tel.substring(7, 11))
    }
}