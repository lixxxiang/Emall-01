package com.example.emall_ec.main.sign

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.SoftKeyboardListener
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.sign.data.CheckMessageBean
import com.example.emall_ec.main.sign.data.CommonBean
import com.example.emall_ec.main.sign.data.SendMessageBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_modify_password.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

/**
 * Created by lixiang on 2018/4/2.
 */
class ModifyPasswordDelegate : BottomItemDelegate() {
    var tel = String()
    var vCode = String()
    var flag1 = false
    var flag2 = false
    var checkMessageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var sendMessageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var findTelephoneParams: WeakHashMap<String, Any>? = WeakHashMap()
    var sendMessageBean = SendMessageBean()
    var checkMessageBean = CheckMessageBean()
    var commonBean = CommonBean()
    private var mISignListener: ISignListener? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ISignListener) {
            mISignListener = activity
        }
    }

    fun create(): ModifyPasswordDelegate? {
        return ModifyPasswordDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_modify_password
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        modify_pwd_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        modify_pwd_toolbar.title = getString(R.string.modify_password)
        (activity as AppCompatActivity).setSupportActionBar(modify_pwd_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        EmallLogger.d(arguments.getString("PAGE_FROM"))

        modify_pwd_vcode_et.addTextChangedListener(mVcodeTextWatcher)
        modify_pwd_tel_et.addTextChangedListener(mTelTextWatcher)

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (modify_pwd_title_rl != null)
                    modify_pwd_title_rl.visibility = View.GONE
            }

            override fun keyBoardHide(height: Int) {
                if (modify_pwd_title_rl != null)
                    modify_pwd_title_rl.visibility = View.VISIBLE
            }
        })

        modify_pwd_toolbar.setNavigationOnClickListener {
            _mActivity.onBackPressed()
        }

        modify_pwd_count_down.setCountDownMillis(60000)
        modify_pwd_count_down.setOnClickListener {
            tel = modify_pwd_tel_et.text.toString()
            if (tel.isEmpty()) {
                /**
                 * empty tel
                 */
                Toast.makeText(activity, getString(R.string.empty_tel) + tel, Toast.LENGTH_SHORT).show()
            } else {
                /**
                 * tel ok
                 */
                if (RegexUtils.isMobileExact(tel)) {
                    /**
                     * tel is valid
                     */
                    findTelephone(tel)
                } else {
                    /**
                     * tel is invalid
                     */
                    Toast.makeText(activity, getString(R.string.wrong_tel) + tel, Toast.LENGTH_SHORT).show()
                }
            }
//            sign_in_by_tel_vcode_tv.visibility = View.VISIBLE
        }

        modify_pwd_submit_btn.setOnClickListener {
            tel = modify_pwd_tel_et.text.toString()
            vCode = modify_pwd_vcode_et.text.toString()
            if (RegexUtils.isMobileExact(tel)) {
                checkMessage(tel, vCode)
            } else {
                Toast.makeText(activity, getString(R.string.wrong_tel) + tel, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun findTelephone(tel: String) {
        findTelephoneParams!!["telephone"] = tel
        RestClient().builder()
                .url("http://59.110.161.48:8023/findTelephone.do")
                .params(findTelephoneParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.meta == "success"){
                            /**
                             * success
                             */
                            getVCode(tel)
                            modify_pwd_count_down.start()

                        }else{
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

    private fun checkMessage(tel: String, vCode: String) {
        checkMessageParams!!["telephone"] = tel
        checkMessageParams!!["code"] = vCode
        RestClient().builder()
                .url("http://59.110.161.48:8023/global/mall/checkMessage.do")
                .params(checkMessageParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        checkMessageBean = Gson().fromJson(response, CheckMessageBean::class.java)
                        if (checkMessageBean.meta == "success"){
                            /**
                             * success
                             */

                            /**
                             * login
                             */
                            if(DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()){
                                SignHandler().onSignIn(response, mISignListener!!)
                            }

                            EmallLogger.d(arguments.getString("PAGE_FROM"))
                            val delegate: ResetPasswordDelegate = ResetPasswordDelegate().create()!!
                            val bundle = Bundle()
                            bundle.putString("MODIFY_PASSWORD_TELEPHONE", tel)
                            bundle.putString("PAGE_FROM",arguments.getString("PAGE_FROM"))
                            delegate.arguments = bundle
                            start(delegate)
                            KeyboardUtils.hideSoftInput(activity)
                        }else{
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

    private fun getVCode(tel: String) {
        sendMessageParams!!["telephone"] = tel
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
                            Toast.makeText(activity, getString(R.string.not_register), Toast.LENGTH_SHORT).show()
                        } else {
                            /**
                             * registered
                             */
                            showHint()
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
        modify_pwd_vcode_tv.text = String.format("已向手机%s发送验证码", hideTel())
        modify_pwd_vcode_tv.visibility = View.VISIBLE
    }

    private fun hideTel(): String {
        return String.format("%s****%s", tel.substring(0, 4), tel.substring(7, 11))
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
            if (modify_pwd_tel_et.text.toString() == "") {
                modify_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                modify_pwd_submit_btn.isClickable = false
            }
            if (flag1 && flag2) {
                modify_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                modify_pwd_submit_btn.isClickable = true
            }
        }
    }

    private var mVcodeTextWatcher: TextWatcher = object : TextWatcher {
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
            if (modify_pwd_vcode_et.text.toString() == "") {
                modify_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                modify_pwd_submit_btn.isClickable = false
            }
            if (flag1 && flag2) {
                modify_pwd_submit_btn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                modify_pwd_submit_btn.isClickable = true
            }
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }
}