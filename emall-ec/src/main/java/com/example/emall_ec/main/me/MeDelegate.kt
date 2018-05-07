package com.example.emall_ec.main.me

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.order.OrderListDelegate
import kotlinx.android.synthetic.main.delegate_me.*
import android.widget.RelativeLayout
import android.widget.Toast
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SizeUtils
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.demand.InvoiceDelegate
import com.example.emall_ec.main.me.adapter.MeFunctionAdapter
import com.example.emall_ec.main.me.collect.CollectionDelegate
import com.example.emall_ec.main.me.feedback.FeedbackDelegate
import com.example.emall_ec.main.me.setting.SettingDelegate
import com.example.emall_ec.main.order.ProductDeliveryDelegate
import com.example.emall_ec.main.scanner.ScannerDelegate
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
    var toast: Toast?= null
    override fun setLayout(): Any? {
        return R.layout.delegate_me
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(activity, null)
        setSwipeBackEnable(false)

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
            if (!NetworkUtils.isConnected())
                if (toast != null) {
                    toast!!.setText(getString(R.string.no_internet))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            else
                toOrderDelegate(0)
        }

        me_avatar_iv.setOnClickListener {
            if (!NetworkUtils.isConnected())
                if (toast != null) {
                    toast!!.setText(getString(R.string.no_internet))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            else {
                if (DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                    val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                    val bundle = Bundle()
                    bundle.putString("PAGE_FROM", "AVATAR")
                    delegate.arguments = bundle
                    DELEGATE!!.startForResult(delegate, ME_USERNAME_CODE)
                }
            }
        }

        me_fake_btn.setOnClickListener {
            if (!NetworkUtils.isConnected())
                if (toast != null) {
                    toast!!.setText(getString(R.string.no_internet))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            else {
                if (DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                    val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                    val bundle = Bundle()
                    bundle.putString("PAGE_FROM", "AVATAR")
                    delegate.arguments = bundle
                    DELEGATE!!.startForResult(delegate, ME_USERNAME_CODE)
                }
            }
        }



        me_function_lv.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    if (!NetworkUtils.isConnected())
                        if (toast != null) {
                            toast!!.setText(getString(R.string.no_internet))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                    else {
                        if (!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                            (DELEGATE as EcBottomDelegate).start(CollectionDelegate().create())
                        } else {
                            val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                            val bundle = Bundle()
                            bundle.putString("PAGE_FROM", "ME")
                            delegate.arguments = bundle
                            (DELEGATE as EcBottomDelegate).start(delegate)
                        }
                    }
                }
                1 -> {

                }
                2 -> {
                    if (!NetworkUtils.isConnected())
                        if (toast != null) {
                            toast!!.setText(getString(R.string.no_internet))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                    else {
                        if (DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                            val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                            val bundle = Bundle()
                            bundle.putString("PAGE_FROM", "ME")
                            delegate.arguments = bundle
                            (DELEGATE as EcBottomDelegate).start(delegate)
                        } else {
                            val delegate: InvoiceDelegate = InvoiceDelegate().create()!!
                            val bundle: Bundle? = Bundle()
                            bundle!!.putString("INVOICE_PRICE", "-")
                            delegate.arguments = bundle
                            (DELEGATE as EcBottomDelegate).start(delegate)
                        }
                    }
                }
                3 -> {
                    if (!NetworkUtils.isConnected())
                        if (toast != null) {
                            toast!!.setText(getString(R.string.no_internet))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                    else
                        (DELEGATE as EcBottomDelegate).start(SettingDelegate().create())
                }
                4 -> {
                    if (!NetworkUtils.isConnected())
                        if (toast != null) {
                            toast!!.setText(getString(R.string.no_internet))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                    else
                        (DELEGATE as EcBottomDelegate).start(ContactDelegate().create())
                }
                5 -> {
                    if (!NetworkUtils.isConnected())
                        if (toast != null) {
                            toast!!.setText(getString(R.string.no_internet))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
                    else
                        (DELEGATE as EcBottomDelegate).start(FeedbackDelegate().create())
                }
            }
        }

        btn1.setOnClickListener {
            if (!NetworkUtils.isConnected())
                if (toast != null) {
                    toast!!.setText(getString(R.string.no_internet))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            else

                toOrderDelegate(1)
        }

        btn2.setOnClickListener {
            if (!NetworkUtils.isConnected())
                if (toast != null) {
                    toast!!.setText(getString(R.string.no_internet))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            else
                toOrderDelegate(2)
        }

        btn3.setOnClickListener {
            if (!NetworkUtils.isConnected())
                if (toast != null) {
                    toast!!.setText(getString(R.string.no_internet))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            else
                toOrderDelegate(3)
        }

        btn4.setOnClickListener {
            if (!NetworkUtils.isConnected())
                if (toast != null) {
                    toast!!.setText(getString(R.string.no_internet))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            else
                toOrderDelegate(4)
        }

        me_to_computer_iv.setOnClickListener {
            if (!NetworkUtils.isConnected())
                if (toast != null) {
                    toast!!.setText(getString(R.string.no_internet))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
            else
                (DELEGATE as EcBottomDelegate).start(ScannerDelegate().create())
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun toOrderDelegate(index: Int) {
        if (DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
            val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
            val bundle = Bundle()
            bundle.putString("PAGE_FROM", "ME")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        } else {
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

        if (!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
            userName = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].username

            if (userName != "") {
                me_user_name.text = userName
                me_hint.text = getString(R.string.me_hint)
            }
            me_to_computer_iv.visibility = View.VISIBLE
        } else {
            me_user_name.text = getString(R.string.sign_in)
            me_hint.text = getString(R.string.click_to_login)
            me_to_computer_iv.visibility = View.GONE
        }
    }
}