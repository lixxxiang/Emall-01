package com.example.emall_ec.main.sign

import android.app.Activity
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator.params
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
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.view.SoftKeyboardListener
import com.example.emall_ec.main.me.MeDelegate
import kotlinx.android.synthetic.main.delegate_set_password.*


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
//        set_nickname_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        set_user_name_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(set_user_name_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        set_nickname_tel_et.addTextChangedListener(mTextWatcher)
        setEditTextInhibitInputSpeChat(set_nickname_tel_et)

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
            EmallLogger.d(params)

            userName = set_nickname_tel_et.text.toString()
            if (userName.isEmpty()) {
                Toast.makeText(activity, getString(R.string.empty_userName), Toast.LENGTH_SHORT).show()
            } else {
                if (!checkMinLength(userName)) {
                    if (!checkMaxLength(userName)) {
//                        userNameAvailable(userName)
                        EmallLogger.d(topFragment)
                        supportDelegate.popTo(BottomItemDelegate::class.java, false)
                    } else
                        Toast.makeText(activity, "用户名过长", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(activity, getString(R.string.empty_userName), Toast.LENGTH_SHORT).show()
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
        }
    }


    fun checkMinLength(string: String): Boolean {
        return string.isEmpty()
    }

    fun checkMaxLength(string: String): Boolean {
        return string.length > 16
    }
}