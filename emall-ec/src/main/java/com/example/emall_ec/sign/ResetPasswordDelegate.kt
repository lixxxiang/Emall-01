package com.example.emall_ec.sign

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_modify_password.*
import kotlinx.android.synthetic.main.delegate_reset_password.*

/**
 * Created by lixiang on 2018/4/2.
 */
class ResetPasswordDelegate : BottomItemDelegate(){

    fun create(): ResetPasswordDelegate?{
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

        reset_pwd_confirm_pwd_et.addTextChangedListener(mTextWatcher)
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
}