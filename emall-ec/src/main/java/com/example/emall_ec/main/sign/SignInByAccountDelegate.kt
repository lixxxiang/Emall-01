package com.example.emall_ec.main.sign

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.sign.data.CommonBean
import com.example.emall_ec.main.sign.data.UserNameLoginBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_sign_in_by_account.*
import java.util.*
import android.text.InputType
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Gravity
import com.example.emall_core.util.dimen.DimenUtil
import kotlinx.android.synthetic.main.forget_pwd_dialog.*


/**
 * Created by lixiang on 2018/3/3.
 */
class SignInByAccountDelegate : EmallDelegate() {
    var isHide : Boolean = true
    var tel = String()
    var password = String()
    var passwordMD5 = String()
    var findTelephoneParams : WeakHashMap<String, Any> ?= WeakHashMap()
    var userNameLoginParams : WeakHashMap<String, Any> ?= WeakHashMap()
    var commonBean = CommonBean()
    var userNameLoginBean = UserNameLoginBean()

    override fun setLayout(): Any? {
        return R.layout.delegate_sign_in_by_account
    }

    @SuppressLint("InflateParams")
    override fun initial() {

        sign_in_by_account_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        sign_in_by_account_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/hide.ttf")
        sign_in_by_account_tel_et.requestFocus()

        sign_in_by_account_tel_et.addTextChangedListener(mTelTextWatcher)
        sign_in_by_account_pwd_et.addTextChangedListener(mPwdTextWatcher)

        val passwordLength = sign_in_by_account_pwd_et.text.length
        sign_in_by_account_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        sign_in_by_account_pwd_et.setSelection(passwordLength)
        sign_in_by_account_hide_tv.setOnClickListener {
            if (isHide){
                sign_in_by_account_hide_tv.height = 14
                sign_in_by_account_hide_tv.text = getString(R.string.show)
                sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/show.ttf")
                sign_in_by_account_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                sign_in_by_account_pwd_et.setSelection(sign_in_by_account_pwd_et.text.length)
                isHide = false
            }else{
                sign_in_by_account_hide_tv.height = 9
                sign_in_by_account_hide_tv.text = getString(R.string.hide)
                sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/hide.ttf")
                sign_in_by_account_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                sign_in_by_account_pwd_et.setSelection(sign_in_by_account_pwd_et.text.length)
                isHide = true
            }
        }



        sign_in_by_account_submit_btn.setOnClickListener {
            tel = sign_in_by_account_tel_et.text.toString()
            password = sign_in_by_account_pwd_et.text.toString()
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

                    if (password.isEmpty()) {
                        /**
                         * empty pwd
                         */
                        Toast.makeText(activity, getString(R.string.empty_password), Toast.LENGTH_SHORT).show()
                    } else {
                        /**
                         * tel ok & pwd ok
                         */
                        checkAccount()
                    }
                } else {
                    /**
                     * tel is invalid
                     */
                    Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT).show()
                }
            }
        }

        btn_sign_in_by_tel_submit.setOnClickListener {
            startWithPop(SignInByTelDelegate())
        }

        sign_in_by_account_login_tv.setOnClickListener {
            startWithPop(SignUpDelegate())
        }

        sign_in_by_account_forget_password_tv.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val dialog = builder.create()
            dialog.setView(LayoutInflater.from(activity).inflate(R.layout.forget_pwd_dialog, null))
            dialog.show()
            val window = dialog.window
            val params = window.attributes
            params.height = DimenUtil().dip2px(context, 180F)
            params.gravity = Gravity.CENTER_HORIZONTAL
            dialog.window.attributes = params

            dialog.forget_rl1.setOnClickListener{
                startWithPop(SignInByTelDelegate())
            }

            dialog.forget_rl2.setOnClickListener{
                dialog.dismiss()
                start(ModifyPasswordDelegate().create())
            }

            dialog.forget_rl3.setOnClickListener{
            }

        }


    }

    private fun checkAccount() {
        findTelephoneParams!!["telephone"] = tel
        RestClient().builder()
                .url("http://59.110.161.48:8023/findTelephone.do")
                .params(findTelephoneParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.meta == "success") {
                            /**
                             * success
                             */
                            login()
                        } else {
                            Toast.makeText(activity, getString(R.string.not_register), Toast.LENGTH_SHORT).show()
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
    }

    private fun login(){
        userNameLoginParams!!["userTelephone"] = tel
        passwordMD5 = EncryptUtils.encryptMD5ToString(password)
        EmallLogger.d(passwordMD5)
        userNameLoginParams!!["password"] = passwordMD5

        RestClient().builder()
                .url("http://59.110.161.48:8023/global/mall/UserNameLogin.do")
                .params(userNameLoginParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        userNameLoginBean = Gson().fromJson(response, UserNameLoginBean::class.java)
                        if (userNameLoginBean.meta == "success") {
                            /**
                             * success
                             */

                        } else {
                            Toast.makeText(activity, getString(R.string.account_pwd_error), Toast.LENGTH_SHORT).show()
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
            sign_in_by_account_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
            if (sign_in_by_account_tel_et.text.toString() == "") {
                sign_in_by_account_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
            }
        }
    }
}