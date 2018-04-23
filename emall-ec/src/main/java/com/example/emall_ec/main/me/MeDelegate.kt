package com.example.emall_ec.main.me

import android.graphics.Color
import android.os.Bundle
import android.view.View
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
import com.example.emall_ec.main.me.adapter.MeFunctionAdapter
import com.example.emall_ec.main.me.collect.CollectionDelegate
import com.example.emall_ec.main.me.setting.SettingDelegate
import com.example.emall_ec.main.sign.SignInByTelDelegate


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

        me_function_lv.adapter = MeFunctionAdapter(iconList, titleList, context)

        me_order.setOnClickListener {
            toOrderDelegate(0)
        }

        me_avatar_iv.setOnClickListener {
            //            val delegate: OrderDetailDelegate = OrderDetailDelegate().create()!!
//            val bundle: Bundle? = Bundle()
//            bundle!!.putString("KEY", "ID")
//            delegate.arguments = bundle
//            (DELEGATE as EcBottomDelegate).startForResult(SignInByTelDelegate().create()!!,ME_USERNAME_CODE)
            if(DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()){
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "AVATAR")
                delegate.arguments = bundle
                DELEGATE!!.startForResult(delegate, ME_USERNAME_CODE)
            }
        }




        me_function_lv.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    (DELEGATE as EcBottomDelegate).start(CollectionDelegate().create())
                }
                1 -> {

                }
                2 -> {

                }
                3 -> {
                    (DELEGATE as EcBottomDelegate).start(SettingDelegate().create())
                }
                4 -> {
                    (DELEGATE as EcBottomDelegate).start(ContactDelegate().create())
                }
                5 -> {

                }
            }
        }

        btn1.setOnClickListener {
            toOrderDelegate(1)
        }

        btn2.setOnClickListener {
            toOrderDelegate(2)
        }

        btn3.setOnClickListener {
            toOrderDelegate(3)
        }

        btn4.setOnClickListener {
            toOrderDelegate(4)
        }


    }

    fun toOrderDelegate(index: Int){
        if(DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()){
            val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
            val bundle = Bundle()
            bundle.putString("PAGE_FROM", "ME")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }else{
            val delegate: OrderListDelegate = OrderListDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("USER_ID", DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
            bundle.putInt("INDEX", index)
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }
    }


    override fun onSupportVisible() {
        super.onSupportVisible()
//        val list: List<UserProfile>? = DatabaseManager().getInstance()!!.getDao()!!.loadAll()
//        EmallLogger.d("google_lenve", "search: " + list!![0].username)
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        if(!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()){
            userName = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].username

            if (userName != "") {
                me_user_name.text = userName
                me_hint.text = getString(R.string.me_hint)
            }
        }else{
            me_user_name.text = getString(R.string.sign_in)
            me_hint.text = getString(R.string.click_to_login)
        }
    }
}