package com.example.emall_ec.main.me

import android.graphics.Typeface
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_me.*

/**
 * Created by lixiang on 2018/3/3.
 */
class MeDelegate : BottomItemDelegate() {
    override fun setLayout(): Any? {
        return R.layout.delegate_me
    }

    override fun initial() {
        me_foward.typeface = Typeface.createFromAsset(activity.assets, "iconfont/foward.ttf")
    }
}