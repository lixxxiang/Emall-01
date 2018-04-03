package com.example.emall_ec.sign

import android.app.Activity
import android.graphics.Typeface
import android.widget.Toast
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator.params
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.sign.data.CommonBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_set_user_name.*
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*
import kotlinx.android.synthetic.main.delegate_sign_up.*
import java.util.*
import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText
import com.example.emall_ec.R
import java.util.Locale.filter
import java.util.regex.Pattern
import android.text.Editable
import android.text.TextWatcher




/**
 * Created by lixiang on 14/02/2018.
 */
class SetUserNameDelegate : EmallDelegate() {

    private var mISignListener: ISignListener? = null
    var userName = String()
    var findUserNameParam: WeakHashMap<String, Any>? = WeakHashMap()
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
        set_nickname_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")


        setEditTextInhibitInputSpeChat(set_nickname_tel_et)
        set_nickname_tel_et.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
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
            EmallLogger.d(params)

            userName = set_nickname_tel_et.text.toString()
            if (userName.isEmpty()) {
                Toast.makeText(activity, getString(R.string.empty_userName), Toast.LENGTH_SHORT).show()
            } else {
                if (!checkMinLength(userName)) {
                    if (!checkMaxLength(userName)) {
                        userNameAvailable(userName)
                    } else
                        Toast.makeText(activity, "用户名过长", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(activity, getString(R.string.empty_userName), Toast.LENGTH_SHORT).show()
            }


//            val url: String = if (FileUtil.checkEmulator()) {
//                "http://10.0.2.2:3003/news"
//            } else {
////                "http://10.10.90.38:3003/news"
//                "http://192.168.1.36:3003/news"
//
//            }
//
//            var fakeResponce = "{\n" +
//                    "avatar: \"avatar\",\n" +
//                    "gender: \"gender\",\n" +
//                    "userId: \"123\",\n" +
//                    "name: \"n\",\n" +
//                    "address: \"address\",\n" +
//                    "id: 44\n" +
//                    "}"
//            SignHandler().onSignUp(fakeResponce, mISignListener!!)
////                            startWithPop(SetPasswordDelegate())
//            startWithPop(SignInByTelDelegate())

//            RestClient().builder()
//                    .url(url)//EMULATOR
////                    .url("http://192.168.2.162:3003/news")//HOME
////                    .params("password", edit_sign_up_pwd.text.toString())
////                    .params("email", edit_sign_up_name.text.toString())
//                    .params(params)
//                    .success(object : ISuccess {
//                        override fun onSuccess(response: String) {
//                            EmallLogger.json("USER_PROFILE", response)
//                            SignHandler().onSignUp(response, mISignListener!!)
////                            startWithPop(SetPasswordDelegate())
//                            startWithPop(SignInByTelDelegate())
//                        }
//                    })
//                    .failure(object : IFailure {
//                        override fun onFailure() {
//                            EmallLogger.d("failure")
//                        }
//                    })
//                    .error(object : IError {
//                        override fun onError(code: Int, msg: String) {
//                            EmallLogger.d("error")
//                        }
//                    })
//                    .build()
////                    .get()
//                    .post()
        }
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
                            Toast.makeText(activity, getString(R.string.username_taken), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, getString(R.string.register_success), Toast.LENGTH_SHORT).show()

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


    fun checkMinLength(string: String): Boolean {
        return string.isEmpty()
    }

    fun checkMaxLength(string: String): Boolean {
        return string.length > 16
    }
}