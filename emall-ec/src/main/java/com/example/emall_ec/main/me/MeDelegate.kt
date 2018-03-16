package com.example.emall_ec.main.me

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.view.StatusBarCompat
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.order.OrderListDelegate
import com.example.emall_ec.main.order.OrderDetailDelegate
import kotlinx.android.synthetic.main.delegate_me.*
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.os.Build




/**
 * Created by lixiang on 2018/3/3.
 */
class MeDelegate : BottomItemDelegate() {
    var DELEGATE: EmallDelegate? = null
    var iconList: MutableList<Int>? = mutableListOf()
    var titleList: MutableList<Int>? = mutableListOf()
    override fun setLayout(): Any? {
        return R.layout.delegate_me
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    override fun initial() {
        iconList!!.add(R.drawable.me_favorite)
        iconList!!.add(R.drawable.me_ticket)
        iconList!!.add(R.drawable.me_bill)
        iconList!!.add(R.drawable.me_setting)
        iconList!!.add(R.drawable.me_contect_us)
        iconList!!.add(R.drawable.me_suggestion)

        titleList!!.add(R.string.favourite)
        titleList!!.add(R.string.ticket)
        titleList!!.add(R.string.bill)
        titleList!!.add(R.string.setting)
        titleList!!.add(R.string.contact_us)
        titleList!!.add(R.string.suggestion)
//        StatusBarCompat.setStatusBarColor(activity, Color.TRANSPARENT)
        //should hide status bar background (default black background) when SDK >= 21
        DELEGATE = getParentDelegate()

        me_foward.typeface = Typeface.createFromAsset(activity.assets, "iconfont/foward.ttf")
        me_function_lv.adapter = MeFunctionAdapter(iconList, titleList, context)

        me_order.setOnClickListener {
            val delegate: OrderListDelegate = OrderListDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }

        me_avatar_iv.setOnClickListener {
            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }
    }
}