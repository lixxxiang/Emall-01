package com.example.emall_ec.main.sign

import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_up.*
import com.example.emall_core.util.log.EmallLogger
import java.util.*
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.view.SoftKeyboardListener
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.sign.data.CheckMessageBean
import com.example.emall_ec.main.sign.data.SendMessageBean
import com.google.gson.Gson
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * Created by lixiang on 2018/2/5.
 */
class SignUpDelegate : BottomItemDelegate() {

    var tel = String()
    var vCode = String()
    var sendMessageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var checkMessageParams: WeakHashMap<String, Any>? = WeakHashMap()

    var flag1 = false
    var flag2 = false
    var emptyToast: Toast? = null
    var wrongToast: Toast? = null
    var wrongVcodeToast: Toast? = null
    var checkMessageBean = CheckMessageBean()
    var sendMessageBean = SendMessageBean()
    private val REQ_MODIFY_FRAGMENT = 100

    fun create(): SignUpDelegate? {
        return SignUpDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_sign_up
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        sign_up_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        sign_up_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
//        sign_up_tel_et.requestFocus()
        sign_up_close.setOnClickListener {
            pop()
        }

        sign_up_tel_et.addTextChangedListener(mTelTextWatcher)
        sign_up_vcode_et.addTextChangedListener(mVcodeTextWatcher)
        sign_up_count_down.setCountDownMillis(60000)

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (sign_up_title_rl != null)
                    sign_up_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                if (sign_up_title_rl != null)
                    sign_up_title_rl.visibility = View.VISIBLE
            }
        })
        /**
         * 验证码点击事件
         */
        sign_up_count_down.setOnClickListener {
            tel = sign_up_tel_et.text.toString()
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
                    sign_up_count_down.start()
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


        /**
         * 手机号码 失去焦点事件
         */
        sign_up_tel_et.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
            if (sign_up_tel_et != null) {
                if (!b) {
                    tel = sign_up_tel_et.text.toString()
                    /**
                     * 不用验空 需验手机号是否有效
                     */
                    if (!RegexUtils.isMobileExact(tel)) {
                        if (wrongToast != null) {
                            wrongToast!!.setText(getString(R.string.wrong_tel) + "2")
                            wrongToast!!.duration = Toast.LENGTH_SHORT
                            wrongToast!!.show()
                        } else {
                            wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel) + "2", Toast.LENGTH_SHORT)
                            wrongToast!!.show()
                        }
                    }
                }
            }
        }

        sign_up_login_tv.setOnClickListener {
            KeyboardUtils.hideSoftInput(activity)
            val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
            val bundle = Bundle()
            bundle.putString("PAGE_FROM", "SIGN_UP")
            delegate.arguments = bundle
            start(delegate)
        }

        sign_up_close.setOnClickListener {
            KeyboardUtils.hideSoftInput(activity)
            pop()
        }

        /**
         *  下一步 点击事件
         */
        btn_sign_up_submit.setOnClickListener {
            tel = sign_up_tel_et.text.toString()
            vCode = sign_up_vcode_et.text.toString()
            /**
             * 不用验空 需验手机号是否有效
             */
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

        btn_sign_up_submit.isClickable = false
    }

    private fun checkMessage(t: String, v: String) {
        checkMessageParams!!["telephone"] = t
        checkMessageParams!!["code"] = v
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
                            EmallLogger.d("success")
                            if (checkMessageBean.register == "0") {
                                val delegate: SetPasswordDelegate = SetPasswordDelegate().create()!!
                                val bundle = Bundle()
                                bundle.putString("MODIFY_PASSWORD_TELEPHONE", this@SignUpDelegate.tel)
                                delegate.arguments = bundle
                                start(delegate)
//            startForResult(delegate, REQ_MODIFY_FRAGMENT)
                                EmallLogger.d(topFragment)
                                EmallLogger.d(preFragment)
//            start(EcBottomDelegate())
//            supportDelegate.showHideFragment(MeDelegate(),IndexDelegate())

                                KeyboardUtils.hideSoftInput(activity)
                            } else {
                                Toast.makeText(activity, "手机号已注册", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            if (wrongVcodeToast != null) {
                                wrongVcodeToast!!.setText(getString(R.string.wrong_vcode))
                                wrongVcodeToast!!.duration = Toast.LENGTH_SHORT
                                wrongVcodeToast!!.show()
                            } else {
                                wrongVcodeToast = Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT)
                                wrongVcodeToast!!.show()
                            }
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
//        var i = "success"
//        if (i == "success") {
//            /**
//             * success
//             */
//            EmallLogger.d("success")
//            val delegate: SetPasswordDelegate = SetPasswordDelegate().create()!!
//            val bundle = Bundle()
//            bundle.putString("MODIFY_PASSWORD_TELEPHONE", tel)
//            delegate.arguments = bundle
//            start(delegate)
////            startForResult(delegate, REQ_MODIFY_FRAGMENT)
//            EmallLogger.d(topFragment)
//            EmallLogger.d(preFragment)
////            start(EcBottomDelegate())
////            supportDelegate.showHideFragment(MeDelegate(),IndexDelegate())
//
//            KeyboardUtils.hideSoftInput(activity)
//        } else {
//            if (wrongVcodeToast != null) {
//                wrongVcodeToast!!.setText(getString(R.string.wrong_vcode))
//                wrongVcodeToast!!.duration = Toast.LENGTH_SHORT
//                wrongVcodeToast!!.show()
//            } else {
//                wrongVcodeToast = Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT)
//                wrongVcodeToast!!.show()
//            }
////            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == REQ_MODIFY_FRAGMENT && resultCode == ISupportFragment.RESULT_OK) {
            // 在此通过Bundle data 获取返回的数据
            EmallLogger.d(data.getString("test"))
//            supportDelegate.pop()
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
            if (sign_up_tel_et.text.toString() == "") {
                btn_sign_up_submit.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                btn_sign_up_submit.isClickable = false
            }
            if (flag1 && flag2) {
                btn_sign_up_submit.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                btn_sign_up_submit.isClickable = true
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
            if (sign_up_vcode_et.text.toString() == "") {
                btn_sign_up_submit.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                btn_sign_up_submit.isClickable = false
            }

            if (flag1 && flag2) {
                btn_sign_up_submit.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                btn_sign_up_submit.isClickable = true

            }
        }
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
//                            Toast.makeText(activity, getString(R.string.not_register), Toast.LENGTH_SHORT).show()
                            showHint()

                        } else {
                            /**
                             * registered
                             */
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
        sign_up_vcode_tv.text = String.format("已向手机%s发送验证码", hideTel())
        sign_up_vcode_tv.visibility = View.VISIBLE
    }

    private fun hideTel(): String {
        return String.format("%s****%s", tel.substring(0, 4), tel.substring(7, 11))
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }
}