package com.example.emall_ec.main.sign

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
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
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_core.util.view.SoftKeyboardListener
import com.example.emall_ec.database.DatabaseManager
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*
import kotlinx.android.synthetic.main.forget_pwd_dialog.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * Created by lixiang on 2018/3/3.
 */
class SignInByAccountDelegate : EmallDelegate() {
    var isHide: Boolean = true
    var tel = String()
    var password = String()
    var passwordMD5 = String()
    var flag1 = false
    var flag2 = false
    var findTelephoneParams: WeakHashMap<String, Any>? = WeakHashMap()
    var userNameLoginParams: WeakHashMap<String, Any>? = WeakHashMap()
    var commonBean = CommonBean()
    var userNameLoginBean = UserNameLoginBean()
    private var mISignListener: ISignListener? = null
    var toast: Toast?= null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ISignListener) {
            mISignListener = activity
        }
    }

    fun create(): SignInByAccountDelegate? {
        return SignInByAccountDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_sign_in_by_account
    }

    @SuppressLint("InflateParams")
    override fun initial() {

        sign_in_by_account_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        sign_in_by_account_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/hide.ttf")
        sign_in_by_account_tel_et.requestFocus()
        EmallLogger.d(arguments.getString("PAGE_FROM"))

        sign_in_by_account_tel_et.addTextChangedListener(mTelTextWatcher)
        sign_in_by_account_pwd_et.addTextChangedListener(mPwdTextWatcher)

        val passwordLength = sign_in_by_account_pwd_et.text.length
        sign_in_by_account_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        sign_in_by_account_pwd_et.setSelection(passwordLength)
        sign_in_by_account_hide_tv.setOnClickListener {
            if (isHide) {
                sign_in_by_account_hide_tv.height = 14
                sign_in_by_account_hide_tv.text = getString(R.string.show)
                sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/show.ttf")
                sign_in_by_account_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                sign_in_by_account_pwd_et.setSelection(sign_in_by_account_pwd_et.text.length)
                isHide = false
            } else {
                sign_in_by_account_hide_tv.height = 9
                sign_in_by_account_hide_tv.text = getString(R.string.hide)
                sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/hide.ttf")
                sign_in_by_account_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                sign_in_by_account_pwd_et.setSelection(sign_in_by_account_pwd_et.text.length)
                isHide = true
            }
        }

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (sign_in_by_account_title_rl != null)
                    sign_in_by_account_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                if (sign_in_by_account_title_rl != null)
                    sign_in_by_account_title_rl.visibility = View.VISIBLE
            }
        })

        sign_in_by_account_submit_btn.setOnClickListener {
            tel = sign_in_by_account_tel_et.text.toString()
            password = sign_in_by_account_pwd_et.text.toString()
            if (RegexUtils.isMobileExact(tel)) {
                checkAccount()
            } else {
                if (toast != null) {
                    toast!!.setText(getString(R.string.wrong_tel))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            }
        }

        sign_in_by_account_submit_btn.isClickable = false
        btn_sign_in_by_tel_submit.setOnClickListener {
            val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
            val bundle = Bundle()
            bundle.putString("PAGE_FROM", "SIGN_IN_BY_COUNT")
            delegate.arguments = bundle
            startWithPop(delegate)
        }

        sign_in_by_account_login_tv.setOnClickListener {
            startWithPop(SignUpDelegate())
        }

        sign_in_by_account_close.setOnClickListener {
            pop()
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

            dialog.forget_rl1.setOnClickListener {
                dialog.dismiss()
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "SIGN_IN_BY_ACCOUNT")
                delegate.arguments = bundle
                startWithPop(delegate)
            }

            dialog.forget_rl2.setOnClickListener {
                dialog.dismiss()
                val delegate: ModifyPasswordDelegate = ModifyPasswordDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))
                delegate.arguments = bundle
                start(delegate)
            }

            dialog.forget_rl3.setOnClickListener {
                dialog.dismiss()
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
                            if (toast != null) {
                                toast!!.setText(getString(R.string.not_register))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.not_register), Toast.LENGTH_SHORT)
                                toast!!.show()
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
    }

    private fun login() {
        userNameLoginParams!!["userTelephone"] = tel
        passwordMD5 = EncryptUtils.encryptMD5ToString(password).toLowerCase()
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
                            EmallLogger.d(response)
                            /**
                             * test------------------------------------
                             */
//                            val info = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0]
//                            if (info != null) {
//                                info.userPassword = passwordMD5
//                                DatabaseManager().getInstance()!!.getDao()!!.update(info)
//                            }
                            SignHandler().onSignIn(response.replaceFirst("null", "\"" + tel + "\"").replaceFirst("null","\"" + passwordMD5 + "\""), mISignListener!!)
//                            SignHandler().onSignIn(response.replaceFirst("null", "\"" + tel + "\""), mISignListener!!)

                            val bundle = Bundle()
                            bundle.putString("USER_NAME", userNameLoginBean.user.username)
                            KeyboardUtils.hideSoftInput(activity)
//                            popTo(preFragment.javaClass, false)
                            pop()
                            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            setFragmentResult(RESULT_OK, bundle)
                        } else {

                            if (toast != null) {
                                toast!!.setText(getString(R.string.account_pwd_error))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.account_pwd_error), Toast.LENGTH_SHORT)
                                toast!!.show()
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
            if (sign_in_by_account_tel_et.text.toString() == "") {
                sign_in_by_account_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                sign_in_by_account_submit_btn.isClickable = false
            }
            if (flag1 && flag2) {
                sign_in_by_account_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                sign_in_by_account_submit_btn.isClickable = true
            }
        }
    }

    private var mPwdTextWatcher: TextWatcher = object : TextWatcher {
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
            if (sign_in_by_account_pwd_et.text.toString() == "") {
                sign_in_by_account_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                sign_in_by_account_submit_btn.isClickable = false
            }
            if (flag1 && flag2) {
                sign_in_by_account_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                sign_in_by_account_submit_btn.isClickable = true
            }
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}