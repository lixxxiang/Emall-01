package com.example.emall_ec.sign

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.sign.data.CommonBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_reset_password.*
import java.util.*

/**
 * Created by lixiang on 2018/4/2.
 */
class ResetPasswordDelegate : BottomItemDelegate() {

    var newPassword = String()
    var confirmPassword = String()
    var commonBean = CommonBean()
    var changePasswordParams: WeakHashMap<String, Any>? = WeakHashMap()
    var tel = String()

    fun create(): ResetPasswordDelegate? {
        return ResetPasswordDelegate()
    }


    override fun setLayout(): Any? {
        return R.layout.delegate_reset_password
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        reset_pwd_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        reset_pwd_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(reset_pwd_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tel = arguments.getString("MODIFY_PASSWORD_TELEPHONE")
        reset_pwd_confirm_pwd_et.addTextChangedListener(mTextWatcher)

        reset_pwd_confirm_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        reset_pwd_new_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        reset_pwd_new_pwd_et.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {

            } else {
                val temp = reset_pwd_new_pwd_et.text.toString()
                EmallLogger.d(temp)
                if (isLetterDigit(temp)) {
                    if (!checkMinLength(temp)) {
                        if (!checkMaxLength(temp)) {
                            newPassword = reset_pwd_new_pwd_et.text.toString()
                        } else
                            Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT).show()
//                    reset_pwd_new_pwd_et.isFocusable = true
//                    reset_pwd_new_pwd_et.isFocusableInTouchMode = true
//                    reset_pwd_new_pwd_et.requestFocus()
//                    if(reset_pwd_confirm_pwd_et.isFocusable == true){
//                        reset_pwd_confirm_pwd_et.isFocusable = false
//                    }
                }
            }
        }

        reset_pwd_submit_btn.setOnClickListener {
            val temp = reset_pwd_new_pwd_et.text.toString()
            EmallLogger.d(temp)
            if (isLetterDigit(temp)) {
                if (!checkMinLength(temp)) {
                    if (!checkMaxLength(temp)) {
                        newPassword = reset_pwd_new_pwd_et.text.toString()
                        confirmPassword = reset_pwd_confirm_pwd_et.text.toString()
                        if (newPassword == confirmPassword) {
                            Toast.makeText(activity, "pwd success", Toast.LENGTH_SHORT).show()
                            changePassword()
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
            reset_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
            if (reset_pwd_confirm_pwd_et.text.toString() == "") {
                reset_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
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