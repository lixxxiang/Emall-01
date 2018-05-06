package com.example.emall_ec.main.me.feedback

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_my_opinion.*
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*

class MyOpinionDelegate : EmallDelegate() {

    var mSharedPreferences: SharedPreferences? = null
    fun create(): MyOpinionDelegate? {
        return MyOpinionDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_my_opinion
    }

    override fun initial() {
        mSharedPreferences = activity.getSharedPreferences("OPINION", Context.MODE_PRIVATE)
        my_opinion_edt.isFocusable = true
        my_opinion_edt.isFocusableInTouchMode = true
        my_opinion_edt.requestFocus()
        my_opinion_edt.addTextChangedListener(mTextWatcher)
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
            EmallLogger.d(my_opinion_edt.text.toString())
            val editor = mSharedPreferences!!.edit()
            editor.putString("opinion", my_opinion_edt.text.toString())
            editor.commit()
        }
    }



    override fun onSupportVisible() {
        super.onSupportVisible()
        KeyboardUtils.showSoftInput(activity)
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }
}