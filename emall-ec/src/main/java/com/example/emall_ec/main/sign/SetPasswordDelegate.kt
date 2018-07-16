package com.example.emall_ec.main.sign

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
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
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.sign.data.CommonBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_set_password.*
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

/**
 * Created by lixiang on 14/02/2018.
 */
class SetPasswordDelegate : BottomItemDelegate() {

    var newPassword = String()
    var confirmPassword = String()
    var commonBean = CommonBean()
    var changePasswordParams: WeakHashMap<String, Any>? = WeakHashMap()
    var tel = String()
    var flag1 = false
    var flag2 = false
    private val REQ_MODIFY_FRAGMENT = 100
    var toast : Toast? = null
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
        set_pwd_toolbar.title = getString(R.string.sign_up)
        (activity as AppCompatActivity).setSupportActionBar(set_pwd_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        set_pwd_toolbar.setNavigationOnClickListener {
            pop()
        }

        set_password_new_pwd_et.addTextChangedListener(mNewTextWatcher)
        set_password_confirm_password_et.addTextChangedListener(mConfirmTextWatcher)

        set_password_confirm_password_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        set_password_new_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (set_password_title_rl != null){
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(1f, 0f, 1f, 0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    set_password_title_rl.startAnimation(animationSet)
                    animationSet.fillAfter = true
                    Handler().postDelayed({
                        set_password_title_rl.visibility = View.GONE
                    }, 200)
                }
            }

            override fun keyBoardHide(height: Int) {
                if (set_password_title_rl != null){
                    set_password_title_rl.visibility = View.VISIBLE
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    set_password_title_rl.startAnimation(animationSet)
                    animationSet.fillAfter = true
                }
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

        btn_set_password_submit.setOnClickListener {
            val temp = set_password_new_pwd_et.text.toString()
            EmallLogger.d(temp)
            if (isLetterDigit(temp)) {
                if (!checkMinLength(temp)) {
                    if (!checkMaxLength(temp)) {
                        newPassword = set_password_new_pwd_et.text.toString()
                        confirmPassword = set_password_confirm_password_et.text.toString()
                        if (newPassword == confirmPassword) {
                            val delegate: SetUserNameDelegate = SetUserNameDelegate().create()!!
                            val bundle = Bundle()
                            bundle.putString("USER_TELEPHONE", tel)
                            bundle.putString("USER_PWD", EncryptUtils.encryptMD5ToString(newPassword))
                            bundle.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))

                            delegate.arguments = bundle
                            start(delegate)
//                            startForResult(delegate, REQ_MODIFY_FRAGMENT)
                            EmallLogger.d(findFragment(EcBottomDelegate().javaClass))



                            KeyboardUtils.hideSoftInput(activity)

//                            changePassword()
                        } else{
                            if (toast != null) {
                                toast!!.setText(getString(R.string.pwd_no_match))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.pwd_no_match), Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
                        }
                    } else{
                        if (toast != null) {
                            toast!!.setText(getString(R.string.pwd_max))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                    }
                } else{
                    if (toast != null) {
                        toast!!.setText(getString(R.string.pwd_min))
                        toast!!.duration = Toast.LENGTH_SHORT
                        toast!!.show()
                    } else {
                        toast = Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
                }
            } else{
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
        btn_set_password_submit.isClickable = false
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == REQ_MODIFY_FRAGMENT && resultCode == ISupportFragment.RESULT_OK) {
            // 在此通过Bundle data 获取返回的数据
            EmallLogger.d(data.getString("test"))
//            supportDelegate.pop()
        }
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

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }
}