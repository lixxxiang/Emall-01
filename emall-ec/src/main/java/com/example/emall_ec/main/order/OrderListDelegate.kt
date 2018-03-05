package com.example.emall_ec.main.order

import android.support.design.R.id.container
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_order_list.*
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.example.emall_ec.main.order.state.*
import android.support.v4.app.FragmentPagerAdapter




/**
 * Created by lixiang on 2018/3/5.
 */
class OrderListDelegate : BottomItemDelegate() {
    var listFragment: MutableList<Fragment>? = mutableListOf()
    var listTitle: MutableList<String>? = mutableListOf()
    private var fAdapter: FragmentPagerAdapter? = null
    fun create(): OrderListDelegate? {
        return OrderListDelegate()
    }

    override fun initial() {
        order_list_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(order_list_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initControls()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_order_list
    }

    private fun initControls() {


        //初始化各fragment
        var allDelegate = AllDelegate()
        var obligationDelegate = ObligationDelegate()
        var checkPendingDelegate = CheckPendingDelegate()
        var inProductionDelegate = InProductionDelegate()
        var deliveredDelegate = DeliveredDelegate()

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


        fAdapter = Find_tab_Adapter(activity.supportFragmentManager, listFragment, listTitle)

        //viewpager加载adapter
        vp_FindFragment_pager.adapter = fAdapter
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager)
        //tab_FindFragment_title.set
    }
}