package com.example.emall_ec.main.me.setting

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.blankj.utilcode.util.RegexUtils
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_modify_tel.*
import kotlinx.android.synthetic.main.delegate_reset_password.*

/**
 * Created by lixiang on 2018/4/8.
 */
class ModifyTelDelegate : BottomItemDelegate(){

    var oldTel = String()
    var newTel = String()

    fun create(): ModifyTelDelegate?{
        return ModifyTelDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_modify_tel
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        modify_tel_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        modify_tel_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(modify_tel_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        modify_tel_old_tel_et.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b){

            }else{
                val tempTel = modify_tel_old_tel_et.text.toString()
                if (RegexUtils.isMobileExact(tempTel)){

                }
            }
        }
    }
}