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
import kotlinx.android.synthetic.main.delegate_goods_detail_coupon.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class GoodsDetailCouponDelegate : EmallDelegate() {


    fun create(): GoodsDetailCouponDelegate? {
        return GoodsDetailCouponDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods_detail_coupon
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        goods_detail_coupon_toolbar.title = "可使用的优惠券"
        (activity as AppCompatActivity).setSupportActionBar(goods_detail_coupon_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        goods_detail_coupon_toolbar.setNavigationOnClickListener {
            pop()
        }


    }


    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}