package com.example.emall_ec.sign

import android.graphics.Color
import android.graphics.Typeface
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.githang.statusbar.StatusBarCompat
import kotlinx.android.synthetic.main.delegate_sign_in_by_account.*
import kotlinx.android.synthetic.main.delegate_sign_in_by_tel.*

/**
 * Created by lixiang on 2018/3/3.
 */
class SignInByAccountDelegate : EmallDelegate() {
    var isHide : Boolean = true
    override fun setLayout(): Any? {
        return R.layout.delegate_sign_in_by_account
    }

    override fun initial() {

//        StatusBarCompat.setStatusBarColor(activity, Color.WHITE)
        sign_in_by_account_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        sign_in_by_account_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/hide.ttf")

        sign_in_by_account_hide_tv.setOnClickListener {
            if (isHide){
                sign_in_by_account_hide_tv.height = 14
                sign_in_by_account_hide_tv.text = getString(R.string.show)
                sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/show.ttf")
                isHide = false
            }else{
                sign_in_by_account_hide_tv.height = 9
                sign_in_by_account_hide_tv.text = getString(R.string.hide)
                sign_in_by_account_hide_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/hide.ttf")
                isHide = true
            }

        }
    }
}