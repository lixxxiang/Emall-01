package com.example.emall_ec.main.coupon

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.coupon.type.EnableCouponDelegate
import com.example.emall_ec.main.coupon.type.InvalidCouponDelegate
import com.example.emall_ec.main.coupon.type.UsedCouponDelegate
import com.example.emall_ec.main.order.Find_tab_Adapter
import kotlinx.android.synthetic.main.delegate_coupon.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class CouponDelegate : EmallDelegate() {
    var listFragment: MutableList<Fragment>? = mutableListOf()
    var listTitle: MutableList<String>? = mutableListOf()
    private var fAdapter: FragmentPagerAdapter? = null

    fun create(): CouponDelegate? {
        return CouponDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_coupon
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        coupon_toolbar.title = "优惠券中心"
        (activity as AppCompatActivity).setSupportActionBar(coupon_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        coupon_toolbar.setNavigationOnClickListener {
            pop()
        }

        initControls()

    }


    private fun initControls() {


        //初始化各fragment
        var enableCouponDelegate = EnableCouponDelegate()
        var usedCouponDelegate = UsedCouponDelegate()
        var invalidCouponDelegate = InvalidCouponDelegate()

        //将fragment装进列表中
        listFragment!!.add(enableCouponDelegate)
        listFragment!!.add(usedCouponDelegate)
        listFragment!!.add(invalidCouponDelegate)

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        listTitle!!.add(resources.getString(R.string.enable))
        listTitle!!.add(resources.getString(R.string.used))
        listTitle!!.add(resources.getString(R.string.invalid))

        //设置TabLayout的模式
        coupon_tl.tabMode = TabLayout.MODE_FIXED
        //为TabLayout添加tab名称
        coupon_tl.addTab(coupon_tl.newTab().setText(listTitle!![0]))
        coupon_tl.addTab(coupon_tl.newTab().setText(listTitle!![1]))
        coupon_tl.addTab(coupon_tl.newTab().setText(listTitle!![2]))


        fAdapter = Find_tab_Adapter(childFragmentManager, listFragment, listTitle)

        //viewpager加载adapter
        coupon_vp.adapter = fAdapter
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        coupon_tl.setupWithViewPager(coupon_vp)
        coupon_vp.offscreenPageLimit = 3
        coupon_vp.currentItem = arguments.getInt("INDEX")

    }


    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}