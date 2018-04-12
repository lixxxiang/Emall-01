package com.example.emall_ec.main.me.setting

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.SoftKeyboardListener
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.database.UserProfile
import com.example.emall_ec.main.sign.data.CheckMessageBean
import com.example.emall_ec.main.sign.data.CommonBean
import com.example.emall_ec.main.sign.data.SendMessageBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_modify_tel_vcode.*
import kotlinx.android.synthetic.main.delegate_set_password.*
import kotlinx.android.synthetic.main.delegate_sign_up.*
import kotlinx.android.synthetic.main.me_function_item.*
import java.util.*

/**
 * Created by lixiang on 2018/4/9.
 */
class ModifyTelVcodeDelegate : BottomItemDelegate() {
    var vcode = String()
    var oldTel = String()
    var newTel = String()
    var emptyToast: Toast? = null
    var sendMessageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var checkMessageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var changeTelephoneParams: WeakHashMap<String, Any>? = WeakHashMap()
    var commonBean = CommonBean()
    var checkMessageBean = CheckMessageBean()
    var sendMessageBean = SendMessageBean()
    fun create(): ModifyTelVcodeDelegate {
        return ModifyTelVcodeDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_modify_tel_vcode
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        modify_tel_vcode_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
//        modify_tel_vcode_vcode_et.requestFocus()

        modify_tel_vcode_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(modify_tel_vcode_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        modify_tel_vcode_toolbar.setNavigationOnClickListener {
            pop()
        }

        modify_tel_vcode_vcode_et.addTextChangedListener(mMVcodeTextWatcher)
        modify_tel_vcode_count_down.setCountDownMillis(60000)

        newTel = arguments.getString("NEW_TELEPHONE")
        oldTel = arguments.getString("OLD_TELEPHONE")
        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (modify_tel_vcode_title_rl != null)
                    modify_tel_vcode_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                if (modify_tel_vcode_title_rl != null)
                    modify_tel_vcode_title_rl.visibility = View.VISIBLE
            }
        })

        modify_tel_vcode_count_down.setOnClickListener {
            modify_tel_vcode_count_down.start()
            getVCode()
//            if (vcode.isEmpty()) {
//                if (emptyToast != null) {
//                    emptyToast!!.setText(getString(R.string.empty_tel))
//                    emptyToast!!.duration = Toast.LENGTH_SHORT
//                    emptyToast!!.show()
//                } else {
//                    emptyToast = Toast.makeText(activity, getString(R.string.empty_vcode), Toast.LENGTH_SHORT)
//                    emptyToast!!.show()
//                }
//            } else {
//                modify_tel_vcode_count_down.start()
//                checkMessage(vcode)
//
//            }
        }

        modify_tel_vcode_submit_btn.setOnClickListener {
            vcode = modify_tel_vcode_vcode_et.text.toString()
            if (!vcode.isEmpty()) {
                checkMessage(vcode)
            } else {
                if (emptyToast != null) {
                    emptyToast!!.setText(getString(R.string.empty_vcode))
                    emptyToast!!.duration = Toast.LENGTH_SHORT
                    emptyToast!!.show()
                } else {
                    emptyToast = Toast.makeText(activity, getString(R.string.empty_vcode), Toast.LENGTH_SHORT)
                    emptyToast!!.show()
                }
            }
        }

        modify_tel_vcode_submit_btn.isClickable = false
    }

    private fun getVCode() {
        sendMessageParams!!["telephone"] = newTel
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
                            showHint()
                        } else {
                            /**
                             * registered
                             */
                            Toast.makeText(activity, "cannot use this number because it is already signed up", Toast.LENGTH_SHORT).show()
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
        modify_tel_vcode_vcode_tv.text = String.format("已向手机%s发送验证码", hideTel())
        modify_tel_vcode_vcode_tv.visibility = View.VISIBLE
    }

    private fun hideTel(): String {
        return String.format("%s****%s", newTel.substring(0, 4), newTel.substring(7, 11))
    }

    private fun checkMessage(v: String) {
        checkMessageParams!!["telephone"] = arguments.getString("NEW_TELEPHONE")
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
                            changeTelephone(oldTel, newTel)
                        } else {
                            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
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
//        var i = "success"
//        if (i == "success") {
//            /**
//             * success
//             */
//            EmallLogger.d("success")
//            val delegate: ResetPasswordDelegate = ResetPasswordDelegate().create()!!
//            val bundle = Bundle()
//            bundle.putString("MODIFY_PASSWORD_TELEPHONE", tel)
//            delegate.arguments = bundle
//            start(delegate)
//        } else {
//            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
//        }
    }

    private fun changeTelephone(oldTel: String, newTel: String) {
        changeTelephoneParams!!["oldTelephone"] = oldTel
        changeTelephoneParams!!["newTelephone"] = newTel
        EmallLogger.d(oldTel + newTel)
        RestClient().builder()
                .url("http://59.110.161.48:8023/changeTelephone.do")
                .params(changeTelephoneParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        EmallLogger.d(response)
                        if (commonBean.meta == "success") {
                            /**
                             * success
                             */
                            val info = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0]
                            if (info != null) {
                                info.userTelephone = newTel
                                DatabaseManager().getInstance()!!.getDao()!!.update(info)
                            }
                            popTo(findFragment(AccountPrivacySettingsDelegate().javaClass).javaClass, false)
                            KeyboardUtils.hideSoftInput(activity)
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


    private var mMVcodeTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            modify_tel_vcode_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
            modify_tel_vcode_submit_btn.isClickable = true
            if (modify_tel_vcode_vcode_et.text.toString() == "") {
                modify_tel_vcode_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                modify_tel_vcode_submit_btn.isClickable = false
            }


        }
    }
}