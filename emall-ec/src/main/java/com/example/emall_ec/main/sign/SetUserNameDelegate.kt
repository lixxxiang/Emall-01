package com.example.emall_ec.main.sign

import android.app.Activity
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.sign.data.CommonBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_set_user_name.*
import java.util.*
import android.text.InputFilter
import android.widget.EditText
import com.example.emall_ec.R
import java.util.regex.Pattern
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.emall_core.util.view.SoftKeyboardListener
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.index.dailypic.pic.PicDetailDelegate
import com.example.emall_ec.main.index.dailypic.video.VideoDetailDelegate
import com.example.emall_ec.main.me.setting.AccountPrivacySettingsDelegate
import com.example.emall_ec.main.me.setting.SettingDelegate
import com.example.emall_ec.main.program.ProgramDetailDelegate
import com.example.emall_ec.main.sign.data.UserNameLoginBean
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * Created by lixiang on 14/02/2018.
 */
class SetUserNameDelegate : BottomItemDelegate() {
    var userNameLoginParams: WeakHashMap<String, Any>? = WeakHashMap()
    var userNameLoginBean = UserNameLoginBean()

    private var mISignListener: ISignListener? = null
    var userName = String()
    var tel = String()
    var pwd = String()
    var findUserNameParam: WeakHashMap<String, Any>? = WeakHashMap()
    var registerParam: WeakHashMap<String, Any>? = WeakHashMap()
    var toast:Toast? = null
    var commonBean = CommonBean()

    fun create(): SetUserNameDelegate? {
        return SetUserNameDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_set_user_name
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ISignListener) {
            mISignListener = activity
        }
    }

    override fun initial() {
        set_nickname_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
//        set_nickname_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        set_user_name_toolbar.title = getString(R.string.sign_up)
        set_user_name_toolbar.setNavigationOnClickListener {
            pop()
        }
        (activity as AppCompatActivity).setSupportActionBar(set_user_name_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        set_nickname_tel_et.addTextChangedListener(mTextWatcher)
        setEditTextInhibitInputSpeChat(set_nickname_tel_et)

        tel = arguments.getString("USER_TELEPHONE")
        pwd = arguments.getString("USER_PWD")

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (set_nickname_title_rl != null)
                    set_nickname_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                if (set_nickname_title_rl != null)
                    set_nickname_title_rl.visibility = View.VISIBLE
            }
        })


        set_nickname_tel_et.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().contains(" ")) {
                    val str = s.toString().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    var str1 = ""
                    for (i in str.indices) {
                        str1 += str[i]
                    }
                    set_nickname_tel_et.setText(str1)
                    set_nickname_tel_et.setSelection(start)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        })


        btn_set_nickname_submit.setOnClickListener {
            userName = set_nickname_tel_et.text.toString()
            if (userName.isEmpty()) {
                if (toast != null) {
                    toast!!.setText(getString(R.string.empty_userName))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.empty_userName), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            } else {
                if (!checkMinLength(userName)) {
                    if (!checkMaxLength(userName)) {
//                        userNameAvailable(userName)
//                        EmallLogger.d(topFragment)
//                        supportDelegate.popTo(BottomItemDelegate::class.java, false)

//                        val bundle = Bundle()
//                        bundle.putString("test", "xxxx")
//                        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//                        supportDelegate.pop()
//                        start(EcBottomDelegate())
                        userNameAvailable(userName)

                    } else
                        if (toast != null) {
                            toast!!.setText("用户名过长")
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, "用户名过长", Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                } else
                    if (toast != null) {
                        toast!!.setText(getString(R.string.empty_userName))
                        toast!!.duration = Toast.LENGTH_SHORT
                        toast!!.show()
                    } else {
                        toast = Toast.makeText(activity, getString(R.string.empty_userName), Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
            }
        }
        btn_set_nickname_submit.isClickable = false
    }

    private fun userNameAvailable(string: String) {
        findUserNameParam!!["userName"] = string
        RestClient().builder()
                .url("http://59.110.161.48:8023/findUserName.do")
                .params(findUserNameParam!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.meta == "success") {
                            if (toast != null) {
                                toast!!.setText(getString(R.string.username_taken))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.username_taken), Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
                        } else {
                            register(tel, pwd, string)
                        }
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {
                        EmallLogger.d("failure")
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {
                        EmallLogger.d("error")
                    }
                })
                .build()
//                    .get()
                .post()
    }

    private fun register(t: String, p: String, n: String) {
        registerParam!!["telephone"] = t
        registerParam!!["userPassword"] = p.toLowerCase()
        registerParam!!["userName"] = n
            RestClient().builder()
                .url("http://59.110.161.48:8023/global/mall/register.do")
                .params(registerParam!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.meta == "success") {
                            login(tel, pwd)
                        }
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {
                        EmallLogger.d("failure")
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {
                        EmallLogger.d("error")
                    }
                })
                .build()
                .post()
    }

    private fun login(tel: String, pwd: String) {
        userNameLoginParams!!["userTelephone"] = tel
        userNameLoginParams!!["password"] = pwd.toLowerCase()
        EmallLogger.d(String.format("%s %s",tel, pwd))

        RestClient().builder()
                .url("http://59.110.161.48:8023/global/mall/UserNameLogin.do")
                .params(userNameLoginParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        userNameLoginBean = Gson().fromJson(response, UserNameLoginBean::class.java)
                        EmallLogger.d(response)
                        if (userNameLoginBean.meta == "success") {
                            /**
                             * success
                             */
                            EmallLogger.d(response)
                            /**
                             * test------------------------------------
                             */
                            SignHandler().onSignIn(response.replaceFirst("null", "\"" + tel + "\""), mISignListener!!)
                            if (toast != null) {
                                toast!!.setText("注册成功")
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, "注册成功", Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
//                            popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                            EmallLogger.d(arguments.getString("PAGE_FROM"))
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
                                arguments.getString("PAGE_FROM") == "GOODS_DETAIL" -> {
                                    popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "INDEX" -> {
                                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "DAILY_PIC" -> {
                                    popTo(findFragment(PicDetailDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "DAILY_VIDEO" -> {
                                    popTo(findFragment(VideoDetailDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "PROGRAM_DETAIL" -> {
                                    popTo(findFragment(ProgramDetailDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                                arguments.getString("PAGE_FROM") == "ME" -> {
                                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                                    KeyboardUtils.hideSoftInput(activity)
                                }
                            }
                            KeyboardUtils.hideSoftInput(activity)
                            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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

    fun setEditTextInhibitInputSpeChat(editText: EditText) {

        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            val speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"
            val pattern = Pattern.compile(speChat)
            val matcher = pattern.matcher(source.toString())
            if (matcher.find())
                ""
            else
                null
        }
        editText.filters = arrayOf(filter)
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
            btn_set_nickname_submit.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
            btn_set_nickname_submit.isClickable = true
            if(set_nickname_tel_et.text.toString() == ""){
                btn_set_nickname_submit.setBackgroundResource(R.drawable.sign_up_btn_shape)
                btn_set_nickname_submit.isClickable = false
            }
        }
    }


    fun checkMinLength(string: String): Boolean {
        return string.isEmpty()
    }

    fun checkMaxLength(string: String): Boolean {
        return string.length > 16
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }
}