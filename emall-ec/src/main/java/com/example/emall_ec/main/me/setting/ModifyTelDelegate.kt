package com.example.emall_ec.main.me.setting

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.SoftKeyboardListener
import com.example.emall_ec.R
import com.example.emall_ec.main.sign.data.CommonBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_modify_tel.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

/**
 * Created by lixiang on 2018/4/8.
 */
class ModifyTelDelegate : BottomItemDelegate() {

    var oldTel = String()
    var newTel = String()
    var flag1 = false
    var flag2 = false
    var wrongToast: Toast? = null
    var findTelephoneParams: WeakHashMap<String, Any>? = WeakHashMap()
    var changeTelephoneParams: WeakHashMap<String, Any>? = WeakHashMap()

    var commonBean = CommonBean()

    fun create(): ModifyTelDelegate? {
        return ModifyTelDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_modify_tel
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        modify_tel_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        modify_tel_toolbar.title = getString(R.string.new_tel)
        (activity as AppCompatActivity).setSupportActionBar(modify_tel_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        modify_tel_toolbar.setNavigationOnClickListener {
            pop()
        }

        modify_tel_old_tel_et.addTextChangedListener(mOldTextWatcher)
        modify_tel_new_tel_et.addTextChangedListener(mNewTextWatcher)

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (modify_tel_title_rl != null)
                    modify_tel_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                if (modify_tel_title_rl != null)
                    modify_tel_title_rl.visibility = View.VISIBLE
            }
        })

        modify_tel_old_tel_et.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (modify_tel_old_tel_et != null) {
                if (!b) {
                    val tempTel = modify_tel_old_tel_et.text.toString()
                    if (!RegexUtils.isMobileExact(tempTel)) {
                        if (wrongToast != null) {
                            wrongToast!!.setText(getString(R.string.wrong_tel))
                            wrongToast!!.duration = Toast.LENGTH_SHORT
                            wrongToast!!.show()
                        } else {
                            wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT)
                            wrongToast!!.show()
                        }
                    }
                }
            }
        }

        modify_tel_submit_btn.setOnClickListener {
            oldTel = modify_tel_old_tel_et.text.toString()
            newTel = modify_tel_new_tel_et.text.toString()
            if (!RegexUtils.isMobileExact(oldTel)) {
                if (wrongToast != null) {
                    wrongToast!!.setText(getString(R.string.wrong_tel))
                    wrongToast!!.duration = Toast.LENGTH_SHORT
                    wrongToast!!.show()
                } else {
                    wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT)
                    wrongToast!!.show()
                }
            } else if (!RegexUtils.isMobileExact(newTel)) {
                if (wrongToast != null) {
                    wrongToast!!.setText(getString(R.string.wrong_tel))
                    wrongToast!!.duration = Toast.LENGTH_SHORT
                    wrongToast!!.show()
                } else {
                    wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT)
                    wrongToast!!.show()
                }
            } else {
                if (oldTel != newTel) {
                    checkAccount(oldTel)
                } else {
                    Toast.makeText(activity, "same tel?!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        modify_tel_submit_btn.isClickable = false
    }

    private fun checkAccount(tel: String) {
        findTelephoneParams!!["telephone"] = tel
        RestClient().builder()
                .url("http://59.110.161.48:8023/findTelephone.do")
                .params(findTelephoneParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        EmallLogger.d(response)
                        if (commonBean.meta == "success") {
                            /**
                             * success
                             */
//                            changeTelephone(oldTel, newTel)
                            KeyboardUtils.hideSoftInput(activity)

                            val delegate = ModifyTelVcodeDelegate().create()
                            val bundle = Bundle()
                            bundle.putString("OLD_TELEPHONE", oldTel)
                            bundle.putString("NEW_TELEPHONE", newTel)
                            delegate.arguments = bundle
                            start(delegate)
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
                            val delegate = ModifyTelVcodeDelegate().create()
                            val bundle = Bundle()
                            bundle.putString("NEW_TELEPHONE", newTel)
                            delegate.arguments = bundle
                            start(delegate)
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

    private var mOldTextWatcher: TextWatcher = object : TextWatcher {
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
                modify_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                modify_tel_submit_btn.isClickable = true
            }

            if (modify_tel_old_tel_et.text.toString() == "") {
                modify_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                modify_tel_submit_btn.isClickable = false
            }
        }
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
                modify_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                modify_tel_submit_btn.isClickable = true

            }
            if (modify_tel_new_tel_et.text.toString() == "") {
                modify_tel_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                modify_tel_submit_btn.isClickable = false
            }
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}