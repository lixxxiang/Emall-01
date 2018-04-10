package com.example.emall_ec.main.me

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.order.OrderListDelegate
import kotlinx.android.synthetic.main.delegate_me.*
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.database.UserProfile
import com.example.emall_ec.main.me.setting.SettingDelegate
import com.example.emall_ec.main.sign.SignInByTelDelegate
import me.yokeyword.fragmentation.ISupportFragment


/**
 * Created by lixiang on 2018/3/3.
 */
class MeDelegate : BottomItemDelegate() {
    var DELEGATE: EmallDelegate? = null
    var iconList: MutableList<Int>? = mutableListOf()
    var titleList: MutableList<Int>? = mutableListOf()
    var userName = String()
    var ME_USERNAME_CODE = 100
    override fun setLayout(): Any? {
        return R.layout.delegate_me
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

    }

    //    override fun onStart() {
//        super.onStart()
//        EmallLogger.d("dfdfd")
//        val userDao = DatabaseManager().getInstance()!!.getDao()!!
//        val list: List<UserProfile>? = userDao.loadAll()
//        for (i in list!!.indices) {
//            EmallLogger.d("google_lenve", "search: " + list[i].username)
//            userName = list[i].username
//        }
//
//        if(userName != ""){
//            me_user_name.text = userName
//
//            me_hint.text = getString(R.string.me_hint)
//        }
//
//    }
//
    override fun onResume() {
        super.onResume()
        EmallLogger.d("resume")

    }

    override fun initial() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(activity, null)
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


        if (DimenUtil().getScreenHeight() - SizeUtils.getMeasuredHeight(me_ll) > 0) {
            val rl = RelativeLayout(activity)
            val rlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (DimenUtil().getScreenHeight() - SizeUtils.getMeasuredHeight(me_ll)))
            rl.layoutParams = rlParams
            rl.setBackgroundColor(Color.parseColor("#F0F0F0"))
            me_ll.addView(rl, rlParams)
        }


        DELEGATE = getParentDelegate()

        me_foward.typeface = Typeface.createFromAsset(activity.assets, "iconfont/foward.ttf")
        me_function_lv.adapter = MeFunctionAdapter(iconList, titleList, context)

        me_order.setOnClickListener {
            val delegate: OrderListDelegate = OrderListDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("USER_ID", DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }

        me_avatar_iv.setOnClickListener {
            //            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
//            val bundle: Bundle? = Bundle()
//            bundle!!.putString("KEY", "ID")
//            delegate.arguments = bundle
//            (DELEGATE as EcBottomDelegate).startForResult(SignInByTelDelegate().create()!!,ME_USERNAME_CODE)
            DELEGATE!!.startForResult(SignInByTelDelegate().create()!!, ME_USERNAME_CODE)
        }



        me_function_lv.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {

                }
                1 -> {

                }
                2 -> {

                }
                3 -> {
                    (DELEGATE as EcBottomDelegate).start(SettingDelegate().create())
                }
                4 -> {

                }
                5 -> {

                }
            }
        }
    }


    override fun onSupportVisible() {
        super.onSupportVisible()
//        val list: List<UserProfile>? = DatabaseManager().getInstance()!!.getDao()!!.loadAll()
//        EmallLogger.d("google_lenve", "search: " + list!![0].username)
        if(!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()){
            userName = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].username


            if (userName != "") {
                me_user_name.text = userName
                me_hint.text = getString(R.string.me_hint)
            }
        }

    }
}