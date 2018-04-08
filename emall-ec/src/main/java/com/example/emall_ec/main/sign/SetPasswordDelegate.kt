package com.example.emall_ec.main.sign

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.SoftKeyboardListener
import com.example.emall_ec.R
import com.example.emall_ec.main.sign.data.CommonBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_reset_password.*
import kotlinx.android.synthetic.main.delegate_set_password.*
import java.util.*

/**
 * Created by lixiang on 14/02/2018.
 */
class SetPasswordDelegate : EmallDelegate() {

    var newPassword = String()
    var confirmPassword = String()
    var commonBean = CommonBean()
    var changePasswordParams: WeakHashMap<String, Any>? = WeakHashMap()
    var tel = String()
    var flag1 = false
    var flag2 = false

    fun create(): SetPasswordDelegate? {
        return SetPasswordDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_set_password
    }

    override fun initial() {
//        set_password_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        set_password_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")

        tel = arguments.getString("MODIFY_PASSWORD_TELEPHONE")

        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        set_pwd_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(set_pwd_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        set_password_new_pwd_et.addTextChangedListener(mNewTextWatcher)
        set_password_confirm_password_et.addTextChangedListener(mConfirmTextWatcher)
        set_password_confirm_password_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        set_password_new_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                set_password_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                set_password_title_rl.visibility = View.VISIBLE
            }
        })

        set_password_new_pwd_et.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val temp = set_password_new_pwd_et.text.toString()
                if (isLetterDigit(temp)) {
                    if (!checkMinLength(temp)) {
                        if (!checkMaxLength(temp)) {
                            newPassword = set_password_new_pwd_et.text.toString()
                        } else
                            Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT).show()
                }
            }
        }

        btn_set_password_submit.setOnClickListener {
            val temp = set_password_new_pwd_et.text.toString()
            EmallLogger.d(temp)
            if (isLetterDigit(temp)) {
                if (!checkMinLength(temp)) {
                    if (!checkMaxLength(temp)) {
                        newPassword = set_password_new_pwd_et.text.toString()
                        confirmPassword = set_password_confirm_password_et.text.toString()
                        if (newPassword == confirmPassword) {
                            Toast.makeText(activity, "pwd success", Toast.LENGTH_SHORT).show()
                            val delegate: SetUserNameDelegate = SetUserNameDelegate().create()!!
                            val bundle = Bundle()
                            bundle.putString("USER_TELEPHONE", tel)
                            bundle.putString("USER_PWD", newPassword)
                            delegate.arguments = bundle
                            start(delegate)
                            KeyboardUtils.hideSoftInput(activity)

//                            changePassword()
                        } else
                            Toast.makeText(activity, getString(R.string.pwd_no_match), Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT).show()
        }
    }

    private fun changePassword() {
        changePasswordParams!!["userTelephone"] = tel
        changePasswordParams!!["userPassword"] = confirmPassword

        RestClient().builder()
                .url("http://59.110.161.48:8023/changePassword.do")
                .params(changePasswordParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.meta == "success") {
                            Toast.makeText(activity, "change pwd success!!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "change pwd failure!!", Toast.LENGTH_SHORT).show()
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private var mNewTextWatcher: TextWatcher = object : TextWatcher {
        /**
         * 大于8小于20
         */
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
            if (flag1 && flag2) {
                btn_set_password_submit.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                btn_set_password_submit.isClickable = true

            }
            if (set_password_new_pwd_et.text.toString() == "") {
                btn_set_password_submit.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                btn_set_password_submit.isClickable = false
            }
        }
    }

    private var mConfirmTextWatcher: TextWatcher = object : TextWatcher {
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

            if (flag1 && flag2) {
                btn_set_password_submit.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                btn_set_password_submit.isClickable = true
            }

            if (set_password_confirm_password_et.text.toString() == "") {
                btn_set_password_submit.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                btn_set_password_submit.isClickable = false
            }
        }
    }

    fun isLetterDigit(str: String): Boolean {
        var isDigit = false//定义一个boolean值，用来表示是否包含数字
        var isLetter = false//定义一个boolean值，用来表示是否包含字母
        for (i in 0 until str.length) {
            if (Character.isDigit(str[i])) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true
            }
            if (Character.isLetter(str[i])) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true
            }
        }
        val regex = "^[a-zA-Z0-9]+$"
        return isDigit && isLetter && str.matches(regex.toRegex())
    }

    fun checkMinLength(string: String): Boolean {
        return string.length < 8
    }

    fun checkMaxLength(string: String): Boolean {
        return string.length > 20
    }
}