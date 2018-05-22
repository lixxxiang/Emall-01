package com.example.emall_ec.main.sign

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.SoftKeyboardListener
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.me.setting.AccountPrivacySettingsDelegate
import com.example.emall_ec.main.me.setting.SettingDelegate
import com.example.emall_ec.main.sign.data.CommonBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_reset_password.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
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
    var flag1 = false
    var flag2 = false
    var toast: Toast? = null
    fun create(): ResetPasswordDelegate? {
        return ResetPasswordDelegate()
    }


    override fun setLayout(): Any? {
        return R.layout.delegate_reset_password
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        reset_pwd_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        reset_pwd_toolbar.title = getString(R.string.modify_password)
        (activity as AppCompatActivity).setSupportActionBar(reset_pwd_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        reset_pwd_toolbar.setNavigationOnClickListener {
            pop()
        }
        tel = arguments.getString("MODIFY_PASSWORD_TELEPHONE")
        reset_pwd_confirm_pwd_et.addTextChangedListener(mConfirmTextWatcher)
        reset_pwd_new_pwd_et.addTextChangedListener(mNewTextWatcher)
        reset_pwd_confirm_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        reset_pwd_new_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD


        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (reset_pwd_title_rl != null)
                    reset_pwd_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                if (reset_pwd_title_rl != null)

                    reset_pwd_title_rl.visibility = View.VISIBLE
            }
        })


        reset_pwd_new_pwd_et.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val temp = reset_pwd_new_pwd_et.text.toString()
                EmallLogger.d(temp)
                if (isLetterDigit(temp)) {
                    if (!checkMinLength(temp)) {
                        if (!checkMaxLength(temp)) {
                            newPassword = reset_pwd_new_pwd_et.text.toString()
                        } else
                            if (toast != null) {
                                toast!!.setText(getString(R.string.pwd_max))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
                    } else
                        if (toast != null) {
                            toast!!.setText(getString(R.string.pwd_min))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                } else {
                    if (toast != null) {
                        toast!!.setText(getString(R.string.error_pwd_format))
                        toast!!.duration = Toast.LENGTH_SHORT
                        toast!!.show()
                    } else {
                        toast = Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
                }
            }
        }

        reset_pwd_submit_btn.setOnClickListener {
            val temp = reset_pwd_new_pwd_et.text.toString()
            if (isLetterDigit(temp)) {
                if (!checkMinLength(temp)) {
                    if (!checkMaxLength(temp)) {
                        newPassword = reset_pwd_new_pwd_et.text.toString()
                        confirmPassword = reset_pwd_confirm_pwd_et.text.toString()
                        if (newPassword == confirmPassword) {
                            EmallLogger.d(arguments.getString("PAGE_FROM"))
                            if (arguments.getString("PAGE_FROM") == "ACCOUNT_PRIVACY_SETTINGS") {
                                if (!pwdRepeat(temp)) {
                                    EmallLogger.d("new", EncryptUtils.encryptMD5ToString(temp))
                                    EmallLogger.d("old", DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userPassword)
                                    changePassword()
                                } else
                                    if (toast != null) {
                                        toast!!.setText(getString(R.string.check_pwd_repeat))
                                        toast!!.duration = Toast.LENGTH_SHORT
                                        toast!!.show()
                                    } else {
                                        toast = Toast.makeText(activity, getString(R.string.check_pwd_repeat), Toast.LENGTH_SHORT)
                                        toast!!.show()
                                    }
                            }else{
                                changePassword()
                            }

                        } else
                            if (toast != null) {
                                toast!!.setText(getString(R.string.pwd_no_match))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.pwd_no_match), Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
                    } else
                        if (toast != null) {
                            toast!!.setText(getString(R.string.pwd_max))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                } else
                    if (toast != null) {
                        toast!!.setText(getString(R.string.pwd_min))
                        toast!!.duration = Toast.LENGTH_SHORT
                        toast!!.show()
                    } else {
                        toast = Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
            } else
                if (toast != null) {
                    toast!!.setText(getString(R.string.error_pwd_format))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
        }
    }

    private fun pwdRepeat(s: String): Boolean {
        return EncryptUtils.encryptMD5ToString(s).toLowerCase() == DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userPassword
    }

    private fun changePassword() {
        changePasswordParams!!["userTelephone"] = tel
        changePasswordParams!!["userPassword"] = EncryptUtils.encryptMD5ToString(confirmPassword).toLowerCase()
        changePasswordParams!!["client"] = "android"

        RestClient().builder()
                .url("http://59.110.161.48:8023/changePassword.do")
                .params(changePasswordParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.meta == "success") {
                            EmallLogger.d(arguments.getString("PAGE_FROM"))
                            val info = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0]
                            if (info != null) {
                                info.userPassword = changePasswordParams!!["userPassword"].toString()
                                DatabaseManager().getInstance()!!.getDao()!!.update(info)
                            }
                            if (toast != null) {
                                toast!!.setText("密码修改成功")
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, "密码修改成功", Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
                            when {
                                arguments.getString("PAGE_FROM") == "SETTING" -> {
                                    popTo(findFragment(SettingDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "ACCOUNT_PRIVACY_SETTINGS" -> {
                                    popTo(findFragment(AccountPrivacySettingsDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "AVATAR" -> {
                                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "ME" -> {
                                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "INDEX" -> {
                                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "GOODS_DETAIL" ->{
                                    popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }

                            }
                        } else {
                            if (toast != null) {
                                toast!!.setText("密码修改失败")
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, "密码修改失败", Toast.LENGTH_SHORT)
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
                reset_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                reset_pwd_submit_btn.isClickable = true

            }
            if (reset_pwd_new_pwd_et.text.toString() == "") {
                reset_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                reset_pwd_submit_btn.isClickable = false
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
                reset_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                reset_pwd_submit_btn.isClickable = true
            }

            if (reset_pwd_confirm_pwd_et.text.toString() == "") {
                reset_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                reset_pwd_submit_btn.isClickable = false
            }
        }
    }

    fun isLetterDigit(str: String): Boolean {
        var isDigit = false
        var isLetter = false
        for (i in 0 until str.length) {
            if (Character.isDigit(str[i])) {
                isDigit = true
            }
            if (Character.isLetter(str[i])) {
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

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }
}