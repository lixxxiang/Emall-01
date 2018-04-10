package com.example.emall_ec.main.sign

import android.graphics.Typeface
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.sign.data.CheckMessageBean
import com.example.emall_ec.main.sign.data.SendMessageBean
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*
import java.util.*
import android.text.Editable
import android.text.TextWatcher
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.SoftKeyboardListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_sign_up.*
import android.app.Activity
import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate


/**
 * Created by lixiang on 2018/2/5.
 */
class SignInByTelDelegate : BottomItemDelegate() {

    var tel = String()
    var vCode = String()
    var flag1 = false
    var flag2 = false
    var sendMessageBean = SendMessageBean()
    var checkMessageBean = CheckMessageBean()
    var sendMessageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var checkMessageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var emptyToast: Toast? = null
    var wrongToast: Toast? = null
    var wrongVcodeToast: Toast? = null
    private var mISignListener: ISignListener? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ISignListener) {
            mISignListener = activity
        }
    }

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
//        sign_in_by_tel_tel_et.requestFocus()

        sign_in_by_tel_vcode_et.addTextChangedListener(mVcodeTextWatcher)
        sign_in_by_tel_tel_et.addTextChangedListener(mTelTextWatcher)
        sign_in_by_tel_count_down.setCountDownMillis(60000)


        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (sign_in_by_tel_title_rl != null)
                    sign_in_by_tel_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                if (sign_in_by_tel_title_rl != null)
                    sign_in_by_tel_title_rl.visibility = View.VISIBLE
            }
        })


        sign_in_by_tel_count_down.setOnClickListener {
            tel = sign_in_by_tel_tel_et.text.toString()
            if (tel.isEmpty()) {
                /**
                 * empty tel
                 */
                if (emptyToast != null) {
                    emptyToast!!.setText(getString(R.string.empty_tel))
                    emptyToast!!.duration = Toast.LENGTH_SHORT
                    emptyToast!!.show()
                } else {
                    emptyToast = Toast.makeText(activity, getString(R.string.empty_tel), Toast.LENGTH_SHORT)
                    emptyToast!!.show()
                }
            } else {
                /**
                 * tel ok
                 */
                if (RegexUtils.isMobileExact(tel)) {
                    /**
                     * tel is valid
                     */
                    sign_in_by_tel_count_down.start()
                    getVCode(tel)
                } else {
                    /**
                     * tel is invalid
                     */
                    if (wrongToast != null) {
                        wrongToast!!.setText(getString(R.string.wrong_tel) + "1")
                        wrongToast!!.duration = Toast.LENGTH_SHORT
                        wrongToast!!.show()
                    } else {
                        wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel) + "1", Toast.LENGTH_SHORT)
                        wrongToast!!.show()
                    }
                }
            }
        }

        sign_in_by_tel_tel_et.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
            if (sign_up_tel_et != null) {
                if (!b) {
                    tel = sign_in_by_tel_tel_et.text.toString()
                    /**
                     * 不用验空 需验手机号是否有效
                     */
                    if (!RegexUtils.isMobileExact(tel)) {
                        if (wrongToast != null) {
                            wrongToast!!.setText(getString(R.string.wrong_tel) + "2")
                            wrongToast!!.duration = Toast.LENGTH_SHORT
                            wrongToast!!.show()
                        } else {
                            EmallLogger.d("shibushi")
                            wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel) + "2", Toast.LENGTH_SHORT)

                            wrongToast!!.show()
                        }
                    }
                }
            }
        }


        sign_in_by_tel_submit_btn.setOnClickListener {
            tel = sign_in_by_tel_tel_et.text.toString()
            vCode = sign_in_by_tel_vcode_et.text.toString()
            if (RegexUtils.isMobileExact(tel)) {
                checkMessage(tel, vCode)
            } else {
                if (wrongToast != null) {
                    wrongToast!!.setText(getString(R.string.wrong_tel) + "3")
                    wrongToast!!.duration = Toast.LENGTH_SHORT
                    wrongToast!!.show()
                } else {
                    wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel) + "3", Toast.LENGTH_SHORT)
                    wrongToast!!.show()
                }
            }
        }
        sign_in_by_tel_submit_btn.isClickable = false


        btn_sign_in_by_account_submit.setOnClickListener {
            startWithPop(SignInByAccountDelegate())
            KeyboardUtils.hideSoftInput(activity)
        }

        sign_in_by_tel_login_tv.setOnClickListener {
            startWithPop(SignUpDelegate())
            KeyboardUtils.hideSoftInput(activity)
        }

    }

    private var mTelTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            flag1 = true
            if (sign_in_by_tel_tel_et.text.toString() == "") {
                sign_in_by_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                sign_in_by_tel_submit_btn.isClickable = false
            }
            if (flag1 && flag2) {
                sign_in_by_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                sign_in_by_tel_submit_btn.isClickable = true
            }
        }
    }

    private var mVcodeTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            flag2 = true
            if (sign_in_by_tel_vcode_et.text.toString() == "") {
                sign_in_by_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                sign_in_by_tel_submit_btn.isClickable = false
            }

            if (flag1 && flag2) {
                sign_in_by_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                sign_in_by_tel_submit_btn.isClickable = true

            }
        }
    }


    private fun checkMessage(t: String, c: String) {
        checkMessageParams!!["telephone"] = t
        checkMessageParams!!["code"] = c
        RestClient().builder()
                .url("http://59.110.161.48:8023/global/mall/checkMessage.do")
                .params(checkMessageParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        checkMessageBean = Gson().fromJson(response, CheckMessageBean::class.java)
                        if (checkMessageBean.meta == "success") {
                            /**
                             * success
                             */
                            EmallLogger.d(response)

                            SignHandler().onSignIn(response, mISignListener!!)
                            val bundle = Bundle()
                            bundle.putString("USER_NAME", checkMessageBean.userInfo.username)
                            KeyboardUtils.hideSoftInput(activity)
//                            popTo(preFragment.javaClass, false)
                            pop()
                            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            setFragmentResult(RESULT_OK, bundle)


                        } else {
                            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .build()
                .post()

//        /**
//         * test
//         */
//        var i = "success"
//        if (i == "success") {
//            /**
//             * success
//             */
//            val bundle = Bundle()
//            bundle.putString("USER_NAME", "lixxx")
//            KeyboardUtils.hideSoftInput(activity)
//            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            setFragmentResult(RESULT_OK, bundle)
//            supportDelegate.pop()
//
////            popTo(preFragment.javaClass, false)
////            pop()
//
//        } else {
//            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
//        }
    }

    private fun getVCode(t: String) {
        sendMessageParams!!["telephone"] = t
        RestClient().builder()
                .url("http://59.110.161.48:8023/global/mall/sendMessage.do")
                .params(sendMessageParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        sendMessageBean = Gson().fromJson(response, SendMessageBean::class.java)
                        if (sendMessageBean.register == "0") {
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
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .build()
                .post()

        /**
         * test
         */
//        var i = "1"
//        if (i == "0") {
//            /**
//             * unregister
//             */
//            Toast.makeText(activity, getString(R.string.not_register), Toast.LENGTH_SHORT).show()
//        } else {
//            /**
//             * registered
//             */
//            showHint()
//
//        }
    }

    private fun showHint() {
        sign_in_by_tel_vcode_tv.text = String.format("已向手机%s发送验证码", hideTel())
        sign_in_by_tel_vcode_tv.visibility = View.VISIBLE
    }

    private fun hideTel(): String {
        return String.format("%s****%s", tel.substring(0, 4), tel.substring(7, 11))
    }
}