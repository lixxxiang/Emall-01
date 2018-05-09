package com.example.emall_ec.main.order

import android.content.Context
import android.content.SharedPreferences
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_order_list.*
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.example.emall_ec.main.order.state.*
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.me.setting.SettingDelegate
import com.example.emall_ec.main.program.ProgramDelegate
import com.example.emall_ec.main.program.ProgramIndexDelegate
import me.yokeyword.fragmentation.SwipeBackLayout
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * Created by lixiang on 2018/3/5.
 */
class OrderListDelegate : BottomItemDelegate() {
    var listFragment: MutableList<Fragment>? = mutableListOf()
    var listTitle: MutableList<String>? = mutableListOf()
    var userId = String()
    private var fAdapter: FragmentPagerAdapter? = null
    var mSharedPreferences: SharedPreferences? = null
    fun create(): OrderListDelegate? {
        return OrderListDelegate()
    }

    override fun initial() {
        setSwipeBackEnable(false)
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        order_list_toolbar.title = getString(R.string.my_order)
        mSharedPreferences = activity.getSharedPreferences("FROM_ORDER_LIST", Context.MODE_PRIVATE)
        userId = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        (activity as AppCompatActivity).setSupportActionBar(order_list_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        order_list_toolbar.setNavigationOnClickListener {
            EmallLogger.d(arguments.getString("PAGE_FROM"))
            when {
                arguments.getString("PAGE_FROM") == "CLASSIFY" -> popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
                arguments.getString("PAGE_FROM") == "ORDER_LIST" -> popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                arguments.getString("PAGE_FROM") == "ME" -> popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)

                arguments.getString("PAGE_FROM") == "GOODS_DETAIL" -> popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
                arguments.getString("PAGE_FROM") == "PAYMENT" -> {
                    if (findFragment(GoodsDetailDelegate().javaClass) == null) {
                        popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                    } else
                        popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
                }
                arguments.getString("PAGE_FROM") == "PROGRAM_INDEX" ->
                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                arguments.getString("PAGE_FROM") == "PROGRAM" ->
                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                else -> pop()
            }
        }
        initControls()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_order_list
    }

    private fun initControls() {


        //初始化各fragment
        var allDelegate = All2Delegate()
        var obligationDelegate = Obligation2Delegate()
        var checkPendingDelegate = CheckPending2Delegate()
        var inProductionDelegate = InProduction2Delegate()
        var deliveredDelegate = Delivered2Delegate()

        //将fragment装进列表中
        listFragment!!.add(allDelegate)
        listFragment!!.add(obligationDelegate)
        listFragment!!.add(checkPendingDelegate)
        listFragment!!.add(inProductionDelegate)
        listFragment!!.add(deliveredDelegate)

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        listTitle!!.add(resources.getString(R.string.all))
        listTitle!!.add(resources.getString(R.string.obligation))
        listTitle!!.add(resources.getString(R.string.check_pending))
        listTitle!!.add(resources.getString(R.string.in_production))
        listTitle!!.add(resources.getString(R.string.delivered))

        //设置TabLayout的模式
        tab_FindFragment_title.tabMode = TabLayout.MODE_FIXED
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(listTitle!![0]))
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(listTitle!![1]))
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(listTitle!![2]))
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(listTitle!![3]))
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(listTitle!![4]))


        fAdapter = Find_tab_Adapter(childFragmentManager, listFragment, listTitle)

        //viewpager加载adapter
        vp_FindFragment_pager.adapter = fAdapter
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager)
        vp_FindFragment_pager.offscreenPageLimit = 5
        vp_FindFragment_pager.currentItem = arguments.getInt("INDEX")
        //tab_FindFragment_title.set

//        swipeBackLayout.addSwipeListener(object : SwipeBackLayout.OnSwipeListener {
//            override fun onEdgeTouch(oritentationEdgeFlag: Int) {
//                EmallLogger.d("1")
//            }
//
//            override fun onDragScrolled(scrollPercent: Float) {
//                EmallLogger.d("2")
//                if (arguments.getString("PAGE_FROM") == "PROGRAM") {
//                    popTo(findFragment(ProgramDelegate().javaClass).javaClass, false)
//                }
//                if (arguments.getString("PAGE_FROM") == "CLASSIFY")
//                    popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
//                if (arguments.getString("PAGE_FROM") == "PROGRAM_INDEX") {
//                    val editor = mSharedPreferences!!.edit()
//                    editor.putString("from_order_list", "1")
//                    editor.commit()
//                    popTo(findFragment(ProgramIndexDelegate().javaClass).javaClass, false)
//                }
//            }
//
//            override fun onDragStateChange(state: Int) {
//                EmallLogger.d("3")
//            }
//
//        })
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}